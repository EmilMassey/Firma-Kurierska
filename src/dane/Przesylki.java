package dane;

import obiekty.Przesylka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Przesylki {
	private Baza_danych baza_danych;
	private Map<Integer, Przesylka> przesylki;
	
	public Przesylki() throws Exception {
		try {
			this.baza_danych = new Baza_danych("przesylki", "dane");
			this.zaladujListePrzesylek();
		} catch (Exception e) {
			throw new Exception("Kurczê! Mam problem z list¹ przesy³ek. " + e.getMessage());
		}
	}
	
	private void zaladujListePrzesylek() throws Exception {
		try {
			this.przesylki = new HashMap <Integer, Przesylka>();
			
			NodeList lista = (NodeList)baza_danych.xpath.compile("//przesylka").evaluate(baza_danych.xml, XPathConstants.NODESET);
			for(int i=0; i<lista.getLength(); i++) {
				Node przesylka = lista.item(i);
				if(przesylka.getNodeType() == Node.ELEMENT_NODE) {
					Element p = (Element) przesylka;
					Przesylka prz = new Przesylka(
							Integer.parseInt(p.getAttribute("id")),
							Integer.parseInt(p.getElementsByTagName("typ").item(0).getTextContent()),
							Integer.parseInt(p.getElementsByTagName("waga").item(0).getTextContent()),
							Boolean.parseBoolean(p.getElementsByTagName("dostarczona").item(0).getTextContent())							
						);
					this.przesylki.put(Integer.parseInt(p.getAttribute("id")), prz);
				}
			}
			
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void nowaPrzesylka(Map <String, String> dane) throws Exception {
		try {
			this.baza_danych.dodajRekord("przesylka", dane);
			this.zaladujListePrzesylek();
		} catch (Exception e) {
			throw new Exception("Szefie, nie mogê sobiê poradziæ z dodaniem nowej przesy³ki. " + e.getMessage());
		}
	}
	
	public ArrayList <String> listaPrzesylek() {
		ArrayList <String> lista = new ArrayList <String>();
		
		for(Map.Entry<Integer, Przesylka> przesylka : this.przesylki.entrySet()) {
			lista.add(przesylka.getValue().getId() + ": " + przesylka.getValue().getTyp() + ", " + przesylka.getValue().getWaga() + "kg, " + przesylka.getValue().isDostarczona());
		}
		
		return lista;
	}
	
	public void usunPrzesylke(String id) throws Exception {
		this.baza_danych.usunRekord(id);
		this.zaladujListePrzesylek();
	}
}
