package com.github.moribund.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A parser class that parses a JSON file.
 */
public class JsonParser {

    /**
     * A single instance of Gson loaded.
     */
    private static final Gson GSON = new Gson();

    /**
     * The path of the file to parse.
     */
    private final String filePath;

    /**
     * The class that is to be serialized for the JSON file.
     */
    private final Class clazz;

    public <T> JsonParser(String filePath, Class<T> classOfT) {
        this.filePath = filePath;
        clazz = classOfT;
    }

    /**
     * Gets the file loaded.
     * @param <T> The generic class to use for parsing the JSON file.
     * @return The complete file loaded with its respective generic type.
     */
    public <T> T getFileLoaded() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            T object = GSON.fromJson(br, (Class<T>) clazz);
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}