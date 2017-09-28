/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomt
 */
public class Read {

    public static void main(String[] args) {
        boolean fileIsValid = false;
        try {
            String name = "";

            while (name.isEmpty()) {
                System.out.println("Person's name:");
                name = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("INPUT(SignedBy%s)", name.replace(" ", ""))));
            int singnatureSize = objectInputStream.readInt();
            byte[] signatureBytes = (byte[]) objectInputStream.readObject();

            String text = (String) objectInputStream.readObject();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(getPublic("public.key"));
            fileIsValid = signature.verify(signatureBytes);

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | SignatureException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (fileIsValid) {
            System.out.println("File is valid");
        } else {
            System.out.println("File is invalid");
        }
    }

    public static PublicKey getPublic(String filename) throws Exception {
        PublicKey key = null;

        try {
            key = (PublicKey) new ObjectInputStream(new FileInputStream(filename)).readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Sign.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return key;
    }
}
