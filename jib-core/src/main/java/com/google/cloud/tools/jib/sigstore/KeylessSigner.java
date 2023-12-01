package com.google.cloud.tools.jib.sigstore;

import com.google.api.client.http.ByteArrayContent;
import dev.sigstore.KeylessSignature;
import dev.sigstore.KeylessSignerException;
import dev.sigstore.bundle.BundleFactory;
import dev.sigstore.rekor.client.RekorEntry;
import dev.sigstore.rekor.client.RekorEntryBody;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

public class KeylessSigner implements Signer {

    private final SigningOptions options;

    public KeylessSigner(SigningOptions options) {
        this.options = options;
    }

    @Override
    public SignResult sign(byte[] digest) {
        try (var signer = dev.sigstore.KeylessSigner.builder().sigstorePublicDefaults().build()){

            KeylessSignature signature = null;
            try {
                signature = signer.sign(digest);
            } catch (KeylessSignerException e) {
               throw new RuntimeException(e) ;
            }

            RekorEntry rekorEntry = signature.getEntry().orElseThrow();
            // used as value for dev.sigstore.cosign/bundle annotation on signature manifest
            RekorEntryBody body = rekorEntry.getBodyDecoded();
            String logID = rekorEntry.getLogID();
            long logIndex = rekorEntry.getLogIndex();
            long integratedTime = rekorEntry.getIntegratedTime();
            String signedEntryTimeStamp = rekorEntry.getVerification().getSignedEntryTimestamp();

            // TODO: is there no better way of doing this?
            var payload = new CosignBundlePayload(body, integratedTime, logIndex, logID);
            var bundle = new CosignBundle(payload, signedEntryTimeStamp);

            return new SignResult(bundle);

        } catch (CertificateException | IOException | NoSuchAlgorithmException | InvalidKeySpecException |
                 InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
