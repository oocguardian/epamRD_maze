package com.epam.maze.util;

import com.epam.maze.util.entity.Coordinates;
import com.epam.maze.util.entity.Maze;

import java.util.*;

public class PlaceholderGenerator {
    private Random rand = new Random();

    public Map<Coordinates, String> createCharsMap(Maze maze, Map<Integer, String> shuffledWord, int distance) {
        int wx = maze.getWx();
        int wy = maze.getWy();
        List<Coordinates> resolve = maze.getResolvePath();

        Map<Coordinates, String> charsToDraw = new LinkedHashMap<>();

        //distance between characters on the resolve path
        int step = resolve.size() / (shuffledWord.size() + 1);

        int resolvePointIndex = 0;
        for (Integer charKey : shuffledWord.keySet()) {
            resolvePointIndex += step;

            Coordinates coordinates = resolve.get(resolvePointIndex);
            charsToDraw.put(coordinates, shuffledWord.get(charKey));
        }

        //getting counter of fake chars
        int randomCharsCount = wx * wy / distance;

        for (int i = 0; i < randomCharsCount; i++) {
            //create random char
            char c = (char) (65 + rand.nextInt(25));

            int cx = rand.nextInt(wx);
            int cy = rand.nextInt(wy);

            Coordinates currentCoordinates = new Coordinates(cx, cy);

            if (!resolve.contains(currentCoordinates) && !charsToDraw.keySet().contains(currentCoordinates))
                charsToDraw.put(currentCoordinates, String.valueOf(c).toUpperCase());
        }
        return charsToDraw;
    }

    public Map<Integer, String> createShuffledWord(String word) {
        Map<Integer, String> charsMap = new LinkedHashMap<>();
        Map<Integer, String> shuffledMap = new LinkedHashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            charsMap.put(i + 1, String.valueOf(curChar).toUpperCase());
        }

        List<Integer> keys = new ArrayList<>(charsMap.keySet());
        Collections.shuffle(keys);

        for (Integer key : keys) {
            shuffledMap.put(key, charsMap.get(key));
        }

        return shuffledMap;
    }

    public Map<Coordinates, String> createNumbersMap(Maze maze, Map<Integer, String> shuffledWord, int distance) {
        int wx = maze.getWx();
        int wy = maze.getWy();
        List<Coordinates> resolve = maze.getResolvePath();

        Map<Coordinates, String> numbersToDraw = new LinkedHashMap<>();

        //distance between characters on the resolve path
        int step = resolve.size() / (shuffledWord.size() + 1);

        int resolvePointIndex = 0;
        for (Integer index : shuffledWord.keySet()) {
            resolvePointIndex += step;

            Coordinates coordinates = resolve.get(resolvePointIndex);
            numbersToDraw.put(coordinates, String.valueOf(index));
        }

        //getting counter of fake chars
        int randomCharsCount = wx * wy / distance;

        for (int i = 0; i < randomCharsCount; i++) {
            //create random char
            int number = rand.nextInt(20);

            int cx = rand.nextInt(wx);
            int cy = rand.nextInt(wy);

            Coordinates currentCoordinates = new Coordinates(cx, cy);

            if (!resolve.contains(currentCoordinates) && !numbersToDraw.keySet().contains(currentCoordinates))
                numbersToDraw.put(currentCoordinates, String.valueOf(number));
        }
        return numbersToDraw;
    }

}
