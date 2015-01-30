/* Autor: Emil Masiakowski (emil.masiakowski@gmail.com) */

package mapa;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Nawigacja {
	private Point[] kierunki = {
			new Point(0, -1),				// g�ra
			new Point(1, 0),				// prawo
			new Point(0, 1),				// d�
			new Point(-1, 0)				// lewo
	};
	private Map <Point, Wektor> wektory = new HashMap <Point, Wektor> ();
	
	private Point dodajPunkty(Point a, Point b) {
		return new Point((int)a.getX() + (int)b.getX(), (int)a.getY() + (int)b.getY());
	}
	
	private void wypelnijMapeWektorow(Mapa mapa) {											// pomocnicza metoda dodaj�ca wszystkie wektory do HashMapy wektor�w
		for(Point wektor : mapa.getPunkty())
			wektory.put(wektor, new Wektor(false, Integer.MAX_VALUE, null));
	}
	
	private Boolean znajdzDroge(Mapa mapa, Point start, Point koniec) {
		if(!czyUlica(mapa, start) && !czyUlica(mapa, koniec))												// kt�ry� z punkt�w nie jest ulic�
			return false;

		Boolean znalezionoDroge = false;
		LinkedBlockingQueue <Point> kolejkaWektorow = new LinkedBlockingQueue <Point> ();					// kolejka
		wypelnijMapeWektorow(mapa);
		
		Wektor v0 = new Wektor(true, 0, null);																// pierwszy wektor
		wektory.put(start, v0);
		kolejkaWektorow.add(start);
		
		while(!znalezionoDroge && !kolejkaWektorow.isEmpty()) {												// dop�ki nie znaleziono drogi i kolejka nie jest pusta
			Point biezacyWektor = kolejkaWektorow.remove();
			wektory.get(biezacyWektor).setOdwiedzony(true);
			
			for(int i=0; i<4; i++) {																		// idziemy we wszystkie kierunki
				Point sasiadujacyWektor = dodajPunkty(biezacyWektor, kierunki[i]);
				
				if(czyUlica(mapa, sasiadujacyWektor) && !wektory.get(sasiadujacyWektor).getOdwiedzony()) {
					int odleglosc = wektory.get(biezacyWektor).getOdleglosc() + 1;
					
					if(odleglosc < wektory.get(sasiadujacyWektor).getOdleglosc()) {
						wektory.get(sasiadujacyWektor).setOdleglosc(odleglosc);								// ustaw now� odleg�o�c, je�eli wi�ksza ni� do tej pory
						wektory.get(sasiadujacyWektor).setPoprzednik(biezacyWektor);						// bie��cy wektor jako poprzednik						
					}
					
					if(sasiadujacyWektor.equals(koniec)) {													// dotarli�my do celu
						znalezionoDroge = true;
						kolejkaWektorow.clear();
					} else {
						kolejkaWektorow.add(sasiadujacyWektor);
					}
				}
			}
		}
		
		return znalezionoDroge;
	}
	
	public Boolean czyUlica(Mapa mapa, Point wspolrzedne) {
		if(mapa.getSzerokosc() >= (int)wspolrzedne.getX() && mapa.getWysokosc() >= (int)wspolrzedne.getY() && mapa.getPunkty().indexOf(wspolrzedne) != -1)
			return true;
		return false;
	}
	
	public Mapa pokazDroge(Mapa mapa, Point start, Point koniec) throws Exception {
		Boolean znaleziono = znajdzDroge(mapa, start, koniec); 
		
		if(znaleziono) {		
			ArrayList <Point> droga = new ArrayList <Point> ();
			
			Point wektor = koniec;
			while(!wektor.equals(start)) {
				droga.add(wektor);
				wektor = wektory.get(wektor).getPoprzednik();
			}
			droga.add(start);										// dodajmy jeszcze start
			
			return new Mapa(mapa.getSzerokosc(), mapa.getWysokosc(), droga);
		} else {
			throw new Exception("Nie mo�na znale�� drogi.");
		}
	}
	
	public int podajOdleglosc(Mapa mapa, Point start, Point koniec) throws Exception {
		Boolean znaleziono = znajdzDroge(mapa, start, koniec); 
		if(znaleziono) {
			return wektory.get(koniec).getOdleglosc();
		} else {
			throw new Exception("Nie mo�na znale�� drogi.");
		}
	}
}
