package me.pajic.affogatocore.util;

import me.pajic.affogatocore.AffogatoCoreClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class SHA256 {

    public static String calculateSHA256(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buf = new byte[4096];
            int len;

            while ((len = fis.read(buf)) != -1) {
                md.update(buf, 0, len);
            }
        } catch (IOException e) {
            AffogatoCoreClient.LOGGER.error("Failed to open file for hash calculation", e);
        }

        var digest = md.digest();
        return HexFormat.of().formatHex(digest);
    }
}
