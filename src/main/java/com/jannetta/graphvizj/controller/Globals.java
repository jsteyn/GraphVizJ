package com.jannetta.graphvizj.controller;

import java.io.*;
import java.util.Properties;

public class Globals {
    static private Properties properties = null;
    static private String lastDir = "";
    static private String outputDir = "";
    static private String type = "png";
    static private String layout = "dot";
    static private String executable = "";

    /**
     * Load properties from system.properties file
     */
    public static void loadProperties() {
        properties = new Properties();
        try {
            File f = new File("system.properties");
            if (!(f.exists())) {
                OutputStream out = new FileOutputStream(f);
            }
            InputStream is = new FileInputStream(f);
            properties.load(is);
            lastDir = getLastDir();
            if (lastDir == null) {
                lastDir = "~";
                setLastDir(lastDir);
            }
            properties.setProperty("lastdir", lastDir);

            // Try loading properties from the file (if found)
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the specified property with the specified value
     * @param property The property to set
     * @param value The value to set the property to
     */
    public static void setProperty(String property, String value) {
        properties.setProperty(property, value);
        File f = new File("system.properties");
        try {
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "This is an optional header comment string");
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Get the specified property and return its value
     * @param property property to return
     * @return property
     */
    public static String getProperty(String property) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(property);
    }

    /**
     * Get the last directory that something was saved to or opened from
     * @return lastDir
     */
    public static String getLastDir() {
        return getProperty("lastdir");
    }

    /**
     * Set the value of lastDir
     * @param lastDir
     */
    public static void setLastDir(String lastDir) {
        setProperty("lastdir", lastDir);
    }

    /**
     * Get the current dot Type saved in the properties file.
     * @return type
     */
    public static String getType() {
        type = getProperty("type");
        if (type == null) type = "png";
        return type;
    }

    /**
     * Set the dot Type
     * @param type
     */
    public static void setType(String type) {
        Globals.type = type;
        setProperty("type", type);
    }

    /**
     * Get the current layout saved in the properties file
     * @return layout
     */
    public static String getLayout() {
        layout = getProperty("layout");
        if (layout == null) layout = "dot";
        return layout;
    }

    /**
     * Set the layout
     * @param layout
     */
    public static void setLayout(String layout) {
        setProperty("layout", layout);
    }

    /**
     * Get the outputDir from the properties file
     * @return outputDir
     */
    public static String getOutputDir() {
        outputDir = getProperty("outputDir");
        if (outputDir == null) outputDir = "./";
        return outputDir;
    }

    /**
     * Set the outputDir
     * @param outputDir
     */
    public static void setOutputDir(String outputDir) {
        Globals.outputDir = outputDir;
        setProperty("outputDir", outputDir);
    }
    
    /**
     * Set the dot executable - whole path + executable
     * @param executable
     */
    public static void setExecutable(String executable) {
    	Globals.executable = executable;
    	setProperty("executable", executable);
    }
    
    /**
     * Get the location of the dot executable
     * @return
     */
    public static String getExecutable() {
    	executable = getProperty("executable");
    	if (executable == null) executable = "dot";
    	return executable;
    }
}
