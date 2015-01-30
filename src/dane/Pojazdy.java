package dane;
import obiekty.Pojazd;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.xpath.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Pojazdy {
	private Baza_danych baza_danych;
	private Map <Integer, Pojazd> pojazdy;
	
	public Pojazdy() throws Exception {
		try {
			this.baza_danych = new Baza_danych("pojazdy", "baza_danych");
			this.zaladujListePojazdow();
		} catch (Exception e) {
			throw new Exception("Kurczê! Mam problem z list¹ pojazdów. " + e.getMessage());
		}
	}
	
	private void zaladujListePojazdow() throws Exception {
		try {
			this.pojazdy = new HashMap <Integer, Pojazd>();
			
			NodeList lista = (NodeList)baza_danych.xpath.compile("//pojazd").evaluate(baza_danych.xml, XPathConstants.NODESET);
			for(int i=0; i<lista.getLength(); i++) {
				Node pojazd = lista.item(i);
				if(pojazd.getNodeType() == Node.ELEMENT_NODE) {
					Element p = (Element) pojazd;
					Pojazd poj = new Pojazd(
							Integer.parseInt(p.getAttribute("id")),
							p.getElementsByTagName("nazwa").item(0).getTextContent(),
							Integer.parseInt(p.getElementsByTagName("typ").item(0).getTextContent()),
							p.getElementsByTagName("rejestracja").item(0).getTextContent(),
							Integer.parseInt(p.getElementsByTagName("pojemnosc").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("typ_ladunku").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("predkosc_max").item(0).getTextContent()),
							Float.parseFloat(p.getElementsByTagName("spalanie").item(0).getTextContent()),
							Float.parseFloat(p.getElementsByTagName("poziom_paliwa").item(0).getTextContent()),
							Float.parseFloat(p.getElementsByTagName("max_pojemnosc_baku").item(0).getTextContent()),
							p.getElementsByTagName("grafika").item(0).getTextContent(),
							Integer.parseInt(p.getElementsByTagName("wspolrzedne").item(0).getTextContent().split("x")[0]),
							Integer.parseInt(p.getElementsByTagName("wspolrzedne").item(0).getTextContent().split("x")[1])
						);
					this.pojazdy.put(Integer.parseInt(p.getAttribute("id")), poj);
				}
			}
			
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public String generujRejestracje(String rej) {
		String litery = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		
		rej += " ";
		
		Random generator = new Random();
		
		for(int i=0; i<4; i++)
			rej += Integer.toString(generator.nextInt(9));
		rej += litery.charAt(generator.nextInt(litery.length()-1));
		
		return rej;
	}
	
	public void nowyPojazd(Map <String, String> dane) throws Exception {
		try {
			dane.put("rejestracja", generujRejestracje("PO"));
			this.baza_danych.dodajRekord("pojazd", dane);
			this.zaladujListePojazdow();
		} catch (Exception e) {
			throw new Exception("Szefie, nie mogê sobiê poradziæ z dodaniem pojazdu. " + e.getMessage());
		}
	}
	
	public void usunPojazd(String id) throws Exception {
		this.baza_danych.usunRekord(id);
		this.zaladujListePojazdow();
	}
	
	public void zmienNazwePojazdu(String id, String nowa_nazwa) throws Exception {
		this.baza_danych.edytujRekord(id, "nazwa", nowa_nazwa);
		this.zaladujListePojazdow();
	}
	
	public ArrayList <String> listaNazwPojazdow() {
		ArrayList <String> lista = new ArrayList <String>();
		
		for(Map.Entry<Integer, Pojazd> pojazd : this.pojazdy.entrySet()) {
			lista.add(pojazd.getValue().getId() + ": " + pojazd.getValue().getNazwa());
		}
		
		return lista;
	}
	
	public ArrayList <Integer> listaIdentyfikatorow() {				// na potrzeby generowania zleceñ
		ArrayList <Integer> lista = new ArrayList <Integer>();
		for(int id : this.pojazdy.keySet()) {
			lista.add(id);
		}
		
		return lista;
	}
	
	public boolean czyIstnieje(int id) {
		if(this.pojazdy.containsKey(id))
			return true;
		return false;
	}
	
	public void zmienPozycjePojazdu(int id, Point pozycja) throws Exception {
		if(this.czyIstnieje(id)) {
			this.baza_danych.edytujRekord(Integer.toString(id), "wspolrzedne", (int)pozycja.getX() + "x" + (int)pozycja.getY());
			this.zaladujListePojazdow();
		} else
			throw new Exception("Nie mamy takiego pojazdu.");		
	}
	
	public Point podajWspolrzedne(int id) throws Exception {
		if(this.czyIstnieje(id))
			return this.pojazdy.get(id).getWspolrzedne();
		else
			throw new Exception("Nie mamy takiego pojazdu.");
	}
	
	public ArrayList <Point> wspolrzedneWszystkich() throws Exception {
		ArrayList <Point> lista = new ArrayList <Point>();
		for(Map.Entry<Integer, Pojazd> pojazd : this.pojazdy.entrySet()) {
			lista.add(pojazd.getValue().getWspolrzedne());
		}
		
		return lista;
	}
	
	public void zmienGrafike(int id, String grafika) throws Exception {
		if(this.czyIstnieje(id)) {
			String stara = this.pojazdy.get(id).getGrafika();
			grafika = stara.substring(0, stara.indexOf("_") + 1) + grafika + ".gif";
			this.baza_danych.edytujRekord(Integer.toString(id), "grafika", grafika);
			this.zaladujListePojazdow();
		} else
			throw new Exception("Nie mamy takiego pojazdu.");	
	}
	
	public String podajGrafike(int id) throws Exception {
		if(this.czyIstnieje(id)) {
			return this.pojazdy.get(id).getGrafika();
		} else
			throw new Exception("Nie mamy takiego pojazdu.");	
	}
	
	public void wezPrzesylke(int id) throws Exception {
		Zlecenia zlecenia = new Zlecenia();
		zlecenia.wezPrzesylke(id, this.podajWspolrzedne(id));
	}
}
