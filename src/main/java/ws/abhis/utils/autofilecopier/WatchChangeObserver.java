package ws.abhis.utils.autofilecopier;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Observer for watch change model.
 */
public class WatchChangeObserver implements PropertyChangeListener {

    private Path destDir;
    private Path[] filter;
    private boolean isFilter;

    public WatchChangeObserver(WatchChange watchChange, Path destDir) {
        watchChange.addListener(this);
        this.destDir = destDir;
        this.isFilter = false;
    }

    public WatchChangeObserver(WatchChange watchChange, Path destDir, Path[] filter) {
        this(watchChange, destDir);
        this.filter = filter;
        this.isFilter = true;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Received event");
        WatchDS newVal = (WatchDS) evt.getNewValue();
        Path changedFile = newVal.getChangedFilePath();
        try {
            if (isFilter) {
                for (Path p : filter) {
                    if (p.toString().contains(changedFile.toString())) {
                        System.out.println("File changed " + changedFile.toString());
                        System.out.println("Comparing with filter " + p.toString());
                        Utils.copyFiles(Paths.get( newVal.getDirPath().toString() + File.separator + changedFile.toString() ), Paths.get(destDir + File.separator + changedFile.toString()));
                        System.out.println("Copied " + changedFile.toString() + ". Timestamp:" + newVal.getTimeStamp());
                    }
                }
            } else {
                System.out.println("File changed " + changedFile.toString());
                Utils.copyFiles(Paths.get( newVal.getDirPath().toString() + File.separator + changedFile.toString() ), Paths.get(destDir + File.separator + changedFile.toString()));
                System.out.println("Copied " + changedFile.toString() + ". Timestamp:" + newVal.getTimeStamp());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
