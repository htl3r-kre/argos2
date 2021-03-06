package com.pwm.argos;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.reflect.Array;
import java.nio.file.NoSuchFileException;
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
        ArrayList<PwSafe> safeList = new ArrayList<>();
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();
        String next;
        PwSafe def = new PwSafe("container");
        String init = textIO.newStringInputReader().read("Do you want to import your password safe from a file [y/n/path]");
        switch (init){
            case "y":
            case "Y":
                try {
                    def.readSafeEncrypted("default_key");
                } catch (NoSuchFileException e){
                    terminal.print("Couldn't find safe.");
                }
                break;
            case "n":
            case "N":
                break;
            default:
                try {
                def.readSafeEncrypted(init,"default_key");
                } catch (NoSuchFileException e){
                    terminal.print("Couldn't find safe.");
                }
        }
        while (run) {
            if (!def.masterSet()){
                String att1 = Encryptor.hash2(textIO.newStringInputReader().withInputMasking(true).read("First Login\nEnter your master key:"));
                if (Encryptor.hash2(textIO.newStringInputReader().withInputMasking(true).read("Please repeat you master key:")).equals(att1)){
                    def.setMaster(att1);
                }
            }
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
                        if (nextLine.length==3) {
                            boolean masterCorrect = false;
                            boolean firstTry = true;
                            String hashOne = "";
                            while (!masterCorrect) {
                                if (firstTry) {
                                    hashOne = Encryptor.hash(textIO.newStringInputReader().withInputMasking(true).read("\nIRREVERSIBLE!!!\nEnter your master password:"));
                                } else {
                                    hashOne = Encryptor.hash(textIO.newStringInputReader().withInputMasking(true).read("\nWRONG MASTER KEY!!!\nEnter your master password:"));
                                }
                                masterCorrect = Encryptor.hash(hashOne).equals(def.getDoubleMasterHash());
                                firstTry=false;
                            }
                            def.addPw(new EncryptedP(def.uidCount, user, Encryptor.encrypt(pw, hashOne), tags.split(" "),nextLine[2]));
                            hashOne=null;
                        }
                        terminal.printf("Password Entry with uid: %d successfully added%n",def.uidCount);
                        def.uidCount++;
                    }
                    break;
                case "get":
                    if (nextLine.length > 1 && def.validate(Integer.parseInt(nextLine[1]))){
                        terminal.print(def.get(Integer.parseInt(nextLine[1])).toString());
                        if (nextLine.length==2) {
                            boolean masterCorrect = false;
                            boolean firstTry = true;
                            String hashOne = "";
                            while (!masterCorrect) {
                                if (firstTry) {
                                    hashOne = Encryptor.hash(textIO.newStringInputReader().withInputMasking(true).read("\nEnter your master password to decrypt:"));
                                } else {
                                    hashOne = Encryptor.hash(textIO.newStringInputReader().withInputMasking(true).read("\nWrong master password\n Enter your master password to decrypt:"));
                                }
                                masterCorrect = Encryptor.hash(hashOne).equals(def.getDoubleMasterHash());
                                firstTry=false;
                            }
                            if (!hashOne.equals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")){
                                StringSelection stringSelection = new StringSelection(Encryptor.decrypt(def.get(Integer.parseInt(nextLine[1])).value,hashOne));
                                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                clipboard.setContents(stringSelection, null);
                                terminal.print(Encryptor.decrypt(def.get(Integer.parseInt(nextLine[1])).value,hashOne)+"\n");
                            }
                            hashOne=null;
                        }

                    }
                    break;
                case "read":
                    if (nextLine.length>1&&nextLine[1].equals("safe")) {
                        if (nextLine.length==2&&!nextLine[1].contains("@")) {
                            try {
                                def.readSafeEncrypted("default_key");
                            } catch (NoSuchFileException e){
                                terminal.print("Couldn't find safe.");
                            }
                        }
                    }
                    break;
                case "write":
                    if (nextLine.length>1&&nextLine[1].equals("safe")){
                        if (nextLine.length==2){
                            try {
                                def.writeSafeEncrypted("default_key");
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

                case "help":
                    terminal.print("Argos Help:\nnew entry [Name] - creates a new password entry named [Name]\nget [uid] - gets the entry with given uid\nwrite safe - writes the safe to a file\nread safe - reads the safe from a file\nprint - prints the safe\nquit - quits the application (recommended to write safe first)\n");
            }
        }
        textIO.dispose();
    }

}
