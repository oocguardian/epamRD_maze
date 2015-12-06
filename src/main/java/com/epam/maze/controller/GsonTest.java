package com.epam.maze.controller;

import com.epam.MazeGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GsonTest {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String toJson(){
        MazeGenerator mazeGenerator = new MazeGenerator(50, 50);
        int[][] maze = mazeGenerator.getMaze();

        JsonObject jsonObject = new JsonObject();

        JsonArray parrentArray = new JsonArray();
        for (int[] child : maze) {
            JsonArray childArray = new JsonArray();
            for (int element : child){
                childArray.add(element);
            }
            parrentArray.add(childArray);
        }

        jsonObject.add("maze", parrentArray);
        return jsonObject.toString();
    }

}
