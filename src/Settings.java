public class Settings {
    private static final String FILE_PATH = "resources/";

    private static final String PLANTS_ALL_CORRECT = FILE_PATH + "kvetiny.txt";
    private static final String PLANTS_INVALID_DATE = FILE_PATH + "kvetiny-spatne-datum.txt";
    private static final String PLANTS_INVALID_FREQUENCY = FILE_PATH + "kvetiny-spatne-frekvence.txt";
    private static final String DELIMITER = "\t";
    private static final String OUTPUT_FILE = "vystup-kvetiny.txt";

    public static String getDelimiter() {
        return DELIMITER;
    }

    public static String getPlantsAllCorrect() {
        return PLANTS_ALL_CORRECT;
    }

    public static String getPlantsInvalidDate() {
        return PLANTS_INVALID_DATE;
    }

    public static String getPlantsInvalidFrequency() {
        return PLANTS_INVALID_FREQUENCY;
    }

    public static String getOutputFile() {
        return OUTPUT_FILE;
    }
}
