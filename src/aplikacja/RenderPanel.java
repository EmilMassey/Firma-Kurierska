package aplikacja;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dane.Pojazdy;


public class RenderPanel extends JPanel {
    public ArrayList <Point> trasa = new ArrayList <Point> ();
    public BufferedImage miasto;   
    public BufferedImage budynki;
    public Map <String, BufferedImage> grafikiPojazdow = new HashMap <String, BufferedImage> ();    
    
    protected void paintComponent(Graphics g){		
        super.paintComponent(g);
        
        g.drawImage(miasto, 0, 0, null);

        g.setColor(Color.BLUE);
        for(Point punkt : this.trasa) {
        	g.fillOval((int)punkt.getX()*30 + 15, (int)punkt.getY()*30 + 15, 5, 5);
        }
        
        g.setColor(Color.MAGENTA);
        
    	try {
    		Pojazdy pojazdy = new Pojazdy();
	        for(Integer id : pojazdy.listaIdentyfikatorow())
	        	g.drawImage(grafikiPojazdow.get(pojazdy.podajGrafike(id)), (int)pojazdy.podajWspolrzedne(id).getX()*30, (int)pojazdy.podajWspolrzedne(id).getY()*30, null);
    	} catch (Exception e) {}

        g.drawImage(budynki, 0, 0, null);
    }
    
}