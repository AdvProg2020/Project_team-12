package Json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonFileReader {
    private final Gson gson;

    public JsonFileReader() {
        gson = (new GsonBuilder()).create();
    }

    public <T> T read(File file, Class<T> classOfT) throws FileNotFoundException {
        return this.gson.fromJson(new FileReader(file), classOfT);
    }
}