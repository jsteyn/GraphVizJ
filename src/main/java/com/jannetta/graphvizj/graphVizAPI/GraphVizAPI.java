package com.jannetta.graphvizj.graphVizAPI;

import com.jannetta.graphvizj.components.Console;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphVizAPI {
	static Logger logger = LoggerFactory.getLogger(GraphVizAPI.class);
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
     * @param fileType the type of file to save to
     * @param layout The layout to use for generating the graph
     * @return the png image created from the dot file as an array of byte
     */
    //https://stackoverflow.com/questions/6751944/how-to-execute-unix-commands-through-windows-cygwin-using-java
    public static byte[] runDot(String executable, String filename, String fileType, String layout) {
        byte[] byteStream = null;
        logger.debug("SUFFIX: " + fileType);
        logger.debug("LAYOUT: " + layout);
        try {
            if (layout == null) {
                layout = "dot";
            }
            File tmpFile = File.createTempFile("graph_", "." + fileType, new File("./"));
            Runtime rt = Runtime.getRuntime();
            String dotcommand = executable + " -T" + fileType + " -o" + tmpFile + " -K" + layout + " " + filename;
            String args[] = {executable, "-T" + fileType, "-o" + tmpFile, "-K" + layout, filename};
           // String args[] = {"c:\\cygwin64\\bin\\dot", " -T" + fileType, "-o" + tmpFile, "-K" + layout, filename};
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
            String s;
            while ((s = stdInput.readLine()) != null) {
               Console.log(s);
            }

            // Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                Console.log(s);
            }
            in.close();
            if (!tmpFile.delete()) {
                Console.log("Warning: " + tmpFile.getAbsolutePath() + " could not be deleted!");
            }

        } catch (IOException | InterruptedException e) {
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
