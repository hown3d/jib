package com.google.cloud.tools.jib.sigstore;

import dev.sigstore.rekor.client.RekorEntryBody;

public class CosignBundle {

    CosignBundlePayload payload;
    String signedEntryTimestamp;


    public CosignBundle(CosignBundlePayload payload, String signedEntryTimestamp) {
        this.signedEntryTimestamp = signedEntryTimestamp;
        this.payload = payload;
    }

}
