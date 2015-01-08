package aplikacja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import komunikacja.*;
import dane.Pojazdy;
import dane.Przesylki;

public class Kontroler {
	private Analiza analiza;
	private Pojazdy pojazdy;
	private Przesylki przesylki;
	
	public Kontroler() {
		try {
			this.analiza = new Analiza("dane/slownik.xml");
			this.pojazdy = new Pojazdy();
			this.przesylki = new Przesylki();
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
	
	public String interpretujKomende(String komenda) throws Exception {
		ArrayList <String> argumenty = analiza.okreslArgumenty(komenda);
		String argument = analiza.okreslArgument(komenda);
		String odpowiedz = "";
		
		komenda = this.zamienPolskie(komenda.toLowerCase());						// ¯eby uproœciæ, nie mog³o byæ wczeœniej, ¿eby argumentów nie zmieniaæ
		
		Typ typ = this.analiza.okreslTyp(komenda);
		Akcja akcja = this.analiza.okreslAkcje(komenda);
		Obiekt obiekt = this.analiza.okreslObiekt(komenda);
		
		if(typ.czyIstnieje() && akcja.czyIstnieje() && obiekt.czyIstnieje()) {		
			switch(obiekt.getNazwa()) {
				case "paczka":
					switch(typ.getNazwa()) {
						case "raport":
							switch(akcja.getNazwa()) {
								case "pokaz":
									String lista = "";
									for(String prz : this.przesylki.listaPrzesylek()) {
										lista += prz + "\n";
									}
									odpowiedz = "(id: typ, waga, czy dostarczona)\n" + lista;
									break;
							}
							break;
						case "zarzadzanie":
							switch(akcja.getNazwa()) {
								case "dodanie":
									Map <String, String> dane = new HashMap<String, String>();
									if(!argumenty.isEmpty() && argumenty.size() >= 2) {
										dane.put("typ", argumenty.get(0));
										dane.put("waga", argumenty.get(1));			
										dane.put("dostarczona", "false");
										przesylki.nowaPrzesylka(dane);
										odpowiedz = "Doda³em przesy³kê!";
									} else {
										throw new Exception("Muszê znaæ takie informacje o przesy³ce: typ, waga");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										przesylki.usunPrzesylke(argument);
										odpowiedz = "Wykreœli³em paczkê.";										
									} else {
										throw new Exception("O któr¹ paczkê chodzi? Móg³by Szef podaæ jej identyfikator?");
									}
									break;
							}
					}
					break;
				case "pojazd":
					switch(typ.getNazwa()) {
						case "raport":
							switch(akcja.getNazwa()) {
								case "pokaz":
									String lista = "";
									for(String nazwa : this.pojazdy.listaNazwPojazdow()) {
										lista += nazwa + "\n";
									}
									odpowiedz = "(id: nazwa)\n" + lista;
									break;
								case "wspolrzedne":
									odpowiedz = "x: " + pojazdy.podajWspolrzedne(Integer.parseInt(argument))[0];
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
										// wspó³rzêdne dodaæ
										
										pojazdy.nowyPojazd(dane);
										odpowiedz = "Doda³em pojazd!";
									} else {
										throw new Exception("Muszê znaæ takie informacje o pojeŸdzie: nazwa, typ, pojemnoœæ, typ ³adunku, prêdkoœæ maksymalna, spalanie, poziom paliwa, maksymalna pojemnoœæ baku, adres grafiki");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										pojazdy.usunPojazd(argument);
										odpowiedz = "Wykreœli³em pojazd z naszej bazy.";										
									} else {
										throw new Exception("O który samochód chodzi? Móg³by Szef podaæ jego identyfikator?");
									}
									break;
								case "edycja":
									if(argumenty.size() >= 2) {
										pojazdy.zmienNazwePojazdu(argumenty.get(0), argumenty.get(1));
										odpowiedz = "Nazwa zmieniona, szefie!";
									}
									else {
										throw new Exception("Nazwê którego samochodu mam zmieniæ? Proszê podaæ id i now¹ nazwê");
									}
									break;
							}
							break;
						case "sterowanie":
							switch(akcja.getNazwa()) {
								case "gora":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w górê";
									} else {
										throw new Exception("Który pojazd przesun¹æ?");
									}
									break;
								case "dol":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w dó³";
									} else {
										throw new Exception("Który pojazd przesun¹æ?");
									}
									break;
								case "prawo":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w prawo";
									} else {
										throw new Exception("Który pojazd przesun¹æ?");
									}
									break;
								case "lewo":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w lewo";
									} else {
										throw new Exception("Który pojazd przesun¹æ?");
									}
									break;
								default:
									throw new Exception("Dok¹d mam jechaæ?");
							}
							break;
					}
					break;
				default:
					throw new Exception("Nie rozumiem komendy. " + typ.getNazwa() + ": " + akcja.getNazwa() + " " + obiekt.getNazwa());
			}
		} else {
			throw new Exception("Nie rozumiem komendy");
		}
		
		return odpowiedz;
	}
}
