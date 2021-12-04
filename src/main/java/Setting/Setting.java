package Setting;

public class Setting {
    private static boolean soundOn = true;

    public static boolean isSoundOn() {
        return soundOn;
    }

    public static void setSoundOn(boolean soundOn) {
        Setting.soundOn = soundOn;
    }
}
