package dane;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import obiekty.Zlecenie;

public class Zlecenia {
	private Baza_danych baza_danych;
	private Map<Integer, Zlecenie> zlecenia;
	
	public Zlecenia() throws Exception {
		try {
			this.baza_danych = new Baza_danych("zlecenia", "dane");
			this.zaladujListeZlecen();
		} catch (Exception e) {
			throw new Exception("Kurczê! Mam problem z list¹ zleceñ. " + e.getMessage());
		}
	}
	
	private void zaladujListeZlecen() throws Exception {
		try {
			this.zlecenia = new HashMap <Integer, Zlecenie>();
			
			NodeList lista = (NodeList)baza_danych.xpath.compile("//zlecenie").evaluate(baza_danych.xml, XPathConstants.NODESET);
			for(int i=0; i<lista.getLength(); i++) {
				Node zlecenie = lista.item(i);
				if(zlecenie.getNodeType() == Node.ELEMENT_NODE) {
					Element p = (Element) zlecenie;
					
					String[] tmp;					
					tmp = p.getElementsByTagName("dataNadania").item(0).getTextContent().split("-");
					Calendar dataNadania = new GregorianCalendar(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
					tmp = p.getElementsByTagName("dataOdbioru").item(0).getTextContent().split("-");
					Calendar dataOdbioru = new GregorianCalendar(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
					tmp = p.getElementsByTagName("deadline").item(0).getTextContent().split("-");
					Calendar deadline = new GregorianCalendar(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));

					
					Zlecenie zlec = new Zlecenie(
							Integer.parseInt(p.getAttribute("id")),
							Integer.parseInt(p.getElementsByTagName("pojazd").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("przesylka").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("startX").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("startY").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("celX").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("celY").item(0).getTextContent()),
							dataNadania,
							dataOdbioru,
							deadline,
							Boolean.parseBoolean(p.getElementsByTagName("wykonane").item(0).getTextContent()),
							Boolean.parseBoolean(p.getElementsByTagName("wTrakcie").item(0).getTextContent())
						);
					this.zlecenia.put(Integer.parseInt(p.getAttribute("id")), zlec);
				}
			}
			
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void noweZlecenie(Map <String, String> dane) throws Exception {
		try {
			this.baza_danych.dodajRekord("zlecenie", dane);
			this.zaladujListeZlecen();
		} catch (Exception e) {
			throw new Exception("Szefie, nie mogê sobiê poradziæ z dodaniem nowego zlecenia. " + e.getMessage());
		}
	}
	
	public ArrayList <String> listaZlecen() throws Exception {
		ArrayList <String> lista = new ArrayList <String>();
		Nieruchomosci nieruchomosci = new Nieruchomosci();
		
		for(Map.Entry<Integer, Zlecenie> zlecenie : this.zlecenia.entrySet()) {
			if(!zlecenie.getValue().isWykonane()) {
				String start = nieruchomosci.podajAdres(new Point((int)zlecenie.getValue().getStartX(), (int)zlecenie.getValue().getStartY()));
				String cel = nieruchomosci.podajAdres(new Point((int)zlecenie.getValue().getCelX(), (int)zlecenie.getValue().getCelY()));
				lista.add("Zlecenie numer " + zlecenie.getValue().getId() + " dla pojazdu " + zlecenie.getValue().getPojazd() + " z " + start + " do " + cel);
			}
		}
		
		return lista;
	}
	
	public void usunZlecenie(String id) throws Exception {
		this.baza_danych.usunRekord(id);
		this.zaladujListeZlecen();
	}
	
	public void wygenerujZlecenia(int liczbaZlecen) throws Exception {
		int wymiarX = 25;
		int wymiarY = 25;
		
		try {
			for(int i=0; i<liczbaZlecen; i++) {
				Random los = new Random();
				
				Map <String, String> danePrzesylki = new HashMap<String, String>();			// generujemy przesylke
				Przesylki przesylki = new Przesylki();
				danePrzesylki.put("typ", Integer.toString(los.nextInt(4)));					// generujemy typ 0-4
				danePrzesylki.put("waga", Integer.toString(los.nextInt(5000)+50));			// generujemy wagê 50-5049
				danePrzesylki.put("dostarczona", "false");
				int idPrzesylki = przesylki.nowaPrzesylka(danePrzesylki);
				
				Map <String, String> daneZlecenia = new HashMap<String, String>();			// generujemy zlecenie
				
				Pojazdy pojazdy = new Pojazdy();
				ArrayList <Integer> listaIdentyfikatorow = pojazdy.listaIdentyfikatorow();
				int idPojazdu = listaIdentyfikatorow.get(los.nextInt(listaIdentyfikatorow.size()));	// losujemy identyfikator
				
				Nieruchomosci nieruchomosci = new Nieruchomosci();
				
				int startX = los.nextInt(wymiarX);
				int startY = los.nextInt(wymiarY);				
				while(!nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, new Point(startX, startY))) {					// generuje tylko istniej¹ce adresy
					startX = los.nextInt(wymiarX);
					startY = los.nextInt(wymiarY);
				}
				
				int celX = los.nextInt(wymiarX);
				int celY = los.nextInt(wymiarY);				
				while(!nieruchomosci.nawigacja.czyUlica(nieruchomosci.mapa, new Point(celX, celY))) {
					celX = los.nextInt(wymiarX);
					celY = los.nextInt(wymiarY);
				}
				
				Calendar teraz = new GregorianCalendar();											// dzisiejsza data
				Calendar termin = new GregorianCalendar();											
				termin.add(Calendar.DAY_OF_MONTH, los.nextInt(15));									// termin to losowane 0-14 dni od dzisiaj
				String dataTeraz = teraz.get(Calendar.YEAR) + "-" + teraz.get(Calendar.MONTH) + "-" + teraz.get(Calendar.DAY_OF_MONTH);
				String dataTermin = termin.get(Calendar.YEAR) + "-" + termin.get(Calendar.MONTH) + "-" + termin.get(Calendar.DAY_OF_MONTH);
				
				daneZlecenia.put("pojazd", Integer.toString(idPojazdu));
				daneZlecenia.put("przesylka", Integer.toString(idPrzesylki));
				daneZlecenia.put("startX", Integer.toString(startX));					// losujemy x Ÿród³a przesy³ki
				daneZlecenia.put("startY", Integer.toString(startY));					// losujemy y Ÿród³a przesy³ki
				daneZlecenia.put("celX", Integer.toString(celX));						// losujemy x celu przesy³ki
				daneZlecenia.put("celY", Integer.toString(celY));						// losujemy y celu przesy³ki
				daneZlecenia.put("dataNadania", dataTeraz);
				daneZlecenia.put("dataOdbioru", "2999-0-1");							// jeszcze nie znamy, ale ustawmy
				daneZlecenia.put("deadline", dataTermin);
				daneZlecenia.put("wykonane", "false");
				daneZlecenia.put("wTrakcie", "false");
				
				this.noweZlecenie(daneZlecenia);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void dostarczZlecenie(int id) throws Exception {
		Calendar teraz = new GregorianCalendar();
		
		String rok = Integer.toString(teraz.get(Calendar.YEAR));
		String miesiac = Integer.toString(teraz.get(Calendar.MONTH));
		String dzien = Integer.toString(teraz.get(Calendar.DAY_OF_MONTH));
		
		String dataDostarczenia = rok + "-" + miesiac + "-" + dzien;
		
		this.baza_danych.edytujRekord(Integer.toString(id), "wykonane", "true");
		this.baza_danych.edytujRekord(Integer.toString(id), "dataOdbioru", dataDostarczenia);
		
		Przesylki przesylki = new Przesylki();
		przesylki.oddajPrzesylke(id);
		
		this.zaladujListeZlecen();
	}
	
	public Point wezPrzesylke(int id, Point wspolrzedne) throws Exception {
		for(Map.Entry<Integer, Zlecenie> zlecenie : this.zlecenia.entrySet()) {
			Point start = new Point(zlecenie.getValue().getStartX(), zlecenie.getValue().getStartY());
			if(!zlecenie.getValue().isWykonane() && !zlecenie.getValue().isWTrakcie() && wspolrzedne.equals(start)) {
				if(zlecenie.getValue().getPojazd() == id) {
					this.baza_danych.edytujRekord(Integer.toString(zlecenie.getValue().getId()), "wTrakcie", "true");
					this.zaladujListeZlecen();
					return new Point((int)zlecenie.getValue().getCelX(), (int)zlecenie.getValue().getCelY());
				} else {
					throw new Exception("T¹ paczkê powinien zabraæ pojazd nr " + id);
				}
			}
		}
		throw new Exception("W tym miejscu nie ma ¿adnej paczki");
	}
	
	public void dostarcz(int id, Point wspolrzedne) throws Exception {
		Boolean jestCelem = false;
		
		for(Map.Entry<Integer, Zlecenie> zlecenie : this.zlecenia.entrySet()) {
			Point cel = new Point(zlecenie.getValue().getCelX(), zlecenie.getValue().getCelY());
			if(!zlecenie.getValue().isWykonane() && zlecenie.getValue().isWTrakcie() && wspolrzedne.equals(cel)) {
				if(zlecenie.getValue().getPojazd() == id) {
					this.baza_danych.edytujRekord(Integer.toString(zlecenie.getValue().getId()), "wTrakcie", "false");
					this.dostarczZlecenie(zlecenie.getValue().getId());
					this.zaladujListeZlecen();
				} else {
					throw new Exception("T¹ paczkê powinien zabraæ pojazd nr " + id);
				}
				jestCelem = true;
			}
		}
		if(!jestCelem)
			throw new Exception("Pomy³ka! Nie mam ¿adnej paczki do dostarczenia na ten adres");
	}
}
