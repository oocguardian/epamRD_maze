import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */

public class MazeGenerator {
    public static final int CHARS_FREQUENCY_RATE = 20;

    private final int x;
    private final int y;
    private final int[][] maze;
    private boolean[][] visited;
    private boolean done;

    private LinkedList<Integer[]> solvePath = new LinkedList<>();
    private static final Random rand = new Random();

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        StdDraw.setXscale(-1, x + 1);
        StdDraw.setYscale(-1, y + 1);
        maze = new int[this.x][this.y];

        visited = new boolean[x][y];

        generateMaze(0, 0);
    }

    public void display() {
        StdDraw.show(0);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int fy = 0; fy < y; fy++) {
            for (int fx = 0; fx < x; fx++) {
                if ((maze[fx][fy] & 1) == 0) StdDraw.line(fx, y - fy + 1, fx + 1, y - fy + 1);
                if ((maze[fx][fy] & 8) == 0) StdDraw.line(fx, y - fy, fx, y - fy + 1);
                if ((maze[fx][fy] & 2) == 0) StdDraw.line(fx, y - fy, fx + 1, y - fy);
                if ((maze[fx][fy] & 4) == 0) StdDraw.line(fx + 1, y - fy, fx + 1, y - fy + 1);
            }
        }
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

    private void showWord(String word) {
        int currentIndex = 0;
        char[] chars = word.toCharArray();

        //distance between characters on the path
        int step = solvePath.size() / (word.length() + 1);

        for (char curChar : chars) {
            currentIndex += step;
            Integer[] coordinates = solvePath.get(currentIndex);
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.filledCircle(coordinates[0] + 0.5, y - coordinates[1] + 0.5, 0.25);
            StdDraw.show(0);
        }

        //getting counter of fake chars
        int randomCharsCount = x * y / CHARS_FREQUENCY_RATE;

        for (int i = 0; i < randomCharsCount; i++) {
            boolean onTheSolvePath = false;
            char c = (char) rand.nextInt(26);
            int cx = rand.nextInt(x);
            int cy = rand.nextInt(y);

            for (Integer[] curCoordinate : solvePath){
                if (Arrays.equals(curCoordinate, new Integer[]{cx, cy})){
                    onTheSolvePath = true;
                    break;
                }
            }

            if (!onTheSolvePath){
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(cx + 0.5, y - cy + 0.5, 0.25);
                StdDraw.show(0);
            }
        }

    }

    private void solve(int startX, int startY) {
        if (startX == x + 1 || startY == y + 1) return;
        if (done || visited[startX][startY]) return;
        visited[startX][startY] = true;

//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.filledCircle(startX + 0.5, y - startY + 0.5, 0.25);
//        StdDraw.show();

        // add path point to solve array
        Integer[] coordinates = {startX, startY};
        solvePath.addLast(coordinates);

        // reached end
        if (x - 1 == startX && y - 1 == startY) done = true;

        if ((maze[startX][startY] & 1) != 0) solve(startX, startY - 1);
        if ((maze[startX][startY] & 4) != 0) solve(startX + 1, startY);
        if ((maze[startX][startY] & 2) != 0) solve(startX, startY + 1);
        if ((maze[startX][startY] & 8) != 0) solve(startX - 1, startY);

        if (done) return;

        solvePath.removeLast();

//        StdDraw.setPenColor(StdDraw.WHITE);
//        StdDraw.filledCircle(startX + 0.5, y - startY + 0.5, 0.25);
//        StdDraw.show();
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

    public static void main(String[] args) {
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 70;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 70;
        MazeGenerator maze = new MazeGenerator(x, y);
        maze.display();
        maze.solve(0, 0);
        maze.showWord("SomeWord");
        System.out.println(maze.solvePath);
        System.out.println(maze.solvePath.size());
        Integer[] cor = maze.solvePath.getLast();
        System.out.println(cor[0]);
        System.out.println(cor[1]);
    }

}