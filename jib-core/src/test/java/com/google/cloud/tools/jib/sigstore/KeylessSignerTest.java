package com.google.cloud.tools.jib.sigstore;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeylessSignerTest extends TestCase {

    @Rule
    public final KeylessSigner signer = new KeylessSigner(null);

    @Test
    public void testSign() throws NoSuchAlgorithmException {
        String test = "test";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                test.getBytes());
        SignResult result = signer.sign(encodedhash);
        assertNotNull(result.getBundle());
    }
}