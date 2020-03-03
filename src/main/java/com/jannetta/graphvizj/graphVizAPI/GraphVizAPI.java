package com.jannetta.graphvizj.graphVizAPI;

import com.jannetta.graphvizj.components.Console;
import org.apache.log4j.Logger;

import java.io.*;

public class GraphVizAPI {
    private static Logger logger = Logger.getLogger(GraphVizAPI.class);
    private Console console = Console.getInstance();

    /**
     * Writes an array of byte to a file
     * @param byteStream the array of byte to be written to a file
     * @param outputFile the file to which the data should be written
     * @return -1 if not successful and 0 if successful
     */
    public static int writeToFile(byte[] byteStream, File outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(byteStream);
            fos.close();
            return 0;
        } catch (java.io.IOException ioe) {
            return -1;
        }
    }

    /**
     * Run the dot command as an external executable
     * @param executable A string givign the full path to the dot executable
     * @param filename The input file containing the dot syntax
     * @return the png image created from the dot file as an array of byte
     */
    public static byte[] runDot(String executable, String filename) {
        byte[] byteStream = null;
        try {
            File tmpFile = File.createTempFile("graph_", ".png", new File("./"));
            Runtime rt = Runtime.getRuntime();
            String args[] = {executable, "-Tpng", "-o" + tmpFile, filename};
            Process p = rt.exec(args, null);
            p.waitFor();
            logger.debug("Exit value: " + p.exitValue());
            FileInputStream in = new FileInputStream(tmpFile.getAbsolutePath());
            byteStream = new byte[in.available()];
            int result = in.read(byteStream);
            logger.trace(result + " bytes read.");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // Read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
               Console.log(s);
            }

            // Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                Console.log(s);
            }
            if (in != null) {
                in.close();
            }
            if (tmpFile.delete() == false) {
                Console.log("Warning: " + tmpFile.getAbsolutePath() + " could not be deleted!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return byteStream;
    }

    /**
     * Write text (as a String) to a file
     * @param text the text to be written to the file
     * @param file the file to write the file to
     */
    public static void writeText(String text, File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.write(text);
            pw.close();
            logger.debug("Saved to " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
