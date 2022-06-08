package com.pwm.argos;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MainTest {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String testHash = Encryptor.hash("hallo");
        EncryptedP p1 = new EncryptedP("hashvalue","user","tag1","tag2");
        EncryptedP p2 = new EncryptedP("hashvalue","user","tag1","tag2");
        EncryptedP p3 = new EncryptedP("hashvalue","user","tag1","tag2");
        PwSafe test = new PwSafe(p1,p2,p3);
        test.writeSafe("safe.pw");
        String a = Encryptor.encrypt("hallo",testHash);
        System.out.println(Encryptor.decrypt(a,testHash));
    }
}
