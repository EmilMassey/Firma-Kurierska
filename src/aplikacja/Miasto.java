package aplikacja;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

public class Miasto extends JFrame implements ActionListener, KeyListener {
    
    public String komenda;              // tymczasowe
    paczka paczka = new paczka(5, 13);  // tymczasowe
    
    public JFrame frame1, frame2;
    public JTextField EkranKomend;
    public JTextArea EkranOdpowiedzi;
    public JScrollPane przewijanie;
    public static Miasto Miasto;
    public Dimension wymiar;
    public RenderPanel renderpanel;
    public Timer timer = new Timer(10, this);

    public Miasto() {
            
            wymiar = Toolkit.getDefaultToolkit().getScreenSize();
            
            //----------------Ekran z mapÄ…
            frame1 = new JFrame("Miasto");
            frame1.setSize(25*30, 25*30);
            frame1.setVisible(true);
            frame1.setResizable(false);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.add(renderpanel = new RenderPanel());
            frame1.addKeyListener((KeyListener) this);
            frame1.setLocation(wymiar.width/2 - frame1.getWidth()/2, wymiar.height/2 - frame1.getHeight()/2);
            
            //----------------Paramety Okna Komend
            EkranKomend = new JTextField();
            EkranKomend.setBounds(10, 10, 400, 50);
            EkranKomend.setVisible(true);
            EkranKomend.addKeyListener(this);
            
            //----------------Paramety Okna Odpowiedzi
            EkranOdpowiedzi = new JTextArea();
            EkranOdpowiedzi.setVisible(true);
            EkranOdpowiedzi.setEditable(false);
            EkranOdpowiedzi.setLineWrap(true);
            EkranOdpowiedzi.setWrapStyleWord(true);
            przewijanie = new JScrollPane(EkranOdpowiedzi);
            przewijanie.setBounds(10, 65, 400, 500);
            przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  
            
            //----------------Ekran z komendami
            frame2 = new JFrame("Okno na komendy");
            frame2.setSize(430, 610);
            frame2.setVisible(true);
            frame2.setLayout(null);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.add(EkranKomend);
            frame2.add(przewijanie);
            Start();
        }
        
        public void Start() {
            timer.start();
        }
        
        public static void main(String[] args) {
            Miasto = new Miasto();    
        }
    
    public void actionPerformed(ActionEvent ae) {
        renderpanel.repaint();
    }
    
    public void keyReleased(KeyEvent K) {}
    public void keyTyped(KeyEvent K) {}
    public void keyPressed(KeyEvent K) {
        switch(K.getKeyCode()) {
            case 10:
            	komenda = EkranKomend.getText();
                
                Kontroler kontroler = new Kontroler();
                String odpowiedz;
                
    			try {
    				odpowiedz = kontroler.interpretujKomende(komenda);
    			} catch (Exception e) {
    				odpowiedz = e.getMessage();
    			}       
    			
    			if(komenda != null){
                    EkranOdpowiedzi.append("\n\nKomenda: " + EkranKomend.getText());
                }
    			
    			EkranOdpowiedzi.append("\nOdpowiedź: " + odpowiedz);
                
                EkranKomend.setText(null);
                break;
            case 32:
                System.out.println("ULICA: " + Pozycja(renderpanel.pozycja_pojazdu_1_x, renderpanel.pozycja_pojazdu_1_y));
            break;
            
            case 37:
            	try {
	                if(renderpanel.Mapa[renderpanel.pozycja_pojazdu_1_y][renderpanel.pozycja_pojazdu_1_x - 1] == 0){
	                    renderpanel.pozycja_pojazdu_1_x = renderpanel.pozycja_pojazdu_1_x - 1;
	                }
            	} catch (Exception e) {}
            break;
            case 38:
            	try {
	                if(renderpanel.Mapa[(renderpanel.pozycja_pojazdu_1_y - 1)][renderpanel.pozycja_pojazdu_1_x] == 0){
	                    renderpanel.pozycja_pojazdu_1_y = renderpanel.pozycja_pojazdu_1_y - 1;
	                }
            	} catch (Exception e) {}
            break;
            case 39:
            	try {
	                if(renderpanel.Mapa[renderpanel.pozycja_pojazdu_1_y][(renderpanel.pozycja_pojazdu_1_x + 1)] == 0){
	                    renderpanel.pozycja_pojazdu_1_x = renderpanel.pozycja_pojazdu_1_x + 1;
	                }
            	} catch (Exception e) {}
            break;
            case 40:
            	try {
	                if(renderpanel.Mapa[(renderpanel.pozycja_pojazdu_1_y + 1)][renderpanel.pozycja_pojazdu_1_x] == 0){
	                    renderpanel.pozycja_pojazdu_1_y = renderpanel.pozycja_pojazdu_1_y + 1;
	                }
            	} catch (Exception e) {}
            break;
        }
    }
    
    String Pozycja(int x, int y) {
        if(x >= 9 && x <= 15 && y >= 9 && y <= 15){return "RONDO KURIERSKIE";}
        switch(x){
            case 2:
                if(y >= 3 && y <= 5){return "WINNA " + (y - 2);}
            break;
            case 4:
                if(y >= 7 && y <= 12){return "AL.NIEPODLEGĹ�OĹšCI " + (y - 6);}
            break;
            case 5:
                if(y >= 0 && y <= 1){return "GĂ“RNA " + (y + 1);}
                if(y >= 12 && y <= 23){return "AL.NIEPODLEGĹ�OĹšCI " + (y - 5);}
            break;
            case 8:
                if(y >= 19 && y <= 20){return "CICHA " + (y - 18);}
            break;
            case 10:
                if(y >= 0 && y <= 2){return "DWORCOWA " + (y + 1);}
            break;
            case 11:
                if(y >= 22 && y <= 24){return "WARSZAWSKA " + (y - 21);}
            break;
            case 12:
                if(y >= 7 && y <= 8){return "BIAĹ�A " + (y - 6);}
                if(y >= 16 && y <= 17){return "JASNA " + (y - 15);}
            break;
            case 17:
                if(y >= 19 && y <= 23){return "MATEJKI " + (y - 18);}
            break;
            case 18:
                if(y >= 0 && y <= 2){return "RYNKOWA " + (y + 1);}
                if(y >= 13 && y <= 17){return "SZKOLNA " + (y - 12);}
            break;
            case 20:
                if(y >= 4 && y <= 9){return "POZNAĹ�SKA " + (y - 3);}
            break;
            case 21:
                if(y >= 9 && y <= 15){return "POZNAĹ�SKA " + (y - 2);}
            break;
            case 22:
                if(y >= 19 && y <= 24){return "LEPPERA " + (y - 18);}
            break;
        }
        switch(y){
            case 2:
                if(x >= 0 && x <= 5){return "DĹ�UGA " + (x + 1);}
            break;
            case 3:
                if(x >= 10 && x <= 24){return "RUBAKA " + (x - 9);}
            break;
            case 6:
                if(x >= 0 && x <= 19){return "JANA PAWĹ�A II " + (x + 1);}
            break;
            case 9:
                if(x >= 22 && x <= 24){return "ĹšLÄ„SKA " + (x - 21);}
            break;    
            case 12:
                if(x >= 6 && x <= 8){return "CIEMNA " + (x - 5);}
                if(x >= 16 && x <= 20){return "MUZYCZNA " + (x - 15);}
            break;    
            case 15:
                if(x >= 22 && x <= 24){return "DOLNA " + (x - 21);}
            break;
            case 17:
                if(x >= 0 && x <= 4){return "3 MAJA " + (x + 1);}
            break;
            case 18:
                if(x >= 8 && x <= 24){return "KOĹšCIUSZKI " + (x - 7);}
            break;    
            case 21:
                if(x >= 6 && x <= 11){return "MOKRA " + (x - 5);}
            break;    
            case 23:
                if(x >= 0 && x <= 5){return "KOPERNIKA " + (x + 1);}
                if(x >= 18 && x <= 21){return "SZPITALNA " + (x - 17);}
            break;
        }
        return "Jeszcze tu trzeba dodaÄ‡!!";
    }
    
}
