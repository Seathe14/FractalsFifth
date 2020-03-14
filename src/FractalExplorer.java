import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay ImageDisplay;
    private FractalGenerator Fractal;
    private Rectangle2D.Double Range2D;

    public FractalExplorer(int DisplaySize) {
        displaySize = DisplaySize;
        // Creating image display
        ImageDisplay = new JImageDisplay(displaySize, displaySize);
        // Creating reference to base object
        Fractal = new Tricorn();
        Range2D = new Rectangle2D.Double(0, 0, displaySize, displaySize);
        Fractal.getInitialRange(Range2D);
    }

    public void createAndShowGui() {
        ImageDisplay.setLayout(new BorderLayout());
        // Creating Window
        JFrame Frame = new JFrame("Fractal Explorer");
        Frame.add(ImageDisplay, BorderLayout.CENTER);

        //Adding combobox to Ui
        JComboBox<String> comboBox1 = new JComboBox<String>();
        comboBox1.addItem("Tricorn");
        comboBox1.addItem("Mandelbrot");
        comboBox1.addItem("Something");
        comboBox1.addItem("Burning Ship");
        ComboBoxHandler comboBoxHandler = new ComboBoxHandler();
        comboBox1.addActionListener(comboBoxHandler);
        //Frame.add(comboBox1,BorderLayout.NORTH);
        //Adding label to UI
        JLabel cmbBoxLbl = new JLabel("Fractal:");
      //  Frame.add(cmbBoxLbl,BorderLayout.NORTH);


        //Creating Jpanel
        JPanel panel1 = new JPanel();
        panel1.add(cmbBoxLbl);
        panel1.add(comboBox1);
        Frame.add(panel1,BorderLayout.NORTH);


        // Button Reset position and Event Handler
        JButton resetButton = new JButton("Reset");
        ButtonHandler buttonHandler = new ButtonHandler();
        resetButton.addActionListener(buttonHandler);
        Frame.add(resetButton, BorderLayout.SOUTH);
        // Button save image
        JButton saveImage = new JButton("Save Image");
        saveImage.addActionListener(buttonHandler);


        // Jpanel for buttons
        JPanel panel2 = new JPanel();
        panel2.add(saveImage);
        panel2.add(resetButton);
        Frame.add(panel2,BorderLayout.SOUTH);



        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // MouseHandler
        MouseHandler click = new MouseHandler();
        ImageDisplay.addMouseListener(click);

        Frame.pack();
        Frame.setVisible(true);
        Frame.setResizable(false);
    }
    private void drawFractal()
    {
        for(int x = 0;x<displaySize;x++)
        {
            for(int y =0;y<displaySize;y++)
            {
                double xCoord = Fractal.getCoord(Range2D.x, Range2D.x + Range2D.width,displaySize,x);
                double yCoord = Fractal.getCoord(Range2D.y, Range2D.y + Range2D.height,displaySize,y);

                int iteration = Fractal.numIterations(xCoord,yCoord);

                if(iteration == -1)
                    ImageDisplay.drawPixel(x,y,0);
                else
                {
                    float hue = 0.7f + (float)iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue,1.0f,1.0f);
                    ImageDisplay.drawPixel(x,y,rgbColor);
                }
            }
        }
        ImageDisplay.repaint();
    }
    private class ComboBoxHandler implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combobox = (JComboBox)e.getSource();
            String command = (String)combobox.getSelectedItem();
            switch(command) {
                case "Tricorn":
                    Fractal = new Tricorn();
                    break;
                case "Mandelbrot":
                    Fractal = new Mandelbrot();
                    break;
                case "Something":
                    Fractal = new Something();
                    break;
                case "Burning Ship":
                    Fractal = new BurningShip();
                    break;
            }
            Fractal.getInitialRange(Range2D);
            drawFractal();
        }
    }
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Reset")) {
                Fractal.getInitialRange(Range2D);
                drawFractal();
            }
            if(command.equals("Save Image"))
            {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images","png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                //fileChooser.showSaveDialog(ImageDisplay);
                if(fileChooser.showSaveDialog(ImageDisplay)!=JFileChooser.APPROVE_OPTION )
                    return;
                File file = fileChooser.getSelectedFile();
                try{
                    ImageIO.write(ImageDisplay.BufImage,"png",new File(file.toString() + ".jpg"));
                } catch (IOException exc)
                {
                    JOptionPane.showMessageDialog(ImageDisplay,exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            double xCoord = Fractal.getCoord(Range2D.x, Range2D.x + Range2D.width, displaySize,x);
            double yCoord = Fractal.getCoord(Range2D.y, Range2D.y + Range2D.height, displaySize,y);
            Fractal.recenterAndZoomRange(Range2D,xCoord,yCoord,0.5);
            drawFractal();
        }
    }
    public static void main(String[] args)
    {
        FractalExplorer FE = new FractalExplorer (800);
        FE.createAndShowGui();
        FE.drawFractal();
    }
}
