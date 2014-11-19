package dane;
import obiekty.Pojazd;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.xpath.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Pojazdy {
	private Baza_danych baza_danych;
	private ArrayList <Pojazd> pojazdy;
	
	public Pojazdy() throws Exception {
		try {
			this.baza_danych = new Baza_danych("pojazdy", "dane");
			this.zaladujListePojazdow();
		} catch (Exception e) {
			throw new Exception("Kurczê! Mam problem z list¹ pojazdów. " + e.getMessage());
		}
	}
	
	private void zaladujListePojazdow() throws Exception {
		try {
			this.pojazdy = new ArrayList <Pojazd>();
			
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
							p.getElementsByTagName("grafika").item(0).getTextContent()							
						);
					this.pojazdy.add(poj);
				}
			}
			
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void nowyPojazd(Map <String, String> dane) throws Exception {
		try {
			this.baza_danych.dodajRekord("pojazd", dane);
			this.zaladujListePojazdow();
		} catch (Exception e) {
			throw new Exception("Szefie, nie mogê sobiê poradziæ z dodaniem pojazdu. " + e.getMessage());
		}
	}
	
	public ArrayList <String> listaNazwPojazdow() {
		ArrayList <String> lista = new ArrayList <String>();
		
		for(Pojazd pojazd : this.pojazdy) {
			lista.add(pojazd.getNazwa());
		}
		
		return lista;
	}
}
