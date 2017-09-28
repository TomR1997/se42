package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    public static final String ALGORITHM = "RSA";
    public static final String PRIVATE_KEY_FILE = "C:\\Users\\Tomt\\Documents\\NetBeansProjects\\Assignment6Encryption\\private.key";
    public static final String PUBLIC_KEY_FILE = "C:\\Users\\Tomt\\Documents\\NetBeansProjects\\Assignment6Encryption\\public.key";
    public static final String SIGNED_FILE = "signed.txt";
    public static final String PATH = "C:\\Users\\Tomt\\Documents\\NetBeansProjects\\Assignment6Encryption\\";
    private static KeyPair key;
    private static SecureRandom secureRandom;
    private static File file = null;
    private static byte[] iv;
    private static byte[] ciphertext = new byte[0];
    private static String plaintext = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (!areKeysPresent()) {
            generateKey();
        }
        System.out.println("Keys created");

        final String originalText = "Text to be encrypted ";
        String password = "test";
        ObjectInputStream inputStream = null;

        inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        final PublicKey publicKey = (PublicKey) inputStream.readObject();
        final byte[] cipherText = encrypt(originalText, publicKey);

        inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
        final String plainText = decrypt(cipherText, privateKey);

        /*System.out.println("Original Text: " + originalText);
        System.out.println("Encrypted Text: " + cipherText.toString());
        System.out.println("Decrypted Text: " + plainText);*/
        try {
            encryptT(password.toCharArray(), originalText, "encrypt.ext");
            decryptT(password.toCharArray());
            System.out.println(ciphertext.toString());
            System.out.println(plainText);
        } catch (UnsupportedEncodingException | InvalidParameterSpecException ex) {
            Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void generateKey() {
        try {
            secureRandom = new SecureRandom();
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024, secureRandom);
            //final KeyPair key = keyGen.generateKeyPair();
            key = keyGen.generateKeyPair();

            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            try (
                    ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                            new FileOutputStream(publicKeyFile))) {
                publicKeyOS.writeObject(key.getPublic());
            }

            try (
                    ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                            new FileOutputStream(privateKeyFile))) {
                privateKeyOS.writeObject(key.getPrivate());
            }
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static boolean areKeysPresent() {

        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception ex) {
            Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return cipherText;
    }

    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return new String(dectyptedText);
    }

    private static void encryptT(char[] password, String message, String fileName) throws UnsupportedEncodingException, InvalidKeyException, InvalidParameterSpecException {

        if (password.length > 0 && !message.isEmpty() && !fileName.isEmpty()) {

            byte[] salt = createSalt();

            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            try {
                cipher.init(Cipher.ENCRYPT_MODE, createSecret(password, salt));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            AlgorithmParameters params = cipher.getParameters();
            try {
                iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            } catch (InvalidParameterSpecException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            //byte[] ciphertext = new byte[0];
            try {
                ciphertext = cipher.doFinal(message.getBytes("UTF-8"));
            } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }

            try {
                createEncryptedFile(salt, ciphertext, fileName);
            } catch (IOException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private static void decryptT(char[] password) throws InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException {

        if (file != null) {
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);;
            } catch (NoSuchPaddingException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }

            AlgorithmParameters params = cipher.getParameters();

            try {
                cipher.init(Cipher.DECRYPT_MODE, createSecret(password, readSalt()), new IvParameterSpec(iv));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }

            try {
                plaintext = new String(cipher.doFinal(readCipherText()), "UTF-8");
            } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            System.out.println(plaintext);
        }
    }

    private static SecretKey createSecret(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    private static byte[] createSalt() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.generateSeed(8);
    }

    private static void createEncryptedFile(byte[] salt, byte[] ciphertext, String fileName) throws IOException {
        File file = new File("C:\\Users\\Tomt\\Documents\\NetBeansProjects\\Assignment6Encryption\\" + fileName);
        DataOutputStream writer = new DataOutputStream(new FileOutputStream(file));
        writer.write(salt);
        writer.write(ciphertext);
    }

    private static byte[] readSalt() throws FileNotFoundException {
        DataInputStream ds = null;
        ds = new DataInputStream(new FileInputStream(file));

        byte[] salt = new byte[8];
        for (int i = 0; i < 8; i++) {
            try {
                salt[i] = ds.readByte();
            } catch (IOException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return salt;
    }

    private static byte[] readCipherText() throws FileNotFoundException {

        DataInputStream ds = null;
        ds = new DataInputStream(new FileInputStream(file));

        for (int i = 0; i < 8; i++) {
            try {
                System.out.println(ds.readByte());
            } catch (IOException e) {
                Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        List<Byte> byteList = new ArrayList();
        try {
            while (ds.available() != 0) {
                try {
                    byteList.add(ds.readByte());
                } catch (IOException e) {
                    Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(EncryptionUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        byte[] ciphertext = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            ciphertext[i] = byteList.get(i);
        }

        return ciphertext;
    }
}
