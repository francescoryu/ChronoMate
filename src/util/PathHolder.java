package util;

public class PathHolder
{
    public static final String USER_PATH = System.getProperty("user.home");
    public static final String APP_PATH = USER_PATH.concat("/ChronoMate/");
    public static final String FILE_PATH = APP_PATH.concat("chronomate.xml");
}
