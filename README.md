# Blackjack Card Game (Java Swing)

![Java](https://img.shields.io/badge/Java-25%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java_Swing-E76F00?style=flat&logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Build-Gradle-02303A?style=flat&logo=gradle&logoColor=white)
![JUnit 5](https://img.shields.io/badge/Test-JUnit_5-25A162?style=flat&logo=junit5&logoColor=white)
![Architecture](https://img.shields.io/badge/Pattern-MVC-blue?style=flat)
![OOP](https://img.shields.io/badge/Concept-OOP_%2F_SOLID-important?style=flat)
![Status](https://img.shields.io/badge/Status-Educational-lightgrey?style=flat)
![License](https://img.shields.io/badge/License-All_Rights_Reserved-red?style=flat)

---

## 📋 Project Overview

**Blackjack Pro** is a robust, object-oriented implementation of the classic casino card game "Blackjack" (21), developed as a comprehensive software engineering project for the "Programming Fundamentals 3" course.

The application features a rich graphical user interface (GUI) built with **Java Swing** and adheres to modern software design principles (**MVC Architecture, SOLID, Clean Code**). Beyond the core gameplay, it includes advanced features like session persistence, historical statistics, and customizable game settings.

---

## ✨ Key Features
### 🎮 Gameplay Mechanics
- **Dynamic Game Setup:** Players can customize their name and choose between 1 or 2 card decks for varied difficulty.
- **Core Actions:** Standard Hit (draw card) and Stand (hold position) mechanics.
- **Smart Dealer:** Automated dealer logic adhering to casino rules (must hit on soft 16, stand on hard 17).
- **Ace Handling:** Intelligent score calculation where Aces dynamically adjust between 1 and 11 points to prevent busting.

### 🖥️ UI/UX Enhancements
- **Swing GUI:** Responsive layout using BorderLayout and GridLayout.
- **Visual Feedback:** Color-coded cards (Red/Black suits) and distinct panels for Dealer/Player.
- **Enhanced UX:** Always-On-Top Dialogs: Critical game prompts (New Game, Game Over) force focus to ensure a smooth flow.
- **Custom Application Icon:** Integrated native taskbar and window icon loaded from resources.
- **Statistics Dashboard:** A dedicated window using JList to view detailed logs of the last 10 rounds (Winner, Scores, Hands).

### 💾 Persistence & Data
- **Save/Load System:** Full game state serialization (gamestate.dat) allowing players to pause and resume sessions.
- **Crash Recovery:** Robust file handling with error logging and user feedback.

---

## 🛠️ Technology Stack
* **Language: Java (JDK 25 recommended, min. JDK 25)**
* **Build System: Gradle**
* **GUI Framework: Java Swing**
* **Testing: JUnit 5**
* **Data Storage: Java Serialization API**

---

## 📂 Project Structure
The project follows the standard Gradle directory structure, with the source code divided into logical packages:
```
BlackjackProject/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── blackjack/
│   │   │       ├── Main.java
│   │   │       ├── gui/
│   │   │       |   |── GameFrame.java 
│   │   │       |   └── StatisticsFrame.java
│   │   │       ├── io/
│   │   │       │   └── SaveManager.java
│   │   │       ├── logic/
│   │   │       |   |── BlackjackGame.java
│   │   │       |   └── RoundResult.java
│   │   │       └── model/
│   │   │           ├── Card.java
│   │   │           ├── Dealer.java
│   │   │           ├── Deck.java
│   │   │           ├── Player.java
│   │   │           ├── Rank.java
│   │   │           └── Suit.java
│   │   └── resources/
│   │       └── icon.png
│   └── test/
│       └── java/
│           └── blackjack/
│               ├── io/
│               │   └── SaveManagerTest.java
│               ├── logic/
│               │   └── BlackjackGameTest.java
│               └── model/
│                   ├── DealerTest.java
│                   ├── CardTest.java
│                   ├── DeckTest.java
│                   └── PlayerTest.java
└── saves/
    └── gamestate.dat

```

---

## ⚙️ Setup & Usage
The project includes the Gradle Wrapper, ensuring a consistent build environment without manual Gradle installation.

### Prerequisites
Java JDK 25 (or newer) installed and configured in JAVA_HOME.
### 1. Clone the Repository
```
git clone https://github.com/hajdu-patrik/Black-Jack-21_JAVA.git
cd Black-Jack-21_JAVA
```

### 2. Build the Project
**Windows:**
```
gradlew build
```

**Linux/Mac:**
```
./gradlew build
```

### 3. Run the game
**Windows:**
```
gradlew run
```

**Linux/Mac:**
```
./gradlew run
```

---

## 🧪 Testing Strategy
The application maintains high test coverage using JUnit 5, focusing on business logic and edge cases.

### Key Test Cases:
- **Deck Integrity:** Verifying card counts for 1-deck (52 cards) and 2-deck (104 cards) modes.
- **Ace Logic:** Testing flexible scoring (e.g., Ace + King = 21, Ace + 5 + 10 = 16).
- **Game Flow:** Simulating Player Bust, Dealer Bust, and Win/Loss conditions.
- **Persistence:** Verifying that saved and reloaded game states are identical.

### Run the test
**Windows:**
```
gradlew test --info
```

**Linux/Mac:**
```
./gradlew test --info
```

---

## 📦 Releases

| Version | Description | Download |
| :--- | :--- | :--- |
| **v1.0.0** | **Blackjack - 21 v1.0.0** - Initial stable release. Includes the full Java Swing game with save/load functionality, statistics dashboard, and JUnit tests. | [Source code (zip)](https://github.com/hajdu-patrik/Black-Jack-21_JAVA/archive/refs/tags/v1.0.0.zip) |

---

## 📄 License

Copyright (c) Hajdú Patrik Zsolt. All rights reserved.

Published for demonstration and portfolio purposes only. Using any part of this code as a solution for an academic assignment is strictly prohibited. See [LICENSE.md](LICENSE.md) for the full terms.
