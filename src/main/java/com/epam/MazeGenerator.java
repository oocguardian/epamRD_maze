package com.epam;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    private boolean[][] visited;
    private boolean done;

    private int endX;
    private int endY;
    private LinkedList<Coordinates> resolvePath = new LinkedList<>();

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];

        visited = new boolean[x][y];

        generateMaze(0, 0);
    }

    public void displayStr() {
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    public LinkedList<Coordinates> solve(int startX, int startY, int endX, int endY) {
        this.endX = endX;
        this.endY = endY;
        solve(startX, startY);
        return resolvePath;
    }

    private void solve(int startX, int startY) {
        if (startX == x + 1 || startY == y + 1) return;
        if (done || visited[startX][startY]) return;

        visited[startX][startY] = true;

        // add path point to solve array
        Coordinates coordinates = new Coordinates(startX, startY);
        resolvePath.addLast(coordinates);

        // reached end
        if (endX - 1 == startX && endY - 1 == startY) done = true;

        if ((maze[startX][startY] & 1) != 0) solve(startX, startY - 1);
        if ((maze[startX][startY] & 4) != 0) solve(startX + 1, startY);
        if ((maze[startX][startY] & 2) != 0) solve(startX, startY + 1);
        if ((maze[startX][startY] & 8) != 0) solve(startX - 1, startY);

        if (done) return;

        resolvePath.removeLast();
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

    public int[][] getMaze() {
        return maze;
    }

    public LinkedList<Coordinates> getResolvePath() {
        return resolvePath;
    }

    public Map<Coordinates, String> getCharsToDraw(String word, LinkedList<Coordinates> resolve, int charDistance) {
        int currentIndex = 0;
        Random rand = new Random();
        HashMap<Coordinates, String> charsToDraw = new LinkedHashMap<>();

        char[] chars = word.toCharArray();

        //distance between characters on the resolve path
        int step = resolve.size() / (word.length() + 1);

        for (char curChar : chars) {
            currentIndex += step;

            Coordinates coordinates = resolve.get(currentIndex);
            charsToDraw.put(coordinates, String.valueOf(curChar).toUpperCase());
        }

        //getting counter of fake chars
        int randomCharsCount = x * y / charDistance;

        for (int i = 0; i < randomCharsCount; i++) {

            //create random char
            char c = (char) (65 + rand.nextInt(25));

            int cx = rand.nextInt(x);
            int cy = rand.nextInt(y);

            Coordinates currentCoordinates = new Coordinates(cx, cy);

            if (!resolvePath.contains(currentCoordinates) && !charsToDraw.keySet().contains(currentCoordinates))
                charsToDraw.put(currentCoordinates, String.valueOf(c).toUpperCase());
        }
        return charsToDraw;
    }


    public static void main(String[] args) throws IOException {
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 100;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 60;
        MazeGenerator maze = new MazeGenerator(x, y);
        LinkedList<Coordinates> path = maze.solve(0, 0, 100, 60);

        System.out.println(maze.getCharsToDraw("word", path, 20));

        MazeViewer mv = new MazeViewer(maze.getMaze(), maze.getResolvePath());
        mv.drawMaze();
        mv.drawWord("something");
        mv.saveToFile("test.png");
    }

}