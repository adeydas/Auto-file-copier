package ws.abhis.utils.autofilecopier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main class.
 */
public class Starter {
    public static void main(String... args) {
        if (args.length < 2) {
            System.out.println("Usage: app.jar <source_path> <target_path> <filter (for selected files file)>");
            return;
        }

        String source = args[0];
        String target = args[1];

        WatchChange watchChange = new WatchChange(source);

        if (args.length>2) {
            Path[] path = new Path[args.length-2];
            for (int i=2, j=0; i<args.length; i++, j++) {
                path[j] = Paths.get(args[i]);
            }
            WatchChangeObserver watchChangeObserver = new WatchChangeObserver(watchChange, Paths.get(target), path);
        } else {
            WatchChangeObserver watchChangeObserver = new WatchChangeObserver(watchChange, Paths.get(target));
        }

        try {
            watchChange.watchDir();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
