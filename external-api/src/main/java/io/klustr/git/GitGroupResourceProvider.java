package io.klustr.git;

import io.klustr.schemas.integrations.git.GitCreateGroupRequest;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.storage.Pagination;

import java.util.List;
import java.util.Optional;

public interface GitGroupResourceProvider {

    Optional<GitGroupReference> getGroupByPath(String path);

    List<GitGroupReference> listGroups(Pagination pagination);

    GitGroupReference createGroup(GitCreateGroupRequest request);

    void deleteGroup(GitGroupReference ref);


}
