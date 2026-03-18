# Platinum Programming Language (PPL)

Platinum (PPL) is a lightweight, **interpreted**, and **strictly dynamically typed** programming language built entirely on **Java**. It combines a clean syntax with the flexibility of dynamic typing while ensuring strict runtime behavior.

## Features

- **Interpreted:** No compilation step is required; code is executed directly by the PPL interpreter.
    
- **Strictly Dynamically Typed:** Variables do not require explicit type declarations, but operations between incompatible types are strictly handled to prevent silent errors.
    
- **Java-Based:** Built using Java, ensuring cross-platform compatibility.
    

---

## Getting Started

### Prerequisites

- Java Runtime Environment (JRE) 8 or higher.
    

### Installation

1. Clone the repository:
    
    Bash
    
    ```
    git clone https://github.com/Animemchik/PlatinumProgrammingLanguage-PPL.git
    ```
    
2. Build the project using your preferred Java IDE or build tool (e.g., Maven/Gradle).
    

---

## Language Syntax & Examples

### Variables

In PPL, you don't need to specify types. Declare variables by using `=` sign.

```
name = "Platinum"
version = 1.0
isEasy = true

if (isEasy) {
    println(name + " version " + version)
}
```

### Data Types

PPL supports standard types including:

- **String:** `"Hello World"`
    
- **Number:** `42`, `3.14`
    
- **Boolean:** `true`, `false`
    

### Control Flow

Use `if` statements and loops to control the execution logic.

**If-Else Statement:**

```
score = 85

if (score >= 90) {
    println("Grade: A")
} else {
    println("Grade: B")
}
```

**Loops:**

```
i = 0
while (i < 5) {
    println("Iteration: " + i)
    i = i + 1
}
```

### Functions

Define reusable blocks of code easily.

```
def greet(user) {
    return "Hello, " + user + "!"
}

message = greet("Developer")
println(message)
```

### Built-in Libraries

Platinum (PPL) includes several built-in modules to handle common tasks without external dependencies.

#### **Standard Library (`std`)**

The core library for basic operations, console I/O, and system interactions.

```
use "std"  
  
// Input from user  
name = readln("Enter your name: ")  
println("Welcome, " + name)
```

#### **Math Library (`math`)**

Provides mathematical constants and functions for advanced calculations.

```
use "Math"  
  
radius = 5  
area = PI * pow(radius, 2)  
  
println("Sine of 90 degrees: " + sin(toRadians(90)))  
println("Absolute value: " + abs(-42))
```

#### **File I/O (`files`)**

Allows you to read from and write to the local file system.

```
use "files"

// Writing to a file
fileId = fopen("example.txt", "w")
if (fileId != -1) {
    writeLine(fileId, "Hello from PPL!")
    fclose(fileId)
}

// Checking status
if (exists("example.txt")) {
    println("File size: " + fileSize("example.txt"))
}
```

#### **Colors Library (`colors`)**

Provides easy-to-use constants for ANSI color output in the terminal.

```
use "Colors"  
  
println(RED + "This is a red error message" + RESET)  
println(GREEN + "Success in green!" + RESET)  
println(CYAN + "Information in cyan" + RESET)
```

---

## Why Platinum?

Platinum is designed for developers who want a flexible scripting environment without the overhead of complex type systems, while still maintaining the reliability of the Java Virtual Machine (JVM).

## Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
