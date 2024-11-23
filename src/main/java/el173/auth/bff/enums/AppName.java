package el173.auth.bff.enums;

/**
 * @author hashithkarunarathne
 * @project Auth-BFF
 * @created 23/11/2024 - 14:48
 */
public enum AppName {
    APP1, APP2;

    private String callbackUrl;

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public static AppName getAppEnum(String appName) {
        for (AppName app : values()) {
            if (app.name().equalsIgnoreCase(appName)) {
                return app;
            }
        }
        return null;
    }

    public static boolean isValidAppName(String appName) {
        for (AppName app : values()) {
            if (app.name().equalsIgnoreCase(appName)) {
                return true;
            }
        }
        return false;
    }
}
