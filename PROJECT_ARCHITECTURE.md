# 8085 Simulator - Actual Project Architecture

## System Flow Diagram

```
                           START
                             |
                             v
                    ┌─────────────────┐
                    │  SimulatorGUI   │
                    │   Main Window   │
                    │   (A/M/R/G)     │
                    └────────┬────────┘
                             |
            ┌────────────────┼────────────────┐
            |                |                |
            v                v                v
    ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
    │ Press A      │  │ Press M      │  │ Press R      │
    │ CodeWindow   │  │ MemoryWindow │  │RegisterWindow│
    └──────┬───────┘  └──────┬───────┘  └──────┬───────┘
           |                 |                  |
           v                 v                  v
    Write Assembly    View/Edit Memory   View Registers
    Code & Save       (Read/Write)       (Display Only)
           |                 |                  |
           └─────────────────┼──────────────────┘
                             |
                             v
                      ┌──────────────┐
                      │ Press G      │
                      │ExecuteWindow │
                      └──────┬───────┘
                             |
                             v
                    ┌─────────────────┐
                    │ Get Saved Code  │
                    │ from CodeWindow │
                    └────────┬────────┘
                             |
                             v
                    ┌─────────────────┐
                    │InstructionParser│
                    │  Parse Assembly │
                    │  Validate Syntax│
                    └────────┬────────┘
                             |
                    ┌────────┴────────┐
                    |                 |
                    v                 v
              Valid Code        Invalid Code
                    |                 |
                    v                 v
            ┌──────────────┐   Show Error
            │ InstExecutor │   Message
            │  Execute     │
            │ Instructions │
            └──────┬───────┘
                   |
                   v
            ┌──────────────┐
            │ Shared CPU   │
            │ (SimulatorState)
            │              │
            │ - Registers  │
            │ - Memory     │
            │ - Flags      │
            │ - PC, SP     │
            └──────┬───────┘
                   |
                   v
            Display Results
            (Registers, Flags,
             Memory Changes)
                   |
                   v
                 STOP
```

## Component Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    GUI Layer                            │
├─────────────────────────────────────────────────────────┤
│  SimulatorGUI.java    - Main menu (A/M/R/G)            │
│  CodeWindow.java      - Assembly code editor            │
│  MemoryWindow.java    - Memory viewer/editor            │
│  RegisterWindow.java  - Register display                │
│  ExecuteWindow.java   - Code execution & results        │
└────────────────────┬────────────────────────────────────┘
                     |
┌────────────────────┴────────────────────────────────────┐
│                 State Management                        │
├─────────────────────────────────────────────────────────┤
│  SimulatorState.java  - Shared CPU instance             │
│  MongoDBHelper.java   - Optional code storage           │
└────────────────────┬────────────────────────────────────┘
                     |
┌────────────────────┴────────────────────────────────────┐
│                  Core Engine                            │
├─────────────────────────────────────────────────────────┤
│  simulator/cpu/CPU.java                                 │
│  simulator/executor/InstExecutor.java                   │
│  simulator/parser/InstructionParser.java                │
└────────────────────┬────────────────────────────────────┘
                     |
┌────────────────────┴────────────────────────────────────┐
│              CPU Components                             │
├─────────────────────────────────────────────────────────┤
│  simulator/memory/Memory.java                           │
│  simulator/registers/Registers.java                     │
│  simulator/registers/Flags.java                         │
│  simulator/registers/ProgramCounter.java                │
│  simulator/registers/StackPointer.java                  │
└────────────────────┬────────────────────────────────────┘
                     |
┌────────────────────┴────────────────────────────────────┐
│            Instruction Set (49 Instructions)            │
├─────────────────────────────────────────────────────────┤
│  simulator/instructions/Instruction.java (Base)         │
│  ├── arithmetic/  (ADD, SUB, INR, DCR, etc.)           │
│  ├── datatransfer/ (MOV, MVI, LDA, STA, etc.)          │
│  ├── logical/     (ANA, ORA, XRA, CMP, etc.)           │
│  ├── branching/   (JMP, JZ, JNZ, CALL, RET, etc.)      │
│  ├── stack/       (PUSH, POP, XTHL, SPHL)              │
│  └── control/     (HLT, NOP, STC, CMC)                 │
└─────────────────────────────────────────────────────────┘
```

## Data Flow

### 1. Writing Code (Press A)
```
User → SimulatorGUI → CodeWindow
                         ↓
                   User writes code
                         ↓
                   Save (ESC key)
                         ↓
              Store in static variable
                         ↓
            Optional: MongoDBHelper.saveCode()
                         ↓
                Return to SimulatorGUI
```

### 2. Editing Memory (Press M)
```
User → SimulatorGUI → MemoryWindow
                         ↓
                   Get shared CPU
                   (SimulatorState.getCPU())
                         ↓
              User enters address & value
                         ↓
                cpu.memory.write(addr, val)
                         ↓
                Return to SimulatorGUI
```

### 3. Viewing Registers (Press R)
```
User → SimulatorGUI → RegisterWindow
                         ↓
                   Get shared CPU
                   (SimulatorState.getCPU())
                         ↓
              Display current register values
                         ↓
                Return to SimulatorGUI
```

### 4. Executing Code (Press G)
```
User → SimulatorGUI → ExecuteWindow
                         ↓
              Get saved code from CodeWindow
                         ↓
              InstructionParser.parse(code)
                         ↓
         ┌───────────────┴───────────────┐
         v                               v
    Valid Instructions            Invalid Syntax
         |                               |
         v                               v
    cpu.softReset()              Show error message
    (preserve memory!)                   |
         |                               v
         v                          Return to menu
    InstExecutor.run(instructions)
         |
         v
    Execute each instruction:
    - Update registers
    - Modify memory
    - Set flags
    - Update PC
         |
         v
    Display results:
    - Final register values
    - Flags status
    - PC, SP values
    - Execution log
         |
         v
    Return to SimulatorGUI
```

## Key Design Patterns

### 1. Singleton Pattern
- **SimulatorState** maintains a single shared CPU instance
- All windows access the same CPU state

### 2. Command Pattern
- Each instruction implements the **Instruction** interface
- `execute(CPU cpu)` method performs the operation

### 3. Parser Pattern
- **InstructionParser** tokenizes and validates assembly code
- Converts text to Instruction objects

### 4. MVC-like Pattern
- **GUI classes** = View (display and user input)
- **CPU/Memory/Registers** = Model (data and state)
- **Executor/Parser** = Controller (business logic)

## Technology Stack

- **Language**: Java (Swing GUI)
- **GUI Framework**: Java Swing
- **Architecture**: Desktop application (NOT web-based)
- **Database**: Optional MongoDB (via MongoDBHelper)
- **Storage**: Local text files (saved_code.txt, code_history.txt)

## NOT Used in This Project

❌ Spring Boot
❌ Web services / REST APIs
❌ React / Angular / Vue
❌ Microservices
❌ Docker / Kubernetes
❌ Help/Tutorial system
❌ AI assistance features

## What This Project IS

✅ Desktop Java Swing application
✅ 8085 microprocessor simulator
✅ Assembly language parser and executor
✅ Memory and register visualization
✅ Educational tool for learning 8085 assembly
