import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageFrameTest extends JFrame
{
    public static void main(String[] args)
    {
        ImageFrameTest imageFrameTest = new ImageFrameTest();
        imageFrameTest.pack();
        imageFrameTest.setVisible(true);
    }
    
    private ImageFrameTest()
    {
        ImagePanel imagePanel = new ImagePanel("Volunteer-Handshake.jpg");
        imagePanel.add(new JButton("How to test"));
        add(imagePanel);
    }
    
    //Creates an image panel for use in backgrounds.
    //Copy and paste this, replace panels you want backgrounds on with it:
    
    private class ImagePanel extends JPanel
    {
        Image backgroundImage;
        ImagePanel(String imagePath)
        {
            try
            {
                backgroundImage = javax.imageio.ImageIO.read(new File(imagePath));
                
            }
            catch(Exception e)
            {
            
            }
        }
    
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int x = (this.getWidth() - backgroundImage.getWidth(null)) / 2;
            int y = (this.getHeight() - backgroundImage.getHeight(null)) / 2;
            try
            {
                g.drawImage(backgroundImage, x, y, null);
            }
            catch(Exception e)
            {
            
            }
        }
    }
}
