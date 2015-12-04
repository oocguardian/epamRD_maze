package com.epam.trash.mazemaster;

class Mazes {
    static final int ROWS = 40;
    static final int COLS = 40;

    public static void main(String[] args) {


//        System.out.println("Recursive Backtracker com.epam.trash.Maze and Solution:");
//
//        Backtracker recBack = new Backtracker(ROWS, COLS);
//        recBack.makeMaze();
//        recBack.printMaze();
//
//        System.out.println();
//
//        Solver recBackSol = new Solver(recBack.getMaze());
//        recBackSol.solveMaze();
//        recBackSol.printSolution();
//
//        System.out.println("\n");


//        System.out.println("Recursive Division com.epam.trash.Maze and Solution:");
//
//        RecursiveDivision recDiv = new RecursiveDivision(ROWS, COLS);
//        recDiv.makeMaze();
//        recDiv.printMaze();
//
//        System.out.println();
//
//        Solver recDivSol = new Solver(recDiv.getMaze());
//        recDivSol.solveMaze();
//        recDivSol.printSolution();
//
//        System.out.println("\n");
//
//
//        System.out.println("Eller's Algorithm com.epam.trash.Maze and Solution:");
//
//        Ellers ell = new Ellers(ROWS, COLS);
//        ell.makeMaze();
//        ell.printMaze();
//
//        System.out.println();
//
//        Solver ellSol = new Solver(ell.getMaze());
//        ellSol.solveMaze();
//        ellSol.printSolution();
//
//        System.out.println("\n");
//
//
        System.out.println("Growing Tree Algorithm com.epam.trash.Maze and Solution:");

        GrowingTree gTree = new GrowingTree(ROWS, COLS);
        gTree.makeMaze();
        gTree.printMaze();

        System.out.println();

        Solver gTreeSol = new Solver(gTree.getMaze());
        gTreeSol.solveMaze();
        gTreeSol.printSolution();

        System.out.println("\n");

    }
}
