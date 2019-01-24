package com.github.moribund.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonParser {

    private final String filePath;
    private final Class clazz;

    public <T> JsonParser(String filePath, Class<T> classOfT) {
        this.filePath = filePath;
        clazz = classOfT;
    }

    public <T> T getFileLoaded() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Gson gson = new Gson();

            T object = gson.fromJson(br, (Class<T>) clazz);
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}