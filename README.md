# Tetris

The present code entails a computer application with a user-friendly interface to play the Tetris-style game using 12 different pentomino-shapes that appear for the player in random order, each with the same probability. Furthermore, there are 3 different bots that are able to play the game. 

## Table of Contents
1. Installation
2. Logic

## Installation

**Prerequisites:**
- [Java Development Kit (JDK)](https://www.oracle.comjavatechnologies/downloads/)
- Code Editor

**Clone the Repository**

1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.

```bash
cd path/to/your/directory
```
3. Clone the repository
```bash
git clone https://gitlab.maastrichtuniversity.nl/bcs_group05_2023/project-1-1.git
```
**Run the Human Gameplay**
1. Navigate to the _TetrisGame_ folder.
2. Run the _Game.java_ file.

**Run the Bots**
1. Navigate to the _TetrisBot_ folder.
2. Run _Bot1.java_/_Bot2.java_/_Bot3.java_ file (whichever bot you want).

## Description

### Human Gameplay ###

This section is referering to the _TetrisGame_ folder.

**_Game.java_**

This class handles all the graphics to display a certain state.

It extends _JPanel_ (corresponding to a panel of the GUI's main frame), and implements a _KeyListener_ to handle keyboard input.

1. A **_Game_ constructor** is implemented to handle the general Tetris game logic. It sets up a game board, initializes pentomino shapes, and manages their descent by means of a timer (looper). The timer checks for collisions with existing pentominos, and handles Game Over scenarios, by displaying a graphical message and resetting the game.

2. **_paintComponent()_** method is responsible for rendering the graphical representation of the Tetris game within a Java Swing component, updating the display as the game progresses.

3. **_advanceToNextPentomino()_** method advances to the next pentomino in the _pentominoOrder_ array.

4. **_getColorID()_** method decodes the ID of a pentomino into the corresponding color.

5. **_reset()_** method resets the game to its initial state.

6. **_start()_** method starts the game.

7. **_keyPressed()_** method is overriden to handle our game's specific keyboard input.

8. **_moveRight()_**, **_moveLeft()_**, and **_moveDown()_** check if the pentomino piece can make the respective movements.

9. **_rotate()_** rotates the pentomino by changing its mutation.

10. **_highScores()_** displays the user's scores.

**_TetrisGUI.java_**

This class handles the graphical display of the game on a Graphical User Interface (GUI). Therefore, it extends _JPanel_ and makes use of various _Swing_ components.

1. A **_TetrisGUI_ constructor** that handles all the visual components of the GUI.

2. **_createMenuButton()_** method creates a button with pre-defined characteristics.

3. **_showInstructions()_** method displays the game's instructions.

4. **_createMenuButtonWithImage()_** creates a button that can display an image.

**_PentominoBuilder.java_** and **_PentominoDatabase.java_**

These classes were authored by the Department of Data Science and Knowledge Engineering (DKE) of Maastricht Universty. In short, they handle all the logic behind the pentominos (which ones exist, how they rotate, their IDs, among other things).

### Bots ###

This section is referering to the _TetrisBot_ folder.

**_TetrisGUI.java_**

This class handles the graphical display of the game on a Graphical User Interface (GUI). Therefore, it extends _JPanel_ and makes use of various _Swing_ components.

1. A **_TetrisGUI_ constructor** that handles all the visual components of the GUI, and creates an object of the selected bot for it to run.

2. **_displayCombinedHighScores()_** displays the high scores for all the bots.

3. **_showInstructions()_** method displays the game's instructions.

4. **_createMenuButton()_** method creates a button with pre-defined characteristics.

5. **_createMenuButtonWithImage()_** creates a button that can display an image.

## Institute

[University of Maastricht](https://www.maastrichtuniversity.nl/nl)
