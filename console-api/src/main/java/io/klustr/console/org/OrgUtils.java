package io.klustr.console.org;

import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgAssignment;
import io.klustr.schemas.console.orgs.OrgUnit;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class OrgUtils {

    /**
     * Will take an organization and traverse the {@link OrgUnit} by using a OU
     * based path structure. For example: "/testOu/under testOu/other ou"
     *
     * @param path The path through the OU hierachy.
     * @param org  The organization to traverse
     * @return The optional org unit that was found.
     */
    public static Optional<OrgUnit> getOrgUnit(String path, Org org) {
        if (StringUtils.isBlank(path)) {
            return Optional.empty();
        }
        if (!path.contains("/")) {
            return Optional.empty();
        }
        if (org.getChildren() == null) {
            return Optional.empty();
        }
        if (org.getChildren().isEmpty()) {
            return Optional.empty();
        }

        String[] tree = path.split("/");

        OrgUnit result = null;
        List<OrgUnit> currentLevel = org.getChildren();

        for (var i = 0; i < tree.length; i++) {
            String key = tree[i].replaceAll("/", "");
            Optional<OrgUnit> match = currentLevel.stream().filter(x -> x.getId().equalsIgnoreCase(key)).findFirst();
            if (match.isEmpty()) {
                return Optional.empty();
            }
            if (i == tree.length - 1) {
                result = match.get();
                return Optional.of(result);
            } else {
                currentLevel = match.get().getChildren();
            }
        }

        return Optional.empty();
    }

    public static String generateId(OrgAssignment assn) {
        String key = assn.getCustomerId() + ":" + assn.getOrgPath() + ":" + assn.getAssigneeType() + ":" + assn.getAssignedTo();
        return U.md5(key);
    }
}
