package aplikacja;

import komunikacja.*;
import dane.Pojazdy;

public class Kontroler {
	private Analiza analiza;
	private Pojazdy pojazdy;
	
	public Kontroler() {
		try {
			this.analiza = new Analiza("dane/slownik.xml");
			this.pojazdy = new Pojazdy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String zamienPolskie(String txt) {
		txt = txt.replace("�", "a");
		txt = txt.replace("�", "c");
		txt = txt.replace("�", "e");
		txt = txt.replace("�", "l");
		txt = txt.replace("�", "n");
		txt = txt.replace("�", "o");
		txt = txt.replace("�", "s");
		txt = txt.replace("�", "z");
		txt = txt.replace("�", "z");
		txt = txt.replace("z", "z");
		
		return txt;
	}
	
	public void interpretujKomende(String komenda) {
		komenda = this.zamienPolskie(komenda.toLowerCase());
		
		Typ typ = this.analiza.okreslTyp(komenda);
		Akcja akcja = this.analiza.okreslAkcje(komenda);
		Obiekt obiekt = this.analiza.okreslObiekt(komenda);
		
		if(typ.czyIstnieje() && akcja.czyIstnieje() && obiekt.czyIstnieje()) {		
			switch(obiekt.getNazwa()) {
				case "pojazd":
					switch(typ.getNazwa()) {
						case "raport":
							switch(akcja.getNazwa()) {
								case "pokaz":
									String lista = "";
									for(String nazwa : this.pojazdy.listaNazwPojazdow()) {
										lista += nazwa;
									}
									System.out.println(lista);
									break;
							}
							break;
					}
					break;
				default:
					System.out.println("Nie rozumiem komendy. " + typ.getNazwa() + ": " + akcja.getNazwa() + " " + obiekt.getNazwa());
			}
		} else {
			System.out.println("Nie rozumiem komendy");
		}
	}
}
