# Tetris

The present code entails a computer application with a user-friendly interface to play the Tetris-style game using 12 different pentomino-shapes that appear for the player in random order, each with the same probability. Furthermore, there are 3 different bots that are able to play the game, and 1 bot that demonstrates the optimal ordering of pentominoes.

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
2. Run the _TetrisGUI_ file, and an option to pick which bot you wish to use will be displayed once you click 'Start'.

## Description

### Human Gameplay ###

This section is referering to the _TetrisGame_ folder.

**_Game.java_**

This class handles all the graphics to display a certain state.

It extends _JPanel_ (corresponding to a panel of the GUI's main frame), and implements a _KeyListener_ to handle keyboard input.

1. A **_Game_ constructor** is implemented to handle the general Tetris game logic. It sets up a game board, initializes pentomino shapes, and manages their descent by means of a timer (looper). The timer checks for collisions with existing pentominoes, and handles Game Over scenarios, by displaying a graphical message and resetting the game.

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

**_PentominoBuilder.java_**, **_PentominoDatabase.java_**, and **_pentominos.csv_**

These classes were authored by the Department of Data Science and Knowledge Engineering (DKE) of Maastricht Universty. In short, they handle all the logic behind the pentominoes (which ones exist, how they rotate, their IDs, among other things).

### Bots ###

This section is referering to the _TetrisBot_ folder.

**_TetrisGUI.java_**

This class handles the graphical display of the game on a Graphical User Interface (GUI). Therefore, it extends _JPanel_ and makes use of various _Swing_ components.

1. A **_TetrisGUI_ constructor** that handles all the visual components of the GUI, and creates an object of the selected bot for it to run.

2. **_displayCombinedHighScores()_** displays the high scores for all the bots.

3. **_showInstructions()_** method displays the game's instructions.

4. **_createMenuButton()_** method creates a button with pre-defined characteristics.

5. **_createMenuButtonWithImage()_** creates a button that can display an image.

**_Bot1.java_**

This class handles the implementation of a bot that considers the next possible move, and bases its pick on the number of non-empty cells in a column.

_Bot1_ extends _JPanel_, and implements a _KeyListener_ to handle keyboard input.

1. A **_Bot1_** constructor is implemented to handle the general bot's logic. It sets up a game board, initializes pentomino shapes, and manages their descent by means of a timer (looper). The timer checks for collisions with existing pentominoes, and handles Game Over scenarios, by displaying a graphical message and resetting the game.

2. **_paintComponent()_** method is responsible for rendering the graphical representation of the game within a Java Swing component, updating the display as the game progresses.

3. **_advanceToNextPentomino()_** advances to the following random pentomino.

4. **_getColorID()_** method decodes the ID of a pentomino into the corresponding color.

5. **_reset()_** method resets the game to its initial state.

6. **_start()_** method starts the game.

7. **_keyPressed()_** is overriden to handle keyboard input "P", which corresponds to pausing the game.

8. **_moveRight()_**, **_moveLeft()_**, and **_moveDown()_** check if the pentomino piece can make the respective movements.

9. **_rotate()_** rotates the pentomino by changing its mutation.

10. **_highScores()_** returns the bot's scores.

11. **_shuffleOrder()_** shuffles the database to further randomize the pentominoes.

12. **_CheckBestOption()_** is the heart of this bot's decision-making. It determines what the best pentomino positioning is, which depends on the number of non-empty rows.

**_Bot2.java_**

This class handles the implementation of a bot that considers the next possible move, and bases its pick on various variables (number of gaps, columns height average, height difference between the highest column and the lowest column, maximum height difference between consecutive columns, and amount of non-removable rows).

_Bot2_ extends _JPanel_, and implements a _KeyListener_ to handle keyboard input.

1. A **_Bot2_** constructor is implemented to handle the general bot's logic. It sets up a game board, initializes pentomino shapes, and manages their descent by means of a timer (looper). The timer checks for collisions with existing pentominoes, and handles Game Over scenarios, by displaying a graphical message and resetting the game.

It makes use of methods previously explained in the _Bot1_ section:
1. **_paintComponent()_**
2. **_advanceToNextPentomino()_**
3. **_getColorID()_**
4. **_reset()_**
5. **_start()_**
6. **_keyPressed()_**
7. **_moveRight()_**, **_moveLeft()_**, and **_moveDown()_**
8. **_rotate()_**
9. **_highScores()_**
10. **_shuffleOrder()_**

Furthermore, it implements the following methods:

1. **_bestScore()_** is the heart of this bot's decision-making. It determines what the best pentomino positioning is, which depends on the variables previously mentioned.

2. **_gapCount()_** calculates the amount of gaps on the board.

3. **_averageHeight()_** calculates the average height of the columns.

4. **_columnHeightDifference()_** calculates the difference between the highest column and the lowest column.

5. **_consecutiveHeightDifference()_** calculates the maximum height difference between consecutive columns.

6. **_removableRow()_** calculates the amount of non-removable rows.

7. **_calculateScore()_** multiplies each propertie by a pre-defined weight attributed to it, and sums up all the values to calculate a score.

**_Bot3.java_**

This class handles the implementation of a bot that considers the next 3 possible move, and bases its pick on the number of non-empty cells in a column.

_Bot3_ extends _JPanel_, and implements a _KeyListener_ to handle keyboard input.

1. A **_Bot3_** constructor is implemented to handle the general bot's logic. It sets up a game board, initializes pentomino shapes, and manages their descent by means of a timer (looper). The timer checks for collisions with existing pentominoes, and handles Game Over scenarios, by displaying a graphical message and resetting the game.

It makes use of methods previously explained in the _Bot1_ section:
1. **_paintComponent()_**
2. **_advanceToNextPentomino()_**
3. **_getColorID()_**
4. **_reset()_**
5. **_start()_**
6. **_keyPressed()_**
7. **_moveRight()_**, **_moveLeft()_**, and **_moveDown()_**
8. **_rotate()_**
9. **_highScores()_**
10. **_shuffleOrder()_**

Furthermore, it implements the following methods:

1. **_CheckBestOption()_** is the heart of this bot's decision-making. It determines what the best pentomino positioning is, which depends on the number of non-empty rows.

2. **_EvaluateNextThreePentominoes_** evaluates positionings for the following three pentominoes.

3. **_nextMoveRight_** and **_nextMoveLeft_** check if the next pentomino can move to the right and if it can move to the left, respectively.

**_BestOrderBot.java_**

This class handles the implementation of a bot that demonstrates the optimal ordering gameplay.

It makes use of methods previously explained in the _Bot1_ section:
1. **_paintComponent()_**
2. **_advanceToNextPentomino()_**
3. **_getColorID()_**
4. **_reset()_**
5. **_start()_**
6. **_keyPressed()_**
7. **_moveRight()_**, **_moveLeft()_**, and **_moveDown()_**
8. **_rotate()_**
9. **_highScores()_**

Furthermore, it implements a method **_placePentomino()_**, that essentially palces the pentomino at its previously designated spot.

**_PentominoBuilder.java_**, **_PentominoDatabase.java_**, and **_pentominos.csv_**

These classes were authored by the Department of Data Science and Knowledge Engineering (DKE) of Maastricht Universty. In short, they handle all the logic behind the pentominoes (which ones exist, how they rotate, their IDs, among other things).

**_BestOrderDatabase.java_**

This class is identical to _PenotominoDatabase.java_. It was implemented because the other bot classes shuffle the initial database to further randomize the pentominoes. Hence, for the _BestOrderBot_ to work properly, the database could not be shuffled, and this class was added.

## Institute

[University of Maastricht](https://www.maastrichtuniversity.nl/nl)
