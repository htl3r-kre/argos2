package com.pwm.argos;

import java.util.ArrayList;


public class EncryptedP {

    protected String entryName;
    protected int uid;
    protected String value;
    protected String userName;
    protected ArrayList<String> tags;

    public String getUserName(){
        return userName;
    }

    public String getValue() {
        return value;
    }

    public EncryptedP(String... args) {
        if (args.length >= 3) {
            this.userName = args[1];
            this.value = args[0];
            tags = new ArrayList<>();
            for (int i = 2; i < args.length ; i++) {
                tags.add(args[i]);
            }
        }
    }

    public EncryptedP(int uid, String user, String pw, String[] tagList, String entryName) {
        this.uid = uid;
        this.value = pw;
        this.userName = user;
        this.tags = new ArrayList<>();
        for (String s:tagList) {
            tags.add(s);
        }
        this.entryName = entryName;
    }
    @Override
    public String toString() {
        return String.format("Password Entry %d:%nUsername: %s%nPassword: %s%nTags: %s%n",uid,userName,value,tags);
    }
}



