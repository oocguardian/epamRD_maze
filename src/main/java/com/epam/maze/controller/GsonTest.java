package com.epam.maze.controller;

import com.epam.Coordinates;
import com.epam.MazeGenerator;
import com.epam.MazeViewer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

@Controller
public class GsonTest {
    public static final int MAZE_X_SIZE = 40;
    public static final int MAZE_Y_SIZE = 40;
    public static final int CELL_SIZE = 15;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String getMazeData() throws IOException {
        MazeGenerator mazeGenerator = new MazeGenerator(MAZE_X_SIZE, MAZE_Y_SIZE);
        int[][] maze = mazeGenerator.getMaze();

        JsonObject jsonObject = new JsonObject();

        JsonArray mazeMap = new JsonArray();
        for (int[] row : maze) {
            JsonArray childArray = new JsonArray();
            for (int cell : row){
                childArray.add(cell);
            }
            mazeMap.add(childArray);
        }

        LinkedList<Coordinates> resolvePath = mazeGenerator.solve(0, 0, 40, 40);
        Map<Coordinates, String> charsMap = mazeGenerator.getCharsToDraw("word", resolvePath, 25);

        makePng(mazeGenerator);

        jsonObject.add("maze", mazeMap);
        jsonObject.addProperty("cell_size", CELL_SIZE);

        JsonArray chars = new JsonArray();

        for (Coordinates coordinates : charsMap.keySet()){
            JsonObject obj = new JsonObject();
            obj.addProperty("char", charsMap.get(coordinates));

            JsonArray array = new JsonArray();
            array.add(coordinates.getX());
            array.add(coordinates.getY());

            obj.add("coordinates", array);
            chars.add(obj);
        }

        jsonObject.add("chars", chars);

        return jsonObject.toString();
    }

    private void makePng(MazeGenerator mazeGenerator) throws IOException {
        MazeViewer mv = new MazeViewer(mazeGenerator.getMaze(), mazeGenerator.getResolvePath());
        mv.drawMaze();
        mv.drawWord("word");
        mv.saveToFile("test.png");
    }

}
