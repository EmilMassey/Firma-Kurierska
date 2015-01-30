package dane;

import java.io.File;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Baza_danych {
	private String nazwa;
	private String folder;
	public Document xml;
	public XPath xpath;
	
	public Baza_danych(String nazwa, String folder) {
		this.nazwa = nazwa;
		this.folder = folder;
		this.zaladujXML();
	}
	
	private String sciezkaDoXml() {
		return this.folder + "/" + this.nazwa + ".xml";
	}
	
	private void zaladujXML() {
		String sciezka = this.sciezkaDoXml();
		
		try {		
			File plik = new File(sciezka);
			
			if(!plik.exists())
				throw new Exception("Nie mo¿na za³adowaæ pliku XML: " + sciezka);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			this.xml = dBuilder.parse(plik);
			XPathFactory xPathfactory = XPathFactory.newInstance();
			xpath = xPathfactory.newXPath();
			
		} catch (Exception e) {
			e.printStackTrace();
	    }	
	}
	
	private void aktualizujXml() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(this.xml);
			StreamResult result = new StreamResult(new File(this.sciezkaDoXml()));
			transformer.transform(source, result);
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public int dodajRekord(String nazwa_rekordu, Map <String, String> dane) {
		this.zaladujXML();
		
		Node root = this.xml.getFirstChild();
		
		int id = Integer.parseInt(this.xml.getElementsByTagName("kolejny_id").item(0).getTextContent());
		
		Element rekord = this.xml.createElement(nazwa_rekordu);
		rekord.setAttribute("id", Integer.toString(id));		
		root.appendChild(rekord);
		
		for (Map.Entry<String, String> entry : dane.entrySet()) {
			Element element = this.xml.createElement(entry.getKey());
			element.appendChild(this.xml.createTextNode(entry.getValue()));
			rekord.appendChild(element);
		}
		
		this.xml.getElementsByTagName("kolejny_id").item(0).setTextContent(Integer.toString(id+1));
		this.aktualizujXml();
		
		return id;																		// Zwraca identyfikator dodanego rekordu
	}
	
	public void usunRekord(String id) throws Exception {
		this.zaladujXML();
		
		Node root = this.xml.getFirstChild();
		Node rekord = (Node) this.xpath.compile("//*[@id='" + id + "']").evaluate(this.xml, XPathConstants.NODE);
		if(rekord != null)
			root.removeChild(rekord);
		else
			throw new Exception("Nie mamy w bazie rekordu o identyfikatorze " + id + ".");
		
		this.aktualizujXml();		
	}
	
	public void edytujRekord(String id, String nazwa_elementu, String nowa_wartosc) throws Exception {		
		this.zaladujXML();
		
		Node rekord = (Node) this.xpath.compile("//*[@id='" + id + "']/" + nazwa_elementu).evaluate(this.xml, XPathConstants.NODE);
		if(rekord != null)
			rekord.setTextContent(nowa_wartosc);
		else
			throw new Exception("Nie mamy w bazie takiego rekordu.");
		
		this.aktualizujXml();
	}
}
