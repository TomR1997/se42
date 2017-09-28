/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomt
 */
public class Sign {

    public static void main(String[] args) throws SignatureException {
        createFile();

        String inputText = getFile();
        try {
            PrivateKey privateKey = getPrivate("private.key");
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            byte[] signatureBytes = signature.sign();
            String name = "";

            while (name.isEmpty()) {
                System.out.println("Type in your name");
                name = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }
            File signatureFile = new File(String.format("INPUT(SignedBy%s)", name.replace(" ", "")));
            signatureFile.createNewFile();

            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(signatureFile));
            os.writeInt(signatureBytes.length);
            os.writeObject(signatureBytes);
            os.writeObject(inputText);
            os.close();

        } catch (NoSuchAlgorithmException | SignatureException | IOException | InvalidKeyException ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static PrivateKey getPrivate(String filename) throws Exception {
        PrivateKey key = null;

        try {
            key = (PrivateKey) new ObjectInputStream(new FileInputStream(filename)).readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return key;
    }

    private static void createFile() {
        File yourFile = new File("INPUT.ext");
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
            System.out.println("New file created");
        } catch (IOException ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public static String getFile() {
        String input = "";
        try {
            input = new String(Files.readAllBytes(Paths.get("INPUT.ext")), UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return input;
    }
}
