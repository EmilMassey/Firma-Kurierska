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
	
	public String interpretujKomende(String komenda) throws Exception {
		ArrayList <String> argumenty = analiza.okreslArgumenty(komenda);
		String argument = analiza.okreslArgument(komenda);
		String odpowiedz = "";
		
		komenda = this.zamienPolskie(komenda.toLowerCase());						// �eby upro�ci�, nie mog�o by� wcze�niej, �eby argument�w nie zmienia�
		
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
										odpowiedz = "Doda�em przesy�k�!";
									} else {
										throw new Exception("Musz� zna� takie informacje o przesy�ce: typ, waga");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										przesylki.usunPrzesylke(argument);
										odpowiedz = "Wykre�li�em paczk�.";										
									} else {
										throw new Exception("O kt�r� paczk� chodzi? M�g�by Szef poda� jej identyfikator?");
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
										// wsp�rz�dne doda�
										
										pojazdy.nowyPojazd(dane);
										odpowiedz = "Doda�em pojazd!";
									} else {
										throw new Exception("Musz� zna� takie informacje o poje�dzie: nazwa, typ, pojemno��, typ �adunku, pr�dko�� maksymalna, spalanie, poziom paliwa, maksymalna pojemno�� baku, adres grafiki");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										pojazdy.usunPojazd(argument);
										odpowiedz = "Wykre�li�em pojazd z naszej bazy.";										
									} else {
										throw new Exception("O kt�ry samoch�d chodzi? M�g�by Szef poda� jego identyfikator?");
									}
									break;
								case "edycja":
									if(argumenty.size() >= 2) {
										pojazdy.zmienNazwePojazdu(argumenty.get(0), argumenty.get(1));
										odpowiedz = "Nazwa zmieniona, szefie!";
									}
									else {
										throw new Exception("Nazw� kt�rego samochodu mam zmieni�? Prosz� poda� id i now� nazw�");
									}
									break;
							}
							break;
						case "sterowanie":
							switch(akcja.getNazwa()) {
								case "gora":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w g�r�";
									} else {
										throw new Exception("Kt�ry pojazd przesun��?");
									}
									break;
								case "dol":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w d�";
									} else {
										throw new Exception("Kt�ry pojazd przesun��?");
									}
									break;
								case "prawo":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w prawo";
									} else {
										throw new Exception("Kt�ry pojazd przesun��?");
									}
									break;
								case "lewo":
									if(argument != "") {
										odpowiedz = "Przesuwam pojazd nr " + argument + " w lewo";
									} else {
										throw new Exception("Kt�ry pojazd przesun��?");
									}
									break;
								default:
									throw new Exception("Dok�d mam jecha�?");
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
