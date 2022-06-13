package com.pwm.argos;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        boolean run = true;
        int c = 0;
        ArrayList<PwSafe> safeList = new ArrayList<>();
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();
        String next;
        PwSafe def = new PwSafe("default");
        while (run) {
            next = String.valueOf(textIO.newStringInputReader().withMinLength(0).read("#>"));
            String[] nextLine = next.split(" ");
            switch (nextLine[0]){
                case "quit":
                    run = false;
                    break;
                case "new":
                    if (nextLine.length > 1 && nextLine[1].equals("entry")){
                        String user = textIO.newStringInputReader().read("Username:");
                        String pw = textIO.newStringInputReader().withInputMasking(true).read("Password:");
                        String tags = textIO.newStringInputReader().read("Tags (space-seperated):");
                        def.addPw(new EncryptedP(user,Encryptor.encrypt(pw,Encryptor.hash(textIO.newStringInputReader().withInputMasking(true).read("\nIRREVERSIBLE!!!\nEnter your master password:"))),tags.split(" ")));
                        terminal.printf("Password Entry with uid: %d successfully added%n",c);
                        c++;
                    } else if (nextLine.length > 1 && nextLine[1].equals("safe")) {
                        if (nextLine.length > 2){
                            safeList.add(new PwSafe(nextLine[2]));
                        }
                    }
                    break;
                case "get":
                    if (nextLine.length > 1 && def.validate(Integer.parseInt(nextLine[1]))){
                        terminal.print(def.get(Integer.parseInt(nextLine[1])).toString());
                        String user = textIO.newStringInputReader().withInputMasking(true).read("Enter master key to get decrypted password:");
                        if (!user.equals("")){
                            StringSelection stringSelection = new StringSelection(Encryptor.decrypt(def.get(Integer.parseInt(nextLine[1])).value,Encryptor.hash(user)));
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, null);
                            terminal.print(Encryptor.decrypt(def.get(Integer.parseInt(nextLine[1])).value,Encryptor.hash(user))+"\n");
                        }
                    }
                    break;
                case "read":
                    if (nextLine.length>1&&nextLine[1].equals("safe")) {
                        if (nextLine.length==2) {
                            def.readSafeEncrypted("test");
                        }
                    }
                    break;
                case "write":
                    if (nextLine.length>1&&nextLine[1].equals("safe")){
                        if (nextLine.length==2){
                            try {
                                def.writeSafeEncrypted("test");
                            } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException |
                                     BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException |
                                     InvalidKeyException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    break;
                case "print":
                    terminal.print(def+"\n");
                    break;
            }
        }
        textIO.dispose();
    }

}
