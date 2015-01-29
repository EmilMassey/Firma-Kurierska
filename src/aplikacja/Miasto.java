package aplikacja;

import java.awt.Dimension;
import java.awt.Point;
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

import obiekty.Pojazd;
import mapa.Mapa;
import dane.Nieruchomosci;
import dane.Pojazdy;

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
    private Nieruchomosci nieruchomosci;
    private Pojazdy pojazdy;
    private int sterowanyPojazd;    

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
            
            try {
            	this.nieruchomosci = new Nieruchomosci();
            	this.pojazdy = new Pojazdy();
            	this.sterowanyPojazd = this.pojazdy.listaIdentyfikatorow().get(0);			// pierwszy ze wszystkich pojazdów domyślnie
            } catch (Exception e) {}
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
                
                Kontroler kontroler = new Kontroler(Miasto);
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
            
            case 37:
            	try {
            		Point noweWspolrzedne = new Point((int)pojazdy.podajWspolrzedne((int)this.sterowanyPojazd).getX() - 1, (int)pojazdy.podajWspolrzedne(this.sterowanyPojazd).getY());
            		if(nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, noweWspolrzedne))
            			pojazdy.zmienPozycjePojazdu(this.sterowanyPojazd, noweWspolrzedne);
            	} catch (Exception e) {}
            break;
            case 38:
            	try {
            		Point noweWspolrzedne = new Point((int)pojazdy.podajWspolrzedne((int)this.sterowanyPojazd).getX(), (int)pojazdy.podajWspolrzedne(this.sterowanyPojazd).getY() - 1);
            		if(nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, noweWspolrzedne))
            			pojazdy.zmienPozycjePojazdu(this.sterowanyPojazd, noweWspolrzedne);
            	} catch (Exception e) {}
            break;
            case 39:
            	try {
            		Point noweWspolrzedne = new Point((int)pojazdy.podajWspolrzedne((int)this.sterowanyPojazd).getX() + 1, (int)pojazdy.podajWspolrzedne(this.sterowanyPojazd).getY());
            		if(nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, noweWspolrzedne))
            			pojazdy.zmienPozycjePojazdu(this.sterowanyPojazd, noweWspolrzedne);
            	} catch (Exception e) {}
            break;
            case 40:
            	try {
            		Point noweWspolrzedne = new Point((int)pojazdy.podajWspolrzedne((int)this.sterowanyPojazd).getX(), (int)pojazdy.podajWspolrzedne(this.sterowanyPojazd).getY() + 1);
            		if(nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, noweWspolrzedne))
            			pojazdy.zmienPozycjePojazdu(this.sterowanyPojazd, noweWspolrzedne);
            	} catch (Exception e) {}
            break;
        }
    }    
    
    public void zmienSterowany(int id) throws Exception {
    	if(pojazdy.czyIstnieje(id))
    		this.sterowanyPojazd = id;
    	else throw new Exception("Nie mamy takiego pojazdu");
    }
    
    public int getSterowany() { return this.sterowanyPojazd; }
}
