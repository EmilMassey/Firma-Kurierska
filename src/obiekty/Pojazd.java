package obiekty;

import java.util.Random;

public class Pojazd {
	private int id;
	private String nazwa;					// Nazwa pojazdu
	private int typ;						// Typ (0, 1, 2, 3, ...)
	private String rejestracja;				// Numer rejestracyjny
	private int pojemnosc;					// �adowno�� w kilogramach
	private int typ_ladunku;				// jaki typ przesy�ek mo�e przewozi�
	private int predkosc_max;				// Najwi�ksza pr�dko��
	private float spalanie;					// Spalanie na 100 km
	private float poziom_paliwa;			// Poziom paliwa w litrach
	private float max_pojemnosc_baku;		// Pojemno�c baku w litrach
	private String grafika;               	// Grafika reprezentuj�ca samoch�d
	
	public Pojazd(int id, String nazwa, int typ, String rejestracja,
			int pojemnosc, int typ_ladunku, int predkosc_max,
			float spalanie, float poziom_paliwa, float max_pojemnosc_baku,
			String grafika) {
		this.id = id;
		this.nazwa = nazwa;
		this.typ = typ;
		this.rejestracja = rejestracja;
		this.pojemnosc = pojemnosc;
		this.typ_ladunku = typ_ladunku;
		this.predkosc_max = predkosc_max;
		this.spalanie = spalanie;
		this.poziom_paliwa = poziom_paliwa;
		this.max_pojemnosc_baku = max_pojemnosc_baku;
		this.grafika = grafika;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	public int getTyp() {
		return typ;
	}


	public void setTyp(int typ) {
		this.typ = typ;
	}


	public String getRejestracja() {
		return rejestracja;
	}


	public void setRejestracja(String rejestracja) {
		this.rejestracja = rejestracja;
	}
	
	public String generujRejestracje(String rej) {
		String litery = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		
		rej += " ";
		
		Random generator = new Random();
		
		for(int i=0; i<4; i++)
			rej += Integer.toString(generator.nextInt(9));
		rej += litery.charAt(generator.nextInt(litery.length()-1));
		
		return rej;
	}


	public int getPojemnosc() {
		return pojemnosc;
	}


	public void setPojemnosc(int pojemnosc) {
		this.pojemnosc = pojemnosc;
	}


	public int getTyp_ladunku() {
		return typ_ladunku;
	}


	public void setTyp_ladunku(int typ_ladunku) {
		this.typ_ladunku = typ_ladunku;
	}


	public int getPredkosc_max() {
		return predkosc_max;
	}


	public void setPredkosc_max(int predkosc_max) {
		this.predkosc_max = predkosc_max;
	}


	public float getSpalanie() {
		return spalanie;
	}


	public void setSpalanie(float spalanie) {
		this.spalanie = spalanie;
	}


	public float getPoziom_paliwa() {
		return poziom_paliwa;
	}


	public void setPoziom_paliwa(float poziom_paliwa) {
		this.poziom_paliwa = poziom_paliwa;
	}


	public float getMax_pojemnosc_baku() {
		return max_pojemnosc_baku;
	}


	public void setMax_pojemnosc_baku(float max_pojemnosc_baku) {
		this.max_pojemnosc_baku = max_pojemnosc_baku;
	}


	public String getGrafika() {
		return grafika;
	}


	public void setGrafika(String grafika) {
		this.grafika = grafika;
	}
	
	
}
