package mapa;

import java.awt.Point;
import java.util.ArrayList;

public class Mapa {
	private int szerokosc;
	private int wysokosc;
	private ArrayList <Point> punkty;
	
	public Mapa(int szerokosc, int wysokosc, ArrayList <Point> punkty) {
		this.szerokosc = szerokosc;
		this.wysokosc = wysokosc;
		this.punkty = punkty;
	}
	
	public int getSzerokosc() {
		return szerokosc;
	}
	public void setSzerokosc(int szerokosc) {
		this.szerokosc = szerokosc;
	}
	public int getWysokosc() {
		return wysokosc;
	}
	public void setWysokosc(int wysokosc) {
		this.wysokosc = wysokosc;
	}
	public ArrayList<Point> getPunkty() {
		return punkty;
	}
	public void setPunkty(ArrayList<Point> punkty) {
		this.punkty = punkty;
	}
}
