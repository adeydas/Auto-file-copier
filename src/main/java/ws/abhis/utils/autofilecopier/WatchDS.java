package ws.abhis.utils.autofilecopier;

import java.nio.file.Path;
import java.util.Date;

/**
 * Data structure for watcher.
 */
public class WatchDS {
    private Path dirPath;
    private Path changedFilePath;
    private long timeStamp;

    public Path getDirPath() {
        return dirPath;
    }

    public void setDirPath(Path dirPath) {
        this.dirPath = dirPath;
    }

    public Path getChangedFilePath() {
        return changedFilePath;
    }

    public void setChangedFilePath(Path changedFilePath) {
        this.changedFilePath = changedFilePath;
    }


    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
