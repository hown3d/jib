package com.google.cloud.tools.jib.sigstore;

public class SignResult {
    private CosignBundle bundle;

    public SignResult(CosignBundle bundle) {
        this.bundle = bundle;
    }

    public CosignBundle getBundle() {
        return bundle;
    }
}
