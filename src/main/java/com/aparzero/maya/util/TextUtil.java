package com.aparzero.maya.util;

public class TextUtil {
    public static String maskName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "";
        }

        String[] parts = fullName.split("\\s+");
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.length() <= 2) {
                masked.append(part);
            } else {
                masked.append(part.charAt(0));
                masked.append("*".repeat(part.length() - 2));
                masked.append(part.charAt(part.length() - 1));
            }

            if (i < parts.length - 1) {
                masked.append(" ");
            }
        }

        return masked.toString();
    }
}
