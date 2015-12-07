package com.epam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MazeViewer {
    public static final int CHARS_FREQUENCY_RATE = 30;
    public static final int IMAGE_SIZE_SCALE = 15;

    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private Rectangle cell;

    private final int x;
    private final int y;
    private final int[][] maze;
    private LinkedList<Coordinates> resolvePath;

    private static final Random rand = new Random();

    public MazeViewer(int[][] maze, LinkedList<Coordinates> resolvePath) {
        this.x = maze.length;
        this.y = maze[0].length;
        this.maze = maze;
        this.resolvePath = resolvePath;
        init();
    }

    private void init() {
        int imageWidth = x * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE * 2;
        int imageHeight = y * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE * 2;

        bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, imageWidth, imageHeight);

        cell = new Rectangle(IMAGE_SIZE_SCALE, IMAGE_SIZE_SCALE);

        g2d.translate(IMAGE_SIZE_SCALE, IMAGE_SIZE_SCALE);
    }

    public void drawMaze() throws IOException {
        g2d.setColor(Color.black);

        for (int fy = 0; fy < y; fy++) {
            for (int fx = 0; fx < x; fx++) {
                int cx = fx * IMAGE_SIZE_SCALE;
                int cy = fy * IMAGE_SIZE_SCALE;
                int step = IMAGE_SIZE_SCALE;

                if ((maze[fx][fy] & 1) == 0) g2d.drawLine(cx, cy, cx + step, cy);
                if ((maze[fx][fy] & 8) == 0) g2d.drawLine(cx, cy, cx, cy + step);
                if ((maze[fx][fy] & 2) == 0) g2d.drawLine(cx, cy + step, cx + step, cy + step);
                if ((maze[fx][fy] & 4) == 0) g2d.drawLine(cx + step, cy, cx + step, cy + step);
            }
        }
    }

    public void drawRect() {
        g2d.setColor(Color.BLACK);
        cell.setLocation(0, 0);
        g2d.draw(cell);
    }

    public void drawResolve() {
        for (Coordinates coordinates : resolvePath) {
            int curX = coordinates.getX() * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE / 4;
            int curY = coordinates.getY() * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE / 4;
            int size = IMAGE_SIZE_SCALE / 2;

            g2d.setColor(Color.BLUE);
            g2d.fillOval(curX, curY, size, size);
        }
    }

    public void drawWord(String word) {
        LinkedHashSet<Coordinates> fakeCharsCoordinates = new LinkedHashSet<>();
        int currentIndex = 0;
        char[] chars = word.toCharArray();

        //distance between characters on the path
        int step = resolvePath.size() / (word.length() + 1);

        Font font = scaleFont(word.substring(0, 1).toUpperCase(), cell, g2d);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        for (char curChar : chars) {
            currentIndex += step;
            Coordinates coordinates = resolvePath.get(currentIndex);
            int curX = coordinates.getX() * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE / 4;
            int curY = coordinates.getY() * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE - 2;

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(curChar).toUpperCase(), curX, curY);
        }

        //getting counter of fake chars
        int randomCharsCount = x * y / CHARS_FREQUENCY_RATE;

        for (int i = 0; i < randomCharsCount; i++) {
            boolean doNotDraw = false;

            char c = (char) (65 + rand.nextInt(25));
            int cx = rand.nextInt(x);
            int cy = rand.nextInt(y);

            Coordinates currentCoordinates = new Coordinates(cx, cy);

            int curX = cx * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE / 4;
            int curY = cy * IMAGE_SIZE_SCALE + IMAGE_SIZE_SCALE - 2;


            if (!resolvePath.contains(currentCoordinates) && !fakeCharsCoordinates.contains(currentCoordinates)){
                fakeCharsCoordinates.add(currentCoordinates);
                g2d.drawString(String.valueOf(c).toUpperCase(), curX, curY);
            }
        }

    }

    public Font scaleFont(String text, Rectangle rect, Graphics g) {
        float fontSize = 15.0f;

        Font font = g.getFont().deriveFont(fontSize);
        int width = g.getFontMetrics(font).stringWidth(text);
        fontSize = (rect.width / width) * fontSize;
        return g.getFont().deriveFont(fontSize);
    }

    public void saveToFile(String path) throws IOException {
        g2d.dispose();
        File file = new File(path);
        ImageIO.write(bufferedImage, "png", file);
    }

}
