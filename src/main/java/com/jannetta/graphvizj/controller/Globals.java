package com.jannetta.graphvizj.controller;

import java.io.*;
import java.util.Properties;

public class Globals {
    static private Properties properties = null;
    static private String lastDir = "";

    public static void loadProperties() {
        properties  = new Properties();
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

}
