package com.pwm.argos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

/**
 * A class to store {@link EncryptedP}asswords
 */
public class PwSafe {
    /**
     * Stores the passwords in an arraylist
     */
    private ArrayList<EncryptedP> data;
    private String path = "../pwsafe.pw";

    /**
     * Creates a safe from {@link EncryptedP}asswords
     * @param inputPws the {@link EncryptedP}asswords to be added to the safe
     */
    public PwSafe(EncryptedP... inputPws){
        data = new ArrayList<>();
        Collections.addAll(this.data, inputPws);
    }

    /**
     * adds a password to the safe list ({@link PwSafe#data})
     * @param pw type: {@link EncryptedP}; the password, which is added
     * @return true if the password was set successfully
     */
    public boolean addPw (EncryptedP pw) {
        return data.add(pw);
    }

    /**
     * sets the path for the safe
     * @param path the path where the safe should be located
     */
    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<EncryptedP> searchFor(String... args){
        ArrayList<EncryptedP> resultList = new ArrayList<>(0);

        return resultList;
    }

    /**
     * reads the safe from a file at {@link PwSafe#path} (with JSON)
     */
    public void readSafe () {
        try {
            data = new Gson().fromJson(Files.readString(Paths.get(path)), new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * writes the safe to a file at {@link PwSafe#path}
     */
    public void writeSafe () {
        String safeString = new Gson().toJson(data);
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(path), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSafe (String pathArg) {
        String safeString = new Gson().toJson(data);
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(pathArg), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "PwSafe{" +
                "data=" + data +
                ", at path='" + path + '\'' +
                '}';
    }
}
