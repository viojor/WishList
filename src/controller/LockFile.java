package controller;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class LockFile {

    public static final String MAIN_LOCK_FILE_NAME = "MAIN_TEMP_LOCK_FILE";
    public static final String ITEM_LOCK_FILE_EXTENSION = "_VIEWER_TEMP_LOCK_FILE";

    private static final String ACCESS_FILE_MODE = "rw";

    public static boolean lockInstance(final String lockFile) {

        try {

            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, ACCESS_FILE_MODE);
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();

            file.deleteOnExit();
            if (fileLock != null) {

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {

                    try {

                        fileLock.release();
                        randomAccessFile.close();
                        file.deleteOnExit();
                    } catch (Exception e) {

                        System.out.println("Unable to remove lock file: " + lockFile + "," + e);
                    }
                }));
                return true;
            }
        } catch (Exception e) {

            System.out.println("Unable to create and/or lock file: " + lockFile + ", " + e);
        }
        return false;
    }
}
