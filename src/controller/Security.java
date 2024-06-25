package controller;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;

public class Security {
	public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048, new SecureRandom());
        KeyPair rsaKeyPair = kpg.generateKeyPair();
        PublicKey pubkey = rsaKeyPair.getPublic();
        PrivateKey prikey = rsaKeyPair.getPrivate();
        
        
	}
}
