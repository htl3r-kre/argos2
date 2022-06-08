package com.pwm.argos;

import java.util.ArrayList;

public class EncryptedP {
    protected String value;
    protected String userName;
    protected ArrayList<String> tags;

    public EncryptedP(String... args) {
        if (args.length >= 3) {
            this.value = args[0];
            this.userName = args[1];
            tags = new ArrayList<>();
            for (int i = 2; i < args.length ; i++) {
                tags.add(args[i]);
            }
        }
    }

    @Override
    public String toString() {
        return "EncryptedP{" +
                "value='" + value + '\'' +
                ", userName='" + userName + '\'' +
                ", tags=" + tags +
                '}';
    }
}
