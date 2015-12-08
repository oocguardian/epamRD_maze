package com.epam.maze.util;

import com.epam.maze.util.entity.Coordinates;
import com.epam.maze.util.entity.Maze;

import java.util.*;

public class MazeGenerator {
    private int wx;
    private int wy;

    private int[][] mazeMap;

    public Maze generateMaze(int wx, int wy, int startX, int startY, int endX, int endY) {
        MazeSolver mazeSolver = new MazeSolver();
        mazeMap = new int[wx][wy];
        this.wx = wx;
        this.wy = wy;

        generateMazeMap(0, wy / 2);
        LinkedList<Coordinates> resolvePath = mazeSolver.solve(startX, startY, endX, endY, mazeMap);
        return new Maze(wx, wy, mazeMap, resolvePath);
    }

    private void generateMazeMap(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, wx) && between(ny, wy)
                    && (mazeMap[nx][ny] == 0)) {
                mazeMap[cx][cy] |= dir.bit;
                mazeMap[nx][ny] |= dir.opposite.bit;
                generateMazeMap(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

}