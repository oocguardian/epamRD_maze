package com.epam.maze.util;

import com.epam.maze.util.entity.Coordinates;

import java.util.LinkedList;

public class MazeSolver {
    private int wx;
    private int wy;

    private int endX;
    private int endY;

    private int[][] mazeMap;

    private LinkedList<Coordinates> resolvePath = new LinkedList<>();

    private boolean[][] visited;
    private boolean done;

    public LinkedList<Coordinates> solve(int startX, int startY, int endX, int endY, int[][] mazeMap) {
        this.endX = endX;
        this.endY = endY;

        this.mazeMap = mazeMap;

        this.wx = mazeMap.length;
        this.wy = mazeMap[0].length;

        visited = new boolean[wx][wy];

        solve(startX, startY);
        return resolvePath;
    }

    private void solve(int startX, int startY) {
        if (startX == wx + 1 || startY == wy + 1) return;
        if (done || visited[startX][startY]) return;

        visited[startX][startY] = true;

        // add path point to solve array
        Coordinates coordinates = new Coordinates(startX, startY);
        resolvePath.addLast(coordinates);

        // reached end
        if (endX - 1 == startX && endY - 1 == startY) done = true;

        if ((mazeMap[startX][startY] & 1) != 0) solve(startX, startY - 1);
        if ((mazeMap[startX][startY] & 4) != 0) solve(startX + 1, startY);
        if ((mazeMap[startX][startY] & 2) != 0) solve(startX, startY + 1);
        if ((mazeMap[startX][startY] & 8) != 0) solve(startX - 1, startY);

        if (done) return;

        resolvePath.removeLast();
    }

}
