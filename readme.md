# Interstellar Stick Hero

Interstellar Stick Hero is a JavaFX-based game where players navigate a character through various challenges using a dynamically growing stick.

## Assumptions

- The game is developed using JavaFX.
- The game's state (like high score and point count) is stored locally in text files.
- The high score is a single integer value.
- The game uses the Singleton design pattern for the `Stick` class to spawn single stick at a moment.
- If the player has 2 or more fireballs (cherries), then their next death will be skipped and 2 fireballs will be deducted.
- 2 cases of death, if stick is less or beyond the next platform, and if one is flipped while motion and goes into the second pillar.
- Stick can grow abruptly to increase the difficulty at times.
- Serializable is used to store the instance.
- Thread is used in stick increase and decrease.

## How to play:
- Press play, hold the right click of the mouse to grow stick and release it to stop and drop
- Press space bar to flip once
- You can pause in between and also save/exit and load the game.
- High score gets saved in document

## Key Classes

### GameController

The central controller for the game, managing game states, transitions between screens, and game logic.

- **Key Functions**:
    - Scene transitions (to main, home, and pause screens).
    - Managing game states and interactions.
    - Handling user inputs and game events.

### Stick

Represents the stick used by the player in the game.

- **Design Pattern**: Singleton
- **Key Functions**:
    - Ensuring a single instance of the stick throughout the game.
    - Managing stick properties such as length and power-up status.

### StickHero

Represents the main character or hero of the game.

- **Key Functions**:
    - Handling movements and animations of the hero.
    - Interacting with the game environment (e.g., sticks and pillars).

### Pillar

Represents the pillars encountered in the game.

- **Key Functions**:
    - Generating pillars with random widths and positions.
    - Managing animations and properties of the pillars.

### SaveGameState

Handles the serialization of the game's state for saving/loading purposes.

- **Key Functions**:
    - Storing and retrieving the game's score and cherry count.

### highScore

Manages the high score functionality.

- **Key Functions**:
    - Storing and retrieving the highest score achieved in the game.

### ScreenController

Manages the transitions between different screens in the game.

- **Key Functions**:
    - Switching between main, home, and pause screens based on user interactions.

## Design Patterns

### Singleton

Implemented in the `Stick` class to ensure that there is only one instance of the stick throughout the game. This pattern is critical for maintaining consistent game logic and state related to the stick.

## Usage

To play the game, run the `Main` class. Navigate through the game using the keyboard and mouse inputs as defined in the `GameController` and other event-handling classes.

## Notes

- The game's visual and interactive elements are designed using JavaFX.
- Game state persistence is achieved through local file storage.
- Exception handling is implemented to ensure robustness, particularly in file operations and input parsing.
- Background music credits: https://youtu.be/O43jH_KhKBM?si=r_L6asGexy6_NX0Y
---

**Interstellar Stick Hero** - An adventurous journey through space with sticks!
