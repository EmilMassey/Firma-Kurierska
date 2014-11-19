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
		txt = txt.replace("¹", "a");
		txt = txt.replace("æ", "c");
		txt = txt.replace("ê", "e");
		txt = txt.replace("³", "l");
		txt = txt.replace("ñ", "n");
		txt = txt.replace("ó", "o");
		txt = txt.replace("œ", "s");
		txt = txt.replace("¿", "z");
		txt = txt.replace("Ÿ", "z");
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
