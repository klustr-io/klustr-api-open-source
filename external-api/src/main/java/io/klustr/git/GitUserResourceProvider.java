package io.klustr.git;

import io.klustr.schemas.integrations.git.GitCreateUserRequest;
import io.klustr.schemas.integrations.git.GitUserReference;
import io.klustr.storage.Pagination;

import java.util.List;
import java.util.Optional;

public interface GitUserResourceProvider {

    void linkOpenIdProvider(String email,  String extern_uid);

    Optional<GitUserReference> getByEmail(String email);

    GitUserReference getByUserId(String userId);

    Optional<GitUserReference> getByOpenIdProvider(String extern_uid);

    void deactivateUser(String id);

    GitUserReference createUser(GitCreateUserRequest request);

    void updateAvatar(String extern_uid, byte[] imageBytes);

    void updateAvatar(String extern_uid, String url);

    List<GitUserReference> listUsers(Pagination pagination);

}
