package com.interactuamovil.apps.contactosms.api.utils;

public final class JavaVersionDetector {
    
    private static final int JAVA_VERSION;
    private static final boolean IS_JAVA_8;
    private static final boolean IS_JAVA_21_OR_HIGHER;
    
    static {
        String version = System.getProperty("java.version");
        int majorVersion = parseMajorVersion(version);
        JAVA_VERSION = majorVersion;
        IS_JAVA_8 = (majorVersion == 8);
        IS_JAVA_21_OR_HIGHER = (majorVersion >= 21);
    }
    
    private JavaVersionDetector() {
    }
    
    private static int parseMajorVersion(String version) {
        if (version == null || version.isEmpty()) {
            return 8;
        }
        
        try {
            if (version.startsWith("1.")) {
                String[] parts = version.split("\\.");
                if (parts.length >= 2) {
                    return Integer.parseInt(parts[1]);
                }
            } else {
                int dotIndex = version.indexOf('.');
                if (dotIndex > 0) {
                    return Integer.parseInt(version.substring(0, dotIndex));
                } else {
                    return Integer.parseInt(version);
                }
            }
        } catch (NumberFormatException e) {
            return 8;
        }
        
        return 8;
    }
    
    public static int getJavaVersion() {
        return JAVA_VERSION;
    }
    
    public static boolean isJava8() {
        return IS_JAVA_8;
    }
    
    public static boolean isJava21OrHigher() {
        return IS_JAVA_21_OR_HIGHER;
    }
    
    public static boolean isJava9OrHigher() {
        return JAVA_VERSION >= 9;
    }
    
    public static boolean isJava11OrHigher() {
        return JAVA_VERSION >= 11;
    }
    
    public static boolean isJava15OrHigher() {
        return JAVA_VERSION >= 15;
    }
}

