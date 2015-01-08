package komunikacja;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml { 
	public static void main (String[] args) throws ParserConfigurationException{
		Xml t = new Xml();
		t.readXml() ;
	}
	public void readXml () throws ParserConfigurationException {
	    File fXmlFile = new File("dane/pojazdy.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = null;
	    
	    try {
	        doc = dBuilder.parse(fXmlFile);
	    } catch (SAXException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    doc.getDocumentElement().normalize();

	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	    NodeList nList = doc.getElementsByTagName("pojazd");

	    System.out.println("----------------------------");

	    for (int temp = 0; temp < nList.getLength(); temp++) {

	        Node nNode = nList.item(temp);

	        System.out.println("\nCurrent Element :" + nNode.getNodeName());

	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	            Element eElement = (Element) nNode;

	            System.out.println("nazwa : " + eElement.getElementsByTagName("nazwa").item(0).getTextContent());            

	        }
	   }
	}
}