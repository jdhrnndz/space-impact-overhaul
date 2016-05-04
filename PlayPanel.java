import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class PlayPanel extends JPanel implements ActionListener{
    private Timer fire = new Timer(250, this);
    private Timer gameTimer = new Timer(3, this);
    private SpaceCraft ship;
    public Graphics g;
    public BufferedImage bg;
    public BufferedImage abg;
    public BufferedImage healthbar, subhealth, gameOver, menuButton, heart, victory;
    private int subimgy = 2400;
    private int timeCount, posx, posy;
    private ArrayList enemies = new ArrayList();
    private int lifeCount = 3;
    private int playerScore, impact;
    private Boss boss;

    JButton back;
    ImageIcon start = new ImageIcon("Menubutton.png");

    public PlayPanel() throws Exception{
        this.addKeyListener(new Adapter());
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.ship = new SpaceCraft(fire, this);
        ship.setLives(lifeCount);
        abg = ImageIO.read(new File("Galaxy.gif"));
        healthbar = ImageIO.read(new File("healthbar.png"));
        gameOver = ImageIO.read(new File("GameOver.gif"));
        menuButton = ImageIO.read(new File("Menubutton.png"));
        heart = ImageIO.read(new File("heart.png"));
        victory = ImageIO.read(new File("victory.png"));
        fire.setRepeats(false);
        setLayout(null);
        back=new JButton("Back", start);
        back.setBorderPainted(false);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
	    back.addActionListener(this);
	    back.setBounds(10, 10, 180, 40);
	    add(back);
	    setVisible(true);
    }

    public void startTimer(){
        gameTimer.start();
    }

    public void actionPerformed(ActionEvent e){
        ship.move();

        if(timeCount == 18200){
        try{
            boss = new Boss();
        }catch(Exception boss){}
        }
        if(timeCount >= 18200){
            boss.move();
            boss.setTimeCount(timeCount);
        }


        if (e.getSource()==back) {
	    	setVisible(false);
	    	Main.cf.firstCard.setVisible(true);
	    	Main.cf.prevCard.setVisible(true);
	    	Main.cf.nextCard.setVisible(true);
		   	gameTimer.stop();
	    }

        if(e.getSource() == gameTimer){
            this.timeCount++;

            if(this.subimgy == 60)
                this.subimgy = 2400;
            this.subimgy--;
            try{
                bg = abg.getSubimage(0, this.subimgy, 800, 600);
            }catch(Exception abc){}

            ArrayList proj = ship.getProjectiles();
            for(int i = 0; i < proj.size(); i++){
                Projectile p = (Projectile) proj.get(i);
                if(p.getVisible()){
                    p.move();
                }
                else{
                    proj.remove(i);
                }
            }
            if(timeCount%200==0){
                try{
                    enemies.add(new Enemy(10, 200, timeCount));
                }catch(Exception enemy){}
            }
            for(int i = 0; i < enemies.size(); i++){
                Enemy enemy = (Enemy) enemies.get(i);
                if(enemy.getVisible()){
                    try{enemy.move();}catch(Exception move){}
                }
                else{
                    if(enemy.deathDelay == 50) enemies.remove(i);
                    if(enemy.getVisible() == false && enemy.deathByCollision == false) enemies.remove(i);
                }
            }
        }
        this.repaint();
    }

    public class Adapter extends KeyAdapter{
        public void keyPressed(KeyEvent keyP){
            ship.keyPressed(keyP);
        }

        public void keyReleased(KeyEvent keyR){
            ship.keyReleased(keyR);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        checkCollisions();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(bg, 0, 0, this);
        subhealth = healthbar.getSubimage(0, 0, ship.getHealth()*2, 40);
        g.drawImage(menuButton, 1, 5, this);
        g2d.drawImage(subhealth, 200, 10, this);

        if(ship.getHealth() == 1 && ship.getLives() > 0){
            lifeCount--;
            this.ship = new SpaceCraft(fire, this);
            ship.setLives(lifeCount);
        }

        else if(ship.getLives() == 0){
            gameTimer.stop();
            g2d.drawImage(gameOver, 0, 0, this);
            g2d.drawImage(menuButton, 340, 480, this);
            back.setBounds(340, 480, 180, 40);
            g2d.dispose();
        }

        else if(timeCount > 18200){
            if(boss.getHealth() <= 0){
            gameTimer.stop();
            g2d.drawImage(victory, 0, 0, this);
            g2d.drawImage(menuButton, 340, 480, this);
            back.setBounds(340, 480, 180, 40);
            g2d.dispose();
            }
        }

        if(timeCount > 18200 && boss.isVisible()){
            g2d.drawImage(boss.getImage(), boss.getPosX(), boss.getPosY(), this);
            g2d.setColor(Color.GREEN);
            g2d.fillRect(boss.getPosX(), boss.getPosY(), boss.getHealth()*3, 5);
        }
        for(int i = 0; i<lifeCount; i++){
            g2d.drawImage(heart, 410+i*50, 0, this);
        }
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("DIALOGINPUT", Font.BOLD, 20));
        g2d.drawString(playerScore + "", 600, 30);
        g2d.drawImage(ship.getImage(), ship.getPosX(), ship.getPosY(), this);
        ArrayList proj = ship.getProjectiles();
        if(proj.size() >= 7)
            proj.remove(0);
        for (int i = 0; i < proj.size(); i++ ){
            Projectile p = (Projectile)proj.get(i);
            g2d.drawImage( p.getImage(), p.getPosX(), p.getPosY(), this );
        }
        for (int i = 0; i < enemies.size(); i++ ){
            Enemy enemy = (Enemy)enemies.get(i);
            g2d.drawImage(enemy.getImage(), enemy.getPosX(), enemy.getPosY(), this);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void checkCollisions(){
        Rectangle rectship = ship.getBounds();
        for (int j = 0; j<enemies.size(); j++) {
            Enemy enemy = (Enemy)enemies.get(j);
            Rectangle rectenemy = enemy.getBounds();
            if (rectship.intersects(rectenemy)) {
                if(enemy.deathDelay == 49){
                ship.receiveDamage(enemy.getCollisionDamage());
                this.playerScore += enemy.getScorePoint()-50;
                }
                enemy.setVisible(false);
                enemy.setExploded(true);
                enemy.deathByCollision = true;
            }
        }

        ArrayList proj = ship.getProjectiles();
        for (int i = 0; i < proj.size(); i++ ){
            Projectile p = (Projectile)proj.get(i);
            Rectangle rectp = p.getBounds();
            for (int j = 0; j<enemies.size(); j++) {
                Enemy enemy = (Enemy)enemies.get(j);
                Rectangle rectenemy = enemy.getBounds();
                if (rectp.intersects(rectenemy)) {
                    p.setVisible(false);
                    enemy.setVisible(false);
                    enemy.setExploded(true);
                    enemy.deathByCollision = true;
                    this.playerScore += enemy.getScorePoint();
                }
            }
        }

        if(timeCount > 18200){
            Rectangle rectboss = boss.getBounds();
            if (rectship.intersects(rectboss) && impact == 0){
                impact++;
                ship.receiveDamage(boss.getCollisionDamage());
                this.playerScore += 50;
                if(boss.getHealth()<=0){
                    this.playerScore += boss.getScorePoint();
                    boss.setVisible(false);
                }
            }
            if (rectship.intersects(rectboss) == false)
                impact = 0;

            for (int i = 0; i < proj.size(); i++ ){
                Projectile p = (Projectile)proj.get(i);
                Rectangle rectp = p.getBounds();
                if (rectp.intersects(rectboss)){
                        p.setVisible(false);
                        boss.receiveDamage(1);
                        this.playerScore += 200;
                        if(boss.getHealth()<=0){
                            this.playerScore += boss.getScorePoint();
                            boss.setVisible(false);
                        }
                }
            }
        }
    }
}
