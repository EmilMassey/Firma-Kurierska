package komunikacja;

import java.lang.String;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

public class Analiza {
	private Document slownik;
	
	public Analiza(String sciezka) {
		try {		
			File plik = new File(sciezka);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			this.slownik = dBuilder.parse(plik);
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}
	
	public Typ okreslTyp(String komenda) {
		NodeList akcje = this.slownik.getElementsByTagName("akcja");
		
		for(int i=0; i<akcje.getLength(); i++) {
			Node slowa = akcje.item(i);
			
			if(slowa.getNodeType() == Node.ELEMENT_NODE) {
				Element wyrazy  = (Element) slowa;
				for(int k=0; k<wyrazy.getElementsByTagName("*").getLength(); k++) {
					if(komenda.indexOf(wyrazy.getElementsByTagName("slowo").item(k).getTextContent()) != -1) {
						Typ typ = new Typ(wyrazy.getAttribute("typ"));
						return typ;
					}
				}					
			}
		}
		Typ typ = new Typ(false);
		return typ;
	}

	public Akcja okreslAkcje(String komenda) {
		NodeList akcje = this.slownik.getElementsByTagName("akcja");
		
		for(int i=0; i<akcje.getLength(); i++) {
			Node slowa = akcje.item(i);
			
			if(slowa.getNodeType() == Node.ELEMENT_NODE) {
				Element wyrazy  = (Element) slowa;
				for(int k=0; k<wyrazy.getElementsByTagName("*").getLength(); k++) {
					Element wyraz = (Element) wyrazy.getElementsByTagName("slowo").item(k);
					if(komenda.indexOf(wyraz.getTextContent()) != -1) {
						Akcja akcja = new Akcja(wyrazy.getAttribute("nazwa"), wyraz.getAttribute("mnoga"));
						return akcja;
					}
				}					
			}
		}
		Akcja akcja = new Akcja(false);
		return akcja;
	}
	
	public Obiekt okreslObiekt(String komenda) {
		NodeList obiekty = this.slownik.getElementsByTagName("obiekt");
		
		for(int i=0; i<obiekty.getLength(); i++) {
			Node slowa = obiekty.item(i);
			
			if(slowa.getNodeType() == Node.ELEMENT_NODE) {
				Element wyrazy  = (Element) slowa;
				for(int k=0; k<wyrazy.getElementsByTagName("*").getLength(); k++) {
					Element wyraz = (Element) wyrazy.getElementsByTagName("slowo").item(k);
					if(komenda.indexOf(wyraz.getTextContent()) != -1) {
						Obiekt obiekt = new Obiekt(wyrazy.getAttribute("nazwa"), wyraz.getAttribute("mnoga"));
						return obiekt;
					}
				}					
			}
		}
		Obiekt obiekt = new Obiekt(false);
		return obiekt;
	}
	
	public String okreslArgument(String komenda) {
		int start = komenda.indexOf("'") + 1;
		int koniec = komenda.indexOf("'", start);
		
		if(start == 0 || koniec == start)
			return "";
		if(koniec == -1)
			return komenda.substring(start);
		
		return komenda.substring(start, koniec);
	}
	
	public ArrayList <String> okreslArgumenty(String komenda) {
		ArrayList <String> argumenty = new ArrayList <String>();
		
		int start = komenda.indexOf("'") + 1;
		int koniec = komenda.indexOf("'", start);
		
		while(start != 0 && koniec != -1) {			
			if(koniec != start) {			
				if(koniec == -1)
					argumenty.add(komenda.substring(start));
				else				
					argumenty.add(komenda.substring(start, koniec));
			} else
				argumenty.add("");
			
			start = komenda.indexOf("'", koniec+1) + 1;
			koniec = komenda.indexOf("'", start);
		}
		
		return argumenty;
	}
}
