package com.google.cloud.tools.jib.sigstore;

public interface Signer {
    SignResult sign(byte[] digest);
}
