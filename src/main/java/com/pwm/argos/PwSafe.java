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

    private String doubleMasterHash;

    public boolean masterSet(){
        return doubleMasterHash!=null;
    }

    public String getDoubleMasterHash(){
        return doubleMasterHash;
    }

    public void setMaster(String doubleHash){
        this.doubleMasterHash=doubleHash;
    }
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

    public String getName() {
        return name;
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
            String safeString = Files.readString(Paths.get(path));
            String[] args = safeString.split("@@@");
            setMaster(args[0]);
            data = new Gson().fromJson(args[1], new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readSafe (String pathArg) {
        try {
            String safeString = Files.readString(Paths.get(path));
            String[] args = safeString.split("@@@");
            setMaster(args[0]);
            data = new Gson().fromJson(args[1], new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readSafeEncrypted (String key) {
        try {
            String safeString = Files.readString(Paths.get(path));
            String[] args = safeString.split("@@@");
            setMaster(args[0]);
            data = new Gson().fromJson(Encryptor.decrypt((args[1]),Encryptor.hash(key)), new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void readSafeEncrypted (String pathArg,String key) {
        try {
            String safeString = Files.readString(Paths.get(pathArg));
            String[] args = safeString.split("@@@");
            setMaster(args[0]);
            data = new Gson().fromJson(Encryptor.decrypt((args[1]),Encryptor.hash(key)), new TypeToken<ArrayList<EncryptedP>>(){}.getType());
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | IOException e){
            e.printStackTrace();
        }
    }

    /**
     * writes the safe to a file at {@link PwSafe#path}
     */
    public void writeSafe () {
        String safeString = doubleMasterHash + "@@@" + new Gson().toJson(data);
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(path), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSafe (String pathArg) {
        String safeString = doubleMasterHash + "@@@" + new Gson().toJson(data);
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(pathArg), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSafeEncrypted (String key,String pathArg) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String safeString = doubleMasterHash + "@@@" + Encryptor.encrypt(new Gson().toJson(data),Encryptor.hash(key));
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(pathArg), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSafeEncrypted (String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String safeString = doubleMasterHash + "@@@" + Encryptor.encrypt(new Gson().toJson(data),Encryptor.hash(key));
        System.out.println(safeString);
        try {
            Files.writeString(Paths.get(path), safeString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String toString() {
        return String.format("Password Safe %s%n",name)+data+"\n";
    }
}
