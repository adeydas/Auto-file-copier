package ws.abhis.utils.autofilecopier;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Listener model for watching file changes.
 */
public class WatchChange {
    private Path path;
    private List<PropertyChangeListener> listeners;
    private WatchDS ds;

    /**
     * Ctor to initialize all vars.
     * @param dirPath
     */
    WatchChange(String dirPath) {
        this.path = FileSystems.getDefault().getPath(dirPath);
        this.listeners = new ArrayList<PropertyChangeListener>();
        this.ds = new WatchDS();
        this.ds.setDirPath(this.path);
        this.ds.setChangedFilePath(null);
        this.ds.setTimeStamp(Utils.getTimeStamp());
    }

    /**
     * Add a listener to the model.
     * @param listener
     */
    public void addListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Notify all listeners.
     * @param property
     * @param oldValue
     * @param newValue
     */
    private void notifyListeners(String property, WatchDS oldValue, WatchDS newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    /**
     * Watch a directory and notify listeners of changes.
     * @throws IOException
     * @throws InterruptedException
     */
    public void watchDir() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        while (true) {
            WatchKey key;
            key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                WatchDS oldVal = this.ds;
                this.ds = new WatchDS();
                this.ds.setDirPath(this.path);
                this.ds.setTimeStamp(Utils.getTimeStamp());
                this.ds.setChangedFilePath(fileName);

                //notify all listeners.
                String property = "";
                if (kind == ENTRY_CREATE)
                    property = "CREATE";
                else if (kind == ENTRY_DELETE)
                    property = "DELETE";
                else if (kind == ENTRY_MODIFY)
                    property = "MODIFY";
                notifyListeners(property, oldVal, this.ds);
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}
