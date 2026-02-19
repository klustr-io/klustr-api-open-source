package io.klustr.git;

import io.klustr.integrations.gitlab.GitLabGroupMembershipProvider;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.git.GitUserReference;

import java.util.List;
import java.util.Optional;

public interface GitGroupMembershipResourceProvider {

    void addGroupMember(GitGroupReference groupRef, GitUserReference userRef, AccessLevel level);

    Optional<GitLabGroupMembershipProvider.GroupMember> getGroupMemberByEmail(GitGroupReference group, GitUserReference userRef);

    void removeGroupMember(GitGroupReference groupRef, GitUserReference userRef);

    void setUserAccessLevel(GitGroupReference groupRef, GitUserReference userRef, AccessLevel level);

    List<GitLabGroupMembershipProvider.GroupMember> listGroupMembers(GitGroupReference group);
}
