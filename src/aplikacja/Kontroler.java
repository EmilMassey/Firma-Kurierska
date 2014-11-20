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
	
	public void interpretujKomende(String komenda) throws Exception {
		ArrayList <String> argumenty = analiza.okreslArgumenty(komenda);
		String argument = analiza.okreslArgument(komenda);
		
		komenda = this.zamienPolskie(komenda.toLowerCase());						// �eby upro�ci�, nie mog�o by� wcze�niej, �eby argument�w nie zmienia�
		
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
										System.out.println("Doda�em pojazd!");
									} else {
										throw new Exception("Musz� zna� takie informacje o poje�dzie: nazwa, typ, pojemno��, typ �adunku, pr�dko�� maksymalna, spalanie, poziom paliwa, maksymalna pojemno�� baku, adres grafiki");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										pojazdy.usunPojazd(argument);
										System.out.println("Wykre�li�em pojazd z naszej bazy.");										
									} else {
										throw new Exception("O kt�ry samoch�d chodzi? M�g�by Szef poda� jego identyfikator?");
									}
									break;
								case "edycja":
									if(argumenty.size() >= 2) {
										pojazdy.zmienNazwePojazdu(argumenty.get(0), argumenty.get(1));
										System.out.println("Nazwa zmieniona, szefie!");
									}
									else {
										throw new Exception("Nazw� kt�rego samochodu mam zmieni�? Prosz� poda� id i now� nazw�");
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
