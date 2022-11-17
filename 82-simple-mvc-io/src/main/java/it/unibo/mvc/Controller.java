package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {
    private File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + DEAFULT_NAME);
    private static final String DEAFULT_NAME = "output.txt";

    /**
     *  Setting a File as current file. 
     *  @param newFile
     */
    public void setFile(final File newFile) {
        this.file = newFile;
    }

    /**
     *  Getting the current file.
     *  @return the current file.
     */
    public File getFile() {
        return this.file;
    }
 
    /** 
     *  Getting the path (in form of String) of the current `File`.
     *  @return the file path.
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /** 
     *  Gets a `String` as input and saves its content on the current file. 
     *  @param content
     */
    public void saveString(final String content) throws IOException {
        try (PrintStream ps = new PrintStream(this.file, StandardCharsets.UTF_8)) {
            ps.print(content);
        } catch (final IOException e) {
            System.out.println(e.getMessage()); // NOPMD: allowed as this is just an exercise
        }
    }
}
