import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class Main{
    public static CardFrame cf;
	public static void main(String args[]) throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        cf = new CardFrame("Card Frame");
	}
}

class CardFrame extends JFrame implements ActionListener {
    JButton firstCard;
    JButton prevCard;
    JButton nextCard;
    JLayeredPane lpane;
    CardLayoutPanel cardpanel;
    CardLayout c1;
    private BufferedImage image;


    public CardFrame(String title) throws Exception{
        image = ImageIO.read(new File("icon.png"));
        ImageIcon start = new ImageIcon("start.png");
        ImageIcon help = new ImageIcon("help.png");
        ImageIcon quit = new ImageIcon("quit.png");
        firstCard = new JButton("            PLAY           ", start);
        prevCard = new JButton("         Help         ", help);
        nextCard = new JButton("         Quit         ", quit);
        setTitle( "Space Impact :: OVERHAUL" );
        setIconImage(image);
        lpane=new JLayeredPane();
		firstCard.setBounds(0, 0, 200, 40);
		prevCard.setBounds(0, 0, 200, 40);
		nextCard.setBounds(0, 0, 200, 40);
		nextCard.addActionListener(this);
		prevCard.addActionListener(this);
		firstCard.addActionListener(this);
        cardpanel= new CardLayoutPanel();
		c1 = (CardLayout)cardpanel.getLayout();
	 	setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		add(lpane, BorderLayout.CENTER);
		lpane.setBounds(0, 0, 800, 600);
		lpane.setBackground(Color.BLACK);
		cardpanel.setBounds(0, 0, 800, 600);
		firstCard.setBorderPainted(false);
		firstCard.setOpaque(false);
		firstCard.setContentAreaFilled(false);
        firstCard.setBounds(500, 220, 200, 60);
        prevCard.setBorderPainted(false);
		prevCard.setOpaque(false);
		prevCard.setContentAreaFilled(false);
		prevCard.setBounds(500, 275, 200, 60);
		nextCard.setBorderPainted(false);
		nextCard.setOpaque(false);
		nextCard.setContentAreaFilled(false);
		nextCard.setBounds(500, 330, 200, 60);
		lpane.add(cardpanel, new Integer(0), 0);
		lpane.add(firstCard, new Integer(1), 0);
		lpane.add(prevCard, new Integer(2), 0);
		lpane.add(nextCard, new Integer(3), 0);
		pack();
		setVisible(true);
  		setResizable(false);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==firstCard){
            firstCard.setVisible(false);
            prevCard.setVisible(false);
	   		nextCard.setVisible(false);
	   		try{
                cardpanel.add(cardpanel.initPlayPanel(), "two");
	   		}catch(Exception cardpanel){}
	    	c1.show(cardpanel,"two");
	    	cardpanel.playpanel.startTimer();
        }
	    else if(ae.getSource()==prevCard){
            c1.show(cardpanel,"three");
            firstCard.setVisible(false);
            prevCard.setVisible(false);
		   	nextCard.setVisible(false);
        }
	    else if(ae.getSource()==nextCard){
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "SPACE IMPACT :: OVERHAUL", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
			else
                c1.show(cardpanel,"one");
	    }
    }

class CardLayoutPanel extends JPanel{
    BackPanel backpanel;
	PlayPanel playpanel;
	HelpPanel helppanel;
	NextPanel nextpanel;

	public CardLayoutPanel() throws Exception{
        this.setLayout(new CardLayout(0,0));
		backpanel = new BackPanel(new ImageIcon("Menu.png").getImage());
        playpanel = initPlayPanel();
		helppanel = new HelpPanel(new ImageIcon("help1.gif").getImage());
		nextpanel = new NextPanel(new ImageIcon("help2.png").getImage());
		this.add(backpanel,"one");
		this.add(playpanel, "two");
		this.add(helppanel,"three");
		this.add(nextpanel, "four");
	}

	public PlayPanel initPlayPanel() throws Exception{
        return playpanel = new PlayPanel();
	}
}


 class BackPanel extends JPanel {
    private Image img;
	Dimension dim;

    public BackPanel(String img) {
        this(new ImageIcon("Galaxy.png").getImage());
    }

    public BackPanel(Image img) {
        this.img = img;
        dim=Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, dim.width, dim.height);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		int imwidth = dim.width;
		int imheight = dim.height;
		g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
    }
}

class NextPanel extends JPanel implements ActionListener{
    private Image img;
        Dimension dim;
		JButton back;
		JButton main;
		ImageIcon start = new ImageIcon("Menubutton.png");
    ImageIcon prev = new ImageIcon("prev.png");

    public NextPanel(String img){
        this(new ImageIcon(img).getImage());
    }

    public NextPanel(Image img){
        this.img = img;
	    dim=Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0, 0, dim.width, dim.height);
	    back=new JButton("Previous", prev);
	    main=new JButton("Main Menu", start);
	    setLayout(null);
	    back.addActionListener(this);
	    main.addActionListener(this);
	    back.setBounds(10, 10, 160, 50);
	    back.addActionListener(this);
	    back.setBorderPainted(false);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
	    main.setBounds(170, 10, 160, 50);
	    main.addActionListener(this);
	    main.setBorderPainted(false);
		main.setOpaque(false);
		main.setContentAreaFilled(false);
	    add(back);
	    add(main);
	    setVisible(true);
	  }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int imwidth = dim.width;
        int imheight = dim.height;
        g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
    }

 	public void actionPerformed(ActionEvent evnt){
        if (evnt.getSource()==back){
	    	setVisible(false);
	    	c1.show(cardpanel,"three");
	    }
	    if (evnt.getSource()==main){
	    	setVisible(false);
	    	Main.cf.firstCard.setVisible(true);
	    	Main.cf.prevCard.setVisible(true);
	    	Main.cf.nextCard.setVisible(true);
	    }
	}
}

class HelpPanel extends JPanel implements ActionListener{
    private Image img;
    Dimension dim;
    JButton back;
    JButton next;
    ImageIcon start = new ImageIcon("Menubutton.png");
    ImageIcon nextb = new ImageIcon("next.png");

    public HelpPanel(String img){
        this(new ImageIcon(img).getImage());
    }

    public HelpPanel(Image img){
        this.img = img;
	    dim=Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0, 0, dim.width, dim.height);
	    back=new JButton("Main Menu", start);
	    next=new JButton("Next", nextb);
	    setLayout(null);
	    back.addActionListener(this);
	    back.setBorderPainted(false);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
	    next.addActionListener(this);
	    next.setBorderPainted(false);
		next.setOpaque(false);
		next.setContentAreaFilled(false);
	    back.setBounds(10, 10, 160, 50);
	    next.setBounds(170, 10, 160, 50);
	    add(back);
	    add(next);
	    setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int imwidth = dim.width;
        int imheight = dim.height;
        g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
  	}

 	public void actionPerformed(ActionEvent event){
        if(event.getSource()==back){
            setVisible(false);
	    	Main.cf.firstCard.setVisible(true);
	    	Main.cf.prevCard.setVisible(true);
	    	Main.cf.nextCard.setVisible(true);

	    }
        else if(event.getSource()==next){
	    	setVisible(false);
	    	c1.show(cardpanel,"four");
	    }
	}
}

}
