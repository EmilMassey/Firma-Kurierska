package dane;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import mapa.Mapa;
import mapa.Nawigacja;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import obiekty.Nieruchomosc;

public class Nieruchomosci {
	private Baza_danych baza_danych;
	private Map <Integer, Nieruchomosc> nieruchomosci;
	public Mapa mapa;
	public Nawigacja nawigacja;
	
	public Nieruchomosci() throws Exception {
		try {
			this.baza_danych = new Baza_danych("nieruchomosci", "baza_danych");
			this.zaladujListeNieruchomosci();
		} catch (Exception e) {
			throw new Exception("Kurczê! Mam problem z list¹ nieruchomoœci. " + e.getMessage());
		}
		try {
			this.nawigacja = new Nawigacja();
		} catch (Exception e) {
			throw new Exception("Nawigacja nie dzia³a...");
		}
	}
	
	private void zaladujListeNieruchomosci() throws Exception {
		try {
			this.nieruchomosci = new HashMap <Integer, Nieruchomosc>();
			
			ArrayList <Point> wspolrzedne = new ArrayList <Point>();
			
			NodeList lista = (NodeList)baza_danych.xpath.compile("//nieruchomosc").evaluate(baza_danych.xml, XPathConstants.NODESET);
			for(int i=0; i<lista.getLength(); i++) {
				Node nieruchomosc = lista.item(i);
				if(nieruchomosc.getNodeType() == Node.ELEMENT_NODE) {
					Element p = (Element) nieruchomosc;
					
					int x = Integer.parseInt(p.getElementsByTagName("wspolrzedne").item(0).getTextContent().split("x")[0]);
					int y = Integer.parseInt(p.getElementsByTagName("wspolrzedne").item(0).getTextContent().split("x")[1]);
					
					Nieruchomosc nie = new Nieruchomosc(
							Integer.parseInt(p.getAttribute("id")),
							p.getElementsByTagName("ulica").item(0).getTextContent(),
							Integer.parseInt(p.getElementsByTagName("numer").item(0).getTextContent()),
							x,
							y
						);
					this.nieruchomosci.put(Integer.parseInt(p.getAttribute("id")), nie);
					wspolrzedne.add(new Point(x, y));
				}
			}
			this.mapa = new Mapa(24, 24, wspolrzedne);
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public String podajAdres(Point wspolrzedne) throws Exception {
		for(Entry <Integer, Nieruchomosc> entry : nieruchomosci.entrySet()) {
			Nieruchomosc nieruchomosc = (Nieruchomosc) entry.getValue();
			if(nieruchomosc.getWspolrzedne().equals(wspolrzedne))
				return nieruchomosc.getUlica() + " " + nieruchomosc.getNumer();
		}
		throw new Exception("Szefie, w tym miejscu nic nie ma!");				
	}
	
	public Point podajWspolrzedne(String adres) throws Exception {											// Funkcja nie dzia³a, nie znajduje
		try {
			int numer = new Integer(Integer.valueOf(adres.replaceAll("\\D", ""), 10));			// pobieramy numer ze stringa, w którym usunêliœmy wszystkie znaki oprócz cyfr
			String ulica = adres.replaceAll("\\d*", "").trim();						// usuwam spacje i numer, ¿eby uzyskaæ sam¹ ulicê
			
			for(Entry <Integer, Nieruchomosc> entry : nieruchomosci.entrySet()) {
				Nieruchomosc nieruchomosc = (Nieruchomosc) entry.getValue();
				if(nieruchomosc.getUlica().equalsIgnoreCase(ulica) && nieruchomosc.getNumer() == numer)
					return nieruchomosc.getWspolrzedne();
			}
			throw new Exception("Nie ma takiego adresu");
		} catch (NumberFormatException e) {									// nie mo¿na pobraæ numeru, pewnie go nie ma
			throw new Exception("A o który numer chodzi?");
		}
	}
}
