package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtil {

    private static final Pattern UUID_REGEX_PATTERN = Pattern.compile(
        "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$"
    );

    public static UUID generateUUIDFromString(String id) {
        UUID uuid = UUID.fromString(id);
        return uuid;
    }

    public static boolean checkValidUUID(String id) {
        if (id == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(id).matches();
    }
}
