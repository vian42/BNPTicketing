package org.kata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.kata.model.output.CustomerSummaries;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {

    public static final Charset ENCODING = UTF_8;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public <T> T getDataFromJsonFile(String fileName, Type typeOfT) throws FileNotFoundException {
        var input = getFileFromResource(fileName);
        if(input==null){
            input = new FileInputStream(fileName);
        }
        Reader reader = new InputStreamReader(input, ENCODING);
        return GSON.fromJson(reader, typeOfT);
    }

    public void writeDataInJsonFile(String file, CustomerSummaries result) throws IOException {
        Writer writer = new FileWriterWithEncoding(file, ENCODING);
        GSON.toJson(result, writer);
        writer.close();
    }

    public InputStream getFileFromResource(String fileName) {
        var classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}

