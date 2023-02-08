package yusama125718.man10_bank_robber.enums;

public enum NexusMode {
    FIXED,
    PERCENTAGE;
    public static boolean isEnumString(String str) {
        for (NexusMode status : NexusMode.values()) {
            if (status.name().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
