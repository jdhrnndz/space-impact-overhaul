import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class Projectile{
    private int subimgx = 7, subimgy = 133;
    private int posx, posy;
    private int height = 9, width = 3;
    private final int MISSILE_SPEED = 7, shooter;
    private boolean isVisible;
    private BufferedImage image;

    public Projectile(int posx, int posy, int shooter){
        this.posx = posx;
        this.posy = posy;
        isVisible = true;
        this.shooter = shooter;
    }

    public Image getImage() {
        try{
            if(shooter == 1){
                image = ImageIO.read(new File("arboris.png"));
                image = image.getSubimage(subimgx, subimgy, width, height);
            }
            if(shooter == 2){
                image = ImageIO.read(new File("redbullet.png"));
                this.width = 10;
                this.height = 40;
            }
        }
        catch(Exception e){}
        return image;
    }

    public int getPosX() {
        return this.posx;
    }

    public int getPosY() {
        return this.posy;
    }

    public boolean getVisible(){
        return isVisible;
    }

    public void move() {
        if(shooter == 1){
            this.posy -= MISSILE_SPEED;
            if(this.posy < 0){
                isVisible = false;
            }
        }
        else if(shooter == 2){
            this.posy += MISSILE_SPEED;
            if(this.posy > 600){
                isVisible = false;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(posx, posy, width, height);
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
}
