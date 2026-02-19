package io.klustr.consent.cms;

import io.klustr.schemas.console.agreements.AgreementContent;
import io.klustr.schemas.console.storage.StorageObject;

import java.util.List;

public interface AgreementStorageProvider {

    StorageObject putAgreement(String agreementId, String locale, String mimeType, byte[] bits);

    void removeVersion(String agreementId, String locale, String version);

    List<StorageObject> listVersions(String agreementId, String locale);

    StorageObject getVersion(String agreementId, String locale, String version);

    StorageObject getLatest(String agreementId, String locale);

    String getMarkdown(String agreementId, String locale, String version);
}
