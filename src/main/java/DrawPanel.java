import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawPanel extends JPanel {

    byte[] byteStream;
    private BufferedImage image;
    JScrollPane jScrollPane = new JScrollPane(this);

    public DrawPanel() {
        super();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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

    public void setByteStream(byte[] byteStream) {
        this.byteStream = byteStream;
        repaint();
    }

    public void readFile(String filename) {
        try {
            image = ImageIO.read(new File(filename));
            repaint();
        } catch (IOException ex) {

        }
    }
}
