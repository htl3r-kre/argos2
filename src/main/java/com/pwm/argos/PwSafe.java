package com.pwm.argos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * A class to store {@link EncryptedP}asswords
 */
public class PwSafe {

    private String name;
    /**
     * Stores the passwords in an arraylist
     */
    private ArrayList<EncryptedP> data = new ArrayList<>();
    private String path = "safe.pw";

    /**
     * Creates a safe from {@link EncryptedP}asswords
     * @param inputPws the {@link EncryptedP}asswords to be added to the safe
     */
    public PwSafe(EncryptedP... inputPws){
        data = new ArrayList<>();
        Collections.addAll(this.data, inputPws);
    }

    public PwSafe (String name){
        this.name = name;
    }

    /**
     * adds a password to the safe list ({@link PwSafe#data})
     * @param pw type: {@link EncryptedP}; the password, which is added
     * @return true if the password was set successfully
     */
    public boolean addPw (EncryptedP pw) {
        return data.add(pw);
    }


    public EncryptedP get(int uid){
        return data.get(uid);
    }

    public boolean validate(int uid){
        return uid + 1 <= data.size();
    }

    /**
     * sets the path for the safe
     * @param path the path where the safe should be located
     */
    public void setPath(String path) {
        this.path = path;
    }

    /*public ArrayList<EncryptedP> searchFor(String... args){

    }*/

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

    public void readSafe (String pathArg) {
        try {
            data = new Gson().fromJson(Files.readString(Paths.get(path)), new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readSafeEncrypted (String key) {
        try {
            data = new Gson().fromJson(Encryptor.decrypt((Files.readString(Paths.get(path))),Encryptor.hash(key)), new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void readSafeEncrypted (String pathArg,String key) {
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

    public void writeSafeEncrypted (String key,String pathArg) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String safeString = Encryptor.encrypt(new Gson().toJson(data),Encryptor.hash(key));
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(pathArg), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSafeEncrypted (String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String safeString = Encryptor.encrypt(new Gson().toJson(data),Encryptor.hash(key));
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(path), safeString, StandardOpenOption.CREATE);
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
