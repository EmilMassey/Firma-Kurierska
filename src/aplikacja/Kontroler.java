package aplikacja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	public void interpretujKomende(String komenda) throws Exception {
		ArrayList <String> argumenty = analiza.okreslArgumenty(komenda);
		String argument = analiza.okreslArgument(komenda);
		
		komenda = this.zamienPolskie(komenda.toLowerCase());						// ¯eby uproœciæ, nie mog³o byæ wczeœniej, ¿eby argumentów nie zmieniaæ
		
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
										lista += nazwa + "\n";
									}
									System.out.println("(id: nazwa)\n" + lista);
									break;
							}
							break;
						case "zarzadzanie":
							switch(akcja.getNazwa()) {
								case "dodanie":
									Map <String, String> dane = new HashMap<String, String>();
									if(!argumenty.isEmpty() && argumenty.size() >= 9) {
										dane.put("nazwa", argumenty.get(0));
										dane.put("typ", argumenty.get(1));
										dane.put("pojemnosc", argumenty.get(2));
										dane.put("typ_ladunku", argumenty.get(3));
										dane.put("predkosc_max", argumenty.get(4));
										dane.put("spalanie", argumenty.get(5));
										dane.put("poziom_paliwa", argumenty.get(6));
										dane.put("max_pojemnosc_baku", argumenty.get(7));
										dane.put("grafika", argumenty.get(8));
										
										pojazdy.nowyPojazd(dane);
										System.out.println("Doda³em pojazd!");
									} else {
										throw new Exception("Muszê znaæ takie informacje o pojeŸdzie: nazwa, typ, pojemnoœæ, typ ³adunku, prêdkoœæ maksymalna, spalanie, poziom paliwa, maksymalna pojemnoœæ baku, adres grafiki");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										pojazdy.usunPojazd(argument);
										System.out.println("Wykreœli³em pojazd z naszej bazy.");										
									} else {
										throw new Exception("O który samochód chodzi? Móg³by Szef podaæ jego identyfikator?");
									}
									break;
								case "edycja":
									if(argumenty.size() >= 2) {
										pojazdy.zmienNazwePojazdu(argumenty.get(0), argumenty.get(1));
										System.out.println("Nazwa zmieniona, szefie!");
									}
									else {
										throw new Exception("Nazwê którego samochodu mam zmieniæ? Proszê podaæ id i now¹ nazwê");
									}
									break;
							}
					}
					break;
				default:
					throw new Exception("Nie rozumiem komendy. " + typ.getNazwa() + ": " + akcja.getNazwa() + " " + obiekt.getNazwa());
			}
		} else {
			throw new Exception("Nie rozumiem komendy");
		}
	}
}
