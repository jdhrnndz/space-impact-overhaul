import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.lang.Math;

public class Enemy{
    private int collisionDamage, gox = 400, goy = 100;
    public int scorePoint, enemyNo;
    private int posx;
    private int posy;
    private BufferedImage image, explosion, subexp;
    private boolean isVisible = true, exploded = false;
    private Random random = new Random();
    private int timeCount, decx, height = 40, width = 40;
    public int deathDelay;
    public boolean deathByCollision;

    public Enemy(int posx, int posy, int timeCount) throws Exception{
        this.posx = posx;
        this.posy = posy;
        this.timeCount = timeCount;
        enemyNo = random.nextInt(5);
        explosion = ImageIO.read(new File("explosion.png"));
    }

    public void fire(){}

    public int getScorePoint(){
        return 100;
    }

    public int getCollisionDamage(){
        return 5;
    }

    public int getPosX(){
        return this.posx;
    }

    public int getPosY(){
        return this.posy;
    }

    public boolean getVisible(){
        return isVisible;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public void move()throws Exception{
        if(timeCount>=200 && timeCount<2200 || timeCount>=10200 && timeCount<12200){
            image = ImageIO.read(new File("enemy1.png"));
            this.posx++;
            this.posy = (int)-(((posx-360)*(posx-360))/(4*80))+400;
        }
        else if(timeCount>=2200 && timeCount<4200 || timeCount>=12200 && timeCount<14200){
            image = ImageIO.read(new File("enemy2.png"));
            this.decx++;
            this.posx = 740-this.decx;
            this.posy = (int)-(((posx-360)*(posx-360))/(4*80))+450;
        }
        else if(timeCount>=4200 && timeCount<6200 || timeCount>=12200 && timeCount<14200){
            image = ImageIO.read(new File("enemy3.png"));
            this.posx = this.posy*2-380;
            this.posy++;
            this.decx=0;
        }
        else if(timeCount>=6200 && timeCount<8200 || timeCount>=14200 && timeCount<16200){
            image = ImageIO.read(new File("enemy4.png"));
            this.posy = this.posx++;
        }
        else if(timeCount>=8200 && timeCount<10200  || timeCount>=16200 && timeCount<18200){
            image = ImageIO.read(new File("enemy5.png"));
            this.posx = 740-this.decx++;
            this.posy = 550-this.decx;
            this.width = 55;
        }

        if (this.posx > 800 || this.posy < 0){
            this.isVisible = false;
        }
    }

    public Image getImage(){
        if(exploded){
            if(deathDelay%10 == 0)
            subexp = explosion.getSubimage((deathDelay/10)*39, 0, 40, 40);
            this.deathDelay++;
            return this.subexp;
        }
        else return this.image;
    }

    public Rectangle getBounds() {
        return new Rectangle(posx, posy, width, height);
    }

    public void setExploded(boolean exploded){
        this.exploded = exploded;
    }
}
