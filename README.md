# Card Placement Game (Java)

A networked digital implementation of a strategic card-placement board game, "Codex Naturalis" by CranioCreations, 
developed as a Software Engineering university project (A.Y. 2023–2024).

This public version replaces all proprietary assets with placeholders.

---

## Project Overview

The project consists of a distributed multiplayer card game built in Java, 
featuring a complete game engine, client-server architecture, and both CLI and GUI interfaces.

The focus of the project was:

- Object-Oriented Design
- MVC architecture
- Network communication (RMI and Socket)
- Concurrency handling
- Modular and scalable structure

---

## Architecture

The system follows a layered architecture:

- **Model** – Core game logic and rule enforcement  
- **Controller** – Turn management and game flow coordination  
- **View** – CLI (TUI) and GUI implementations  
- **Networking Layer** – Support for both Socket and RMI communication  

The server supports multiple simultaneous games and concurrent clients.

---

## Features

### Core Features
- Full rule implementation
- Multiplayer support
- Turn-based game engine
- CLI interface
- GUI interface
- Socket communication
- RMI communication

### Advanced Features
- Multiple concurrent matches
- Integrated chat system
- Disconnection handling

---

## How to Run
This is a Maven project, it should be suitable to the creation of an executable jar using the Maven tools



