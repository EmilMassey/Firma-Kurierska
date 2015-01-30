/* Autor: Emil Masiakowski (emil.masiakowski@gmail.com) */

package mapa;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Nawigacja {
	private Point[] kierunki = {
			new Point(0, -1),				// góra
			new Point(1, 0),				// prawo
			new Point(0, 1),				// dó³
			new Point(-1, 0)				// lewo
	};
	private Map <Point, Wektor> wektory = new HashMap <Point, Wektor> ();
	
	private Point dodajPunkty(Point a, Point b) {
		return new Point((int)a.getX() + (int)b.getX(), (int)a.getY() + (int)b.getY());
	}
	
	private void wypelnijMapeWektorow(Mapa mapa) {											// pomocnicza metoda dodaj¹ca wszystkie wektory do HashMapy wektorów
		for(Point wektor : mapa.getPunkty())
			wektory.put(wektor, new Wektor(false, Integer.MAX_VALUE, null));
	}
	
	private Boolean znajdzDroge(Mapa mapa, Point start, Point koniec) {
		if(!czyUlica(mapa, start) && !czyUlica(mapa, koniec))												// któryœ z punktów nie jest ulic¹
			return false;

		Boolean znalezionoDroge = false;
		LinkedBlockingQueue <Point> kolejkaWektorow = new LinkedBlockingQueue <Point> ();					// kolejka
		wypelnijMapeWektorow(mapa);
		
		Wektor v0 = new Wektor(true, 0, null);																// pierwszy wektor
		wektory.put(start, v0);
		kolejkaWektorow.add(start);
		
		while(!znalezionoDroge && !kolejkaWektorow.isEmpty()) {												// dopóki nie znaleziono drogi i kolejka nie jest pusta
			Point biezacyWektor = kolejkaWektorow.remove();
			wektory.get(biezacyWektor).setOdwiedzony(true);
			
			for(int i=0; i<4; i++) {																		// idziemy we wszystkie kierunki
				Point sasiadujacyWektor = dodajPunkty(biezacyWektor, kierunki[i]);
				
				if(czyUlica(mapa, sasiadujacyWektor) && !wektory.get(sasiadujacyWektor).getOdwiedzony()) {
					int odleglosc = wektory.get(biezacyWektor).getOdleglosc() + 1;
					
					if(odleglosc < wektory.get(sasiadujacyWektor).getOdleglosc()) {
						wektory.get(sasiadujacyWektor).setOdleglosc(odleglosc);								// ustaw now¹ odleg³oœc, je¿eli wiêksza ni¿ do tej pory
						wektory.get(sasiadujacyWektor).setPoprzednik(biezacyWektor);						// bie¿¹cy wektor jako poprzednik						
					}
					
					if(sasiadujacyWektor.equals(koniec)) {													// dotarliœmy do celu
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
			throw new Exception("Nie mo¿na znaleŸæ drogi.");
		}
	}
	
	public int podajOdleglosc(Mapa mapa, Point start, Point koniec) throws Exception {
		Boolean znaleziono = znajdzDroge(mapa, start, koniec); 
		if(znaleziono) {
			return wektory.get(koniec).getOdleglosc();
		} else {
			throw new Exception("Nie mo¿na znaleŸæ drogi.");
		}
	}
}
