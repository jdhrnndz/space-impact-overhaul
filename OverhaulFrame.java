import javax.swing.JFrame;
import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class OverhaulFrame extends JFrame{
    private BufferedImage image;
    public OverhaulFrame() throws Exception{
        image = ImageIO.read(new File("icon.png"));
        this.add(new PlayPanel());
        this.setSize(800, 600);
        this.setLocationRelativeTo( null );
        this.setTitle( "Space Impact :: OVERHAUL" );
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setIconImage(image);
    }

    public static void main(String args[]) throws Exception{
        new OverhaulFrame();
    }
}
