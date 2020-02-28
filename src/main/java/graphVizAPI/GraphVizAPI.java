package graphVizAPI;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Scanner;

public class GraphVizAPI {
    private static Logger logger = Logger.getLogger(GraphVizAPI.class);

    public static int writeToFile(byte[] byteStream, File outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(byteStream);
            fos.close();
        } catch (java.io.IOException ioe) {
            return -1;
        }
        return 1;
    }

    public static byte[] runDot(String executable, String filename) {
        byte[] byteStream = null;
        try {
            File tmpFile = File.createTempFile("graph_", ".png", new File("./"));
            Runtime rt = Runtime.getRuntime();
            String args[] = {executable, "-Tpng", "-o" + tmpFile, filename};
            Process p = rt.exec(args, null);
            p.waitFor();
            logger.debug("Exit value: " + p.exitValue());
            logger.debug("executed");
            FileInputStream in = new FileInputStream(tmpFile.getAbsolutePath());
            byteStream = new byte[in.available()];
            in.read(byteStream);
            if (in != null) {
                in.close();
            }
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            if (tmpFile.delete() == false) {
                System.err.println("Warning: " + tmpFile.getAbsolutePath() + " could not be deleted!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return byteStream;
    }

    public static void writeText(String text, File filename) {
        try {
            PrintWriter pw = new PrintWriter(filename);
            pw.write(text);
            pw.close();
            logger.debug("Saved to " + filename.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
