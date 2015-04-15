package ws.abhis.utils.autofilecopier;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.nio.file.StandardCopyOption.*;

/**
 * Utility methods.
 */
public class Utils {
    /**
     * Get the current timestamp in UNIX format.
     * @return long
     */
    public synchronized static long getTimeStamp() {
        Date date = new Date();
        return date.getTime();
    }

    /**
     * Copy file from source to destination replacing if file exists at destination.
     * @param from
     * @param to
     * @throws IOException
     */
    public synchronized static void copyFiles(Path from, Path to) throws IOException {
        Files.copy(from, to, REPLACE_EXISTING);
    }
}
