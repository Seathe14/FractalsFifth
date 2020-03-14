import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent{
    public BufferedImage BufImage;
    JImageDisplay(int height,int width)
    {
        BufImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width,height));
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(BufImage,0,0,BufImage.getWidth(),BufImage.getHeight(),null);
        super.paintComponent(g);
    }
    public void clearImage()
    {
        BufImage.setRGB(0,0,0);
    }
    public void drawPixel(int x,int y,int rgbColor)
    {
        BufImage.setRGB(x,y,rgbColor);
    }
}
