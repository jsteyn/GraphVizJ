package com.jannetta.graphvizj.components;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawPanel extends JPanel {
	Logger logger = LoggerFactory.getLogger(getClass());

    byte[] byteStream;
    private BufferedImage image;

    public DrawPanel() {
        super(new BorderLayout());
    }

    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(image.getWidth(), image.getHeight());
        } else return new Dimension(getWidth(), getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (byteStream != null)
            g.drawBytes(byteStream, 0, 0, 0, 0);
        if (image != null)
            g.drawImage(image, 0, 0, this);
    }

    public byte[] getByteStream() {
        return byteStream;
    }

    protected void setByteStream(byte[] byteStream) {
        this.byteStream = byteStream;
        repaint();
    }

    protected void readFile(String filename) {
        try {
            image = ImageIO.read(new File(filename));
            repaint();
        } catch (IOException ex) {
            logger.debug("File not found.");
        }
    }
}
