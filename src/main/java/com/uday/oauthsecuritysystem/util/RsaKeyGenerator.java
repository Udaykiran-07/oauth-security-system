package com.uday.oauthsecuritysystem.util;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RsaKeyGenerator {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        String privateKey = Base64.getEncoder()
                .encodeToString(keyPair.getPrivate().getEncoded());

        String publicKey = Base64.getEncoder()
                .encodeToString(keyPair.getPublic().getEncoded());

        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(privateKey.getBytes());
        }

        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKey.getBytes());
        }

        System.out.println("RSA Keys Generated Successfully");
    }
}