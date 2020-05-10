package Controller.DataBase.Json;

import Model.Account.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class has been created for saving files in json form and/or with a TypeAdaptorFactory which can help you save your inherited classes with more compatibility
 * see more information at {@link com.google.gson.typeadapters.RuntimeTypeAdapterFactory}
 *
 */

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
    public <T> void write(T object, String filePath,Class c) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(this.gson.toJson(object,c));
        writer.close();
    }
}
