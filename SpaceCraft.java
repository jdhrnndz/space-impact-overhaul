import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpaceCraft{
    public ArrayList projectiles;
    public int posx, posy, dx, dy;
    private int width = 40, height = 42;
    public int subimgx, subimgy;
    private BufferedImage image, subimage;
    private Timer fire;
    private Graphics g;
    private PlayPanel panel;
    private int health = 100;
    private int lifeCount;

    public SpaceCraft(Timer fire, PlayPanel panel){
        this.fire = fire;
        this.panel = panel;
        projectiles = new ArrayList();
        subimgx = 41;
        subimgy = 1;
        posx = 380;
        posy = 520;
        try{
            image = ImageIO.read(new File("arboris.png"));
        } catch(Exception e){}
    }

    public int getPosX(){
        return posx;
    }

    public int getPosY(){
        return posy;
    }

    public Image getImage(){
        subimage = image.getSubimage(subimgx, subimgy, width, height);
        return subimage;
    }

    public ArrayList getProjectiles(){
        return projectiles;
    }

    public void setProjectiles(ArrayList projectiles){
        this.projectiles = projectiles;
    }

    public void move(){
        if(this.dx==-1 && this.posx >= 0 || this.dx==1 && this.posx+width <= 790)
            this.posx += this.dx;

        if(this.dy==-1 && this.posy >= 60 || this.dy==1 && this.posy+this.height <= 580)
            this.posy += this.dy;
    }

    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_UP){
            dy = -1;
            subimgy = 86;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            dy = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            dx = -1;
            subimgx = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            dx = 1;
            subimgx = 81;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(fire.isRunning()==false)
                fire();
            fire.start();
        }
    }

    public void fire() {
        projectiles.add(new Projectile(posx+20, posy, 1));
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            dx = 0;
            subimgx = 41;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            dx = 0;
            subimgx = 41;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            dy = 0;
            subimgy = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            dy = 0;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(posx, posy, width, height);
    }

    public void receiveDamage(int damage){
        this.health = this.health-damage;
        if(this.health <= 0)
            this.health = 1;
    }

    public int getHealth(){
        return this.health;
    }

    public void setLives(int lifeCount){
        this.lifeCount = lifeCount;
    }

    public int getLives(){
        return this.lifeCount;
    }
}
