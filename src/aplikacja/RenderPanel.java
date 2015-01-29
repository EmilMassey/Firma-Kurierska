package aplikacja;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import dane.Pojazdy;


public class RenderPanel extends JPanel {
    private ArrayList <Point> pozycjePojazdow = new ArrayList <Point> ();
    public ArrayList <Point> trasa = new ArrayList <Point> ();
    
    int Mapa[][] = {
        {1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,4,4,4,4,4,4},
        {1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,4,4,4,4,4,4},
        {0,0,0,0,0,0,1,1,1,1,0,1,1,1,1,1,1,1,0,4,4,4,4,4,4},
        {1,1,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1},
        {1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1},
        {1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1},
        {1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1},
        {1,1,1,1,0,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0},
        {1,1,1,1,0,1,1,1,1,0,3,3,3,3,3,0,1,1,1,1,1,0,1,1,1},
        {1,1,1,1,0,1,1,1,1,0,3,3,3,3,3,0,1,1,1,1,1,0,1,1,1},
        {1,1,1,1,0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,1,1,1},
        {1,1,1,1,1,0,1,1,1,0,3,3,3,3,3,0,1,1,0,1,1,0,1,1,1},
        {1,1,1,1,1,0,1,1,1,0,3,3,3,3,3,0,1,1,0,1,1,0,1,1,1},
        {1,1,1,1,1,0,1,1,1,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0},
        {1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
        {0,0,0,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1},
        {1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,0,1,1},
        {1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1,1},
        {0,0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0,0,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1}
    };
    
    protected void paintComponent(Graphics g){		
        super.paintComponent(g);        

        try {
        	this.pozycjePojazdow = new Pojazdy().wspolrzedneWszystkich();
        } catch (Exception e) {}
        
        
        g.setColor(Color.BLACK);
        for(int a = 0; a < 25; a++){
            for(int b = 0; b < 25; b++){
                if(Mapa[a][b] == 0){
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(b*30, a*30, 30, 30);
                }else if(Mapa[a][b] == 3){
                    g.setColor(Color.ORANGE);
                    g.fillRect(b*30, a*30, 30, 30);
                }
                switch(Mapa[a][b]){
                    case 1:
                        g.setColor(Color.RED);
                        g.fillRect(b*30, a*30, 30, 30);
                    break;
                    case 2:
                        g.setColor(Color.BLUE);
                        g.fillRect(b*30, a*30, 30, 30);
                    break;
                    case 4:
                        g.setColor(Color.GREEN);
                        g.fillRect(b*30, a*30, 30, 30);
                    break;
                    case 5:
                        g.setColor(Color.PINK);
                        g.fillRect(b*30, a*30, 30, 30);
                    break;
                }
            }
        }        

        g.setColor(Color.CYAN);
        for(Point punkt : this.trasa) {
        	g.fillOval((int)punkt.getX()*30 + 15, (int)punkt.getY()*30 + 15, 5, 5);
        }
        
        g.setColor(Color.MAGENTA);
        for(Point pozycja : this.pozycjePojazdow) {
        	g.fillOval((int)pozycja.getX()*30, (int)pozycja.getY()*30, 29, 29);
        }
    }
    
}