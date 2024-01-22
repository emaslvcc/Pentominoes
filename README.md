## 3D Knapsack
This code presents a computer application with a user-friendly interface and a 3D visualizer to solve a 3D knapsack problem using parcels of type A, B and C, and pentominoes of type L, P and T, each with different sizes and values.

## Table of Contents
1. Installation
2. Description

## Installation
**Prerequisites:**
- [Java Development Kit (JDK)](https://www.oracle.comjavatechnologies/downloads/)
- [JavaFX](https://openjfx.io/)
- Code Editor

**Clone the repository**
1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.

```bash
cd path/to/your/directory
```
3. Clone the repository
```bash
git clone https://gitlab.maastrichtuniversity.nl/bcs_group05_2023/project-1-1.git
```
**Run the application**
1. Add the JavaFX libraries to the Java Project.
2. Run the _App.java_ file.

## Description

### GUI / 3D Visualizer ###
**_JavaFX1.java_ :**
This class handles all the graphics displayed in the application. It extends _Application_ (The class from which JavaFX applications extend).
All the classes which the program consists of are connected to this class.

**_App.java_ :**
This class launches the _JavaFX1.java_ application.

### Question A ###
**_Builder.java_ :**
This class includes the methods to support the search of a solution for Question A.

**_Parcel.java_ :**
This class builds the parcel object.

### Question B ###
**_GreedyAlgorithm.java_ :**
This class contains the greedy algorithm for solving Question B. It implements _Runnable_, which helps the algorithm run.

**_Parcel.java_ :**
This class builds the parcel object.

### Question C ###
**_BruteForceAlgorithm.java_ :**
This class contains the brute force algorithm used to solve Question C.

**_PentominoBuilder.java_**, **_PentominoDatabase.java_**, and **_pentominos.csv_ :**

These classes were authored by the Department of Data Science and Knowledge Engineering (DKE) of Maastricht Universty. In short, they handle all the logic behind the pentominoes (which ones exist, how they rotate, their IDs, among other things).

### Question D ###
**_GreedyPentominoes.java_ :**
This class holds the greedy algorithm previously used in Question B, now adapted to work with pentomino shapes.

**_Pentominoes.java_ :**
This class builds the Pentominoes object used in the greedy algorithm. It is a modified and adapted version of the _Parcel.java_ class of Question A.

## Institute

[University of Maastricht](https://www.maastrichtuniversity.nl/nl)