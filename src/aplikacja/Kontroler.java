package aplikacja;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import komunikacja.*;
import dane.Nieruchomosci;
import dane.Pojazdy;
import dane.Przesylki;
import dane.Zlecenia;

public class Kontroler {
	private Miasto miasto;
	private Analiza analiza;
	private Pojazdy pojazdy;
	private Przesylki przesylki;
	private Zlecenia zlecenia;
	private Nieruchomosci nieruchomosci;
	
	public Kontroler(Miasto miasto) {
		try {
			this.miasto = miasto;
			this.analiza = new Analiza("dane/slownik.xml");
			this.pojazdy = new Pojazdy();
			this.przesylki = new Przesylki();
			this.zlecenia = new Zlecenia();
			this.nieruchomosci = new Nieruchomosci();
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
				case "zlecenie":
					switch(typ.getNazwa()) {
						case "ustawienie":
							switch(akcja.getNazwa()) {
								case "wygeneruj":
									if(argument != "") {
										zlecenia.wygenerujZlecenia(Integer.parseInt(argument));
										odpowiedz = "Wygenerowa³em zlecenia!";
									} else {
										throw new Exception("Ile zleceñ wygenerowaæ?");
									}
									break;
							}
							break;
						case "raport":
							switch(akcja.getNazwa()) {
								case "pokaz":
									if(!zlecenia.listaZlecen().isEmpty()) {
										odpowiedz = "Pozosta³o nam do zrobienia jeszcze:\n";
										for(String zlecenie : zlecenia.listaZlecen())
											odpowiedz += zlecenie + "\n";
									} else {
										odpowiedz = "Nie mamy ju¿ wiêcej zleceñ :)";
									}
								break;
							}
							break;
						case "zarzadzanie":
							switch(akcja.getNazwa()) {
								case "dodanie":
									Map <String, String> dane = new HashMap<String, String>();
									if(!argumenty.isEmpty() && argumenty.size() >= 2) {
										dane.put("pojazd", argumenty.get(0));
										dane.put("przesylka", argumenty.get(1));	
										dane.put("startX", argumenty.get(2));		
										dane.put("startY", argumenty.get(3));	
										dane.put("celX", argumenty.get(4));
										dane.put("celY", argumenty.get(5));
										dane.put("dataNadania", argumenty.get(6));	
										dane.put("dataOdbioru", "2999-0-1");
										dane.put("deadline", argumenty.get(7));	
										dane.put("wykonane", "false");
										zlecenia.noweZlecenie(dane);
										odpowiedz = "Doda³em zlecenie!";
									} else {
										throw new Exception("Muszê znaæ takie informacje o zleceniu: id pojazdu, id przesy³ki, wspó³rzêdna X Ÿród³a, wspó³rzêdna Y Ÿród³a, wspo³rzêdna X celu, wspó³rzêdna Y celu, data nadania i termin dostarczenia");
									}
									break;
								case "usuwanie":
									if(argument != "") {
										zlecenia.usunZlecenie(argument);
										odpowiedz = "Wykreœli³em zlecenie.";										
									} else {
										throw new Exception("Które zlecenie usun¹æ? Jaki identyfikator?");
									}
									break;
							}
							break;
					}
					break;
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
							break;
						case "polecenie":
							switch(akcja.getNazwa()) {
								case "zabieranie":
									Point cel = zlecenia.wezPrzesylke(miasto.getSterowany(), pojazdy.podajWspolrzedne(miasto.getSterowany()));
									odpowiedz = "Zabra³em przesy³kê. Muszê j¹ zawieŸæ do " + nieruchomosci.podajAdres(cel);
									break;
								case "dostarczanie":
									zlecenia.dostarcz(miasto.getSterowany(), pojazdy.podajWspolrzedne(miasto.getSterowany()));
									odpowiedz = "Dostarczy³em paczkê, Szefie.";
									break;
							}
							break;
					}
					break;
				case "nieruchomosc":
					switch(typ.getNazwa()) {
						case "raport":
							switch(akcja.getNazwa()) {
								case "wspolrzedne":
									if(argument != "") {
										Point wspolrzedne = nieruchomosci.podajWspolrzedne(argument);
										odpowiedz = "Adres " + argument + "le¿y na wspó³rzêdnych " + (int)wspolrzedne.getX() + "x" + (int)wspolrzedne.getY();
									} else {
										throw new Exception("Jaki adres?");
									}
									break;
								case "adres":
									if(!argumenty.isEmpty() && argumenty.size() >= 2)
										odpowiedz = nieruchomosci.podajAdres(new Point(Integer.parseInt(argumenty.get(0)), Integer.parseInt(argumenty.get(1))));
									else
										throw new Exception("Jakie wspó³rzêdne?");
									break;
							}
							break;
					}
					break;
				case "trasa":
					switch(typ.getNazwa()) {
						case "raport":
							switch(akcja.getNazwa()) {
								case "pokaz":
									if(argument != "") {
										Point wspolrzedne = nieruchomosci.podajWspolrzedne(argument);
										this.miasto.renderpanel.trasa = this.nieruchomosci.nawigacja.pokazDroge(this.nieruchomosci.mapa, pojazdy.podajWspolrzedne(this.miasto.getSterowany()), wspolrzedne).getPunkty();
										odpowiedz = "Proszê bardzo";
									} else {
										throw new Exception("Dok¹d mam nawigowaæ?");
									}										
									break;
							}
							break;
						case "zarzadzanie":
							switch(akcja.getNazwa()) {
								case "usuwanie":
									this.miasto.renderpanel.trasa.clear();
									odpowiedz = "Ju¿ nie pokazujê trasy";
									break;
							}
							break;
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
									if(argument != "") {
										Point wspolrzedne = pojazdy.podajWspolrzedne(Integer.parseInt(argument));
										odpowiedz = "Samochód jest obecnie na wspó³rzêdnych " + (int)wspolrzedne.getX() + "x" + (int)wspolrzedne.getY();
									} else {
										throw new Exception("O który pojazd chodzi?");
									}
									break;
								case "adres":
									if(argument != "") {
										Point wspolrzedne = pojazdy.podajWspolrzedne(Integer.parseInt(argument));
										String adres = nieruchomosci.podajAdres(wspolrzedne);
										odpowiedz = "Samochód jest obecnie na " + adres;
									} else {
										throw new Exception("O który pojazd chodzi?");
									}
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
								case "kierowanie":
									if(argument != "") {
										odpowiedz = "Zaczynam sterowanie pojazdem numer " + argument;
										miasto.zmienSterowany(Integer.parseInt(argument));
									} else {
										throw new Exception("Którego pojazdu stery mam przej¹æ?");
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
