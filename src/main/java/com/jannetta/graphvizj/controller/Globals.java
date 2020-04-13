package com.jannetta.graphvizj.controller;

import java.io.*;
import java.util.Properties;

public class Globals {
    static private Properties properties = null;
    static private String lastDir = "";
    static private String outputDir = "";
    static private String type = "png";
    static private String layout = "dot";

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

    public static String getProperty(String property) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(property);
    }

    public static String getLastDir() {
        return getProperty("lastdir");
    }

    public static void setLastDir(String lastDir) {
        setProperty("lastdir", lastDir);
    }

    public static String getType() {
        type = getProperty("type");
        if (type == null) type = "png";
        return type;
    }

    public static void setType(String type) {
        Globals.type = type;
        setProperty("type", type);
    }


    public static String getLayout() {
        layout = getProperty("layout");
        if (layout == null) layout = "dot";
        return layout;
    }

    public static void setLayout(String layout) {
        setProperty("layout", layout);
    }

    public static String getOutputDir() {
        outputDir = getProperty("outputDir");
        if (outputDir == null) outputDir = "./";
        return outputDir;
    }

    public static void setOutputDir(String outputDir) {
        Globals.outputDir = outputDir;
        setProperty("outputDir", outputDir);
    }
}
