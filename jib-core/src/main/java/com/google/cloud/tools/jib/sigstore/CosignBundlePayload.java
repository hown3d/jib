package com.google.cloud.tools.jib.sigstore;

import dev.sigstore.rekor.client.RekorEntryBody;

public class CosignBundlePayload {
    RekorEntryBody body;
    long integratedTime;
    long logIndex;
    String logId;

    public CosignBundlePayload(RekorEntryBody body, long integratedTime, long logIndex, String logId) {
        this.body = body;
        this.integratedTime = integratedTime;
        this.logIndex = logIndex;
        this.logId = logId;
    }
}
