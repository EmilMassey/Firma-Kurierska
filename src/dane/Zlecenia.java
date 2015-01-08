package dane;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
					tmp = p.getElementsByTagName("dataDostarczenia").item(0).getTextContent().split("-");
					Calendar dataDostarczenia = new GregorianCalendar(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
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
							dataDostarczenia,
							deadline,
							Boolean.parseBoolean(p.getElementsByTagName("wykonane").item(0).getTextContent())
						);
					this.zlecenia.put(Integer.parseInt(p.getAttribute("id")), zlec);
				}
			}
			
		} catch (XPathExpressionException e) {
			throw new Exception(e.getMessage());
		}
	}
}
