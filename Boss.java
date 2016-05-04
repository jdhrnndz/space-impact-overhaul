import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Rectangle;

public class Boss{
    private BufferedImage image;
    public ArrayList projectiles;
    private int health = 100, width = 300, height = 300, posx = 380, posy = 60, dx = 1, timeCount, dy;
    private boolean isCharging, isBackingUp = false, isVisible = true;
    public Boss() throws Exception{
        image = ImageIO.read(new File("boss.png"));
    }

    public int getWidth(){
        return this.width;
    }

    public void move(){
        if(this.posx+width == 799)
            dx = -1;
        else if(this.posx == 1)
            dx = 1;
        this.posx+=dx;
        if(this.timeCount%1600 == 0){
            dx = 0;
            isCharging = true;
        }
        if(isCharging && this.posy < 600){
            dy = 3;
            dx = 0;
        }
        if(this.posy+height >= 599){
            dy = -1;
            isCharging = false;
            isBackingUp = true;
        }
        if(this.posy <= 60 && isBackingUp){
            dy = 0;
            dx = -1;
            isCharging = false;
            isBackingUp = false;
        }
        this.posy+=dy;
    }

    public int getHealth(){
        return this.health;
    }

    public void setTimeCount(int timeCount){
        this.timeCount = timeCount;
    }

    public void receiveDamage(int damage){
        this.health-=damage;
    }

    public void fire() {
        projectiles.add(new Projectile(posx+20, posy, 1));
    }

    public ArrayList getProjectiles(){
        return projectiles;
    }

    public void setProjectiles(ArrayList projectiles){
        this.projectiles = projectiles;
    }


    public int getPosX(){
        return posx;
    }

    public int getPosY(){
        return posy;
    }

    public Image getImage(){
        return this.image;
    }

    public Rectangle getBounds(){
        return new Rectangle(posx, posy, width, height);
    }

    public int getCollisionDamage(){
        return 9;
    }

    public int getScorePoint(){
        return 2500;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public boolean isVisible(){
        return this.isVisible;
    }
}
