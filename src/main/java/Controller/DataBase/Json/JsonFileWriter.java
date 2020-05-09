package Controller.DataBase.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.io.FileWriter;
import java.io.IOException;

public class JsonFileWriter {
    private final Gson gson;

    public JsonFileWriter() {
        gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
    }
    public JsonFileWriter(TypeAdapterFactory typeAdapterFactory){
        gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().registerTypeAdapterFactory(typeAdapterFactory).create();
    }

    public <T> void write(T object, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(this.gson.toJson(object));
        writer.close();
    }
}