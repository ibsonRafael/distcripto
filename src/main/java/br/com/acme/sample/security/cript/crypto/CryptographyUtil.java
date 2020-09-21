package br.com.acme.sample.security.cript.crypto;



import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CryptographyUtil  {

    public static final Subject cryptographyStateSubjet = new Subject();

    public static final Logger logger = LoggerFactory.getLogger(CryptographyUtil.class);

    public static HashMap<String, byte[]> getKeyVault() {
        return keyVault;
    }

    public static void setKeyVault(HashMap<String, byte[]> keyVault) {
        CryptographyUtil.keyVault = keyVault;
    }

    public static String getAlgorithm() {
        return ALGORITHM;
    }

    public static HashMap<String, byte[]> keyVault;

    private static final String ALGORITHM = "RSA";


    /**
     * FIXME Verifcar se exixte a chave no vault
     * @param inputData
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] inputData) throws Exception {
        return decrypt(keyVault.get("privateKey"), inputData);
    }
    public static byte[] decrypt(byte[] privateKey, byte[] inputData)
            throws Exception {

        PrivateKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }

    /**
     * FIXME Verifcar se exixte a chave no vault
     * @param inputData
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] inputData) throws Exception {
        return encrypt(keyVault.get("publicKey"), inputData);
    }
    public static byte[] encrypt(byte[] publicKey, byte[] inputData)
            throws Exception {
        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKey));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(inputData);
        return encryptedBytes;
    }

    public static void generateKeys() throws NoSuchProviderException, NoSuchAlgorithmException {
        HashMap<String, byte[]> localKeyVault = new HashMap<>();
        KeyPair generateKeyPair = generateKeyPair();
        byte[] publicKey = generateKeyPair.getPublic().getEncoded();
        byte[] privateKey = generateKeyPair.getPrivate().getEncoded();
        localKeyVault.put("privateKey", privateKey);
        localKeyVault.put("publicKey", publicKey);
        setKeyVault(localKeyVault);
        cryptographyStateSubjet.setState(cryptographyStateSubjet.getState() + 1);
    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        return generateKeyPair(1024);
    }

    public static KeyPair generateKeyPair(int keysize)
            throws NoSuchAlgorithmException, NoSuchProviderException {

        logger.info("Gerando chaves publica e privada");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // 512 is keysize
        keyGen.initialize(keysize, random);
        KeyPair generateKeyPair = keyGen.generateKeyPair();
        return generateKeyPair;
    }

    public static void scheduleRotateKey(int time) {
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                try {
                    generateKeys();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        },0,time);
    }

    public static String getHumanReadblePublicKey() {
        String key = new String(Base64.getEncoder().encode(getKeyVault().get("publicKey")));
        return WordUtils.wrap(key, 64, "\n", true);
    }




}
