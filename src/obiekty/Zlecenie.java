package obiekty;

import java.util.Calendar;

public class Zlecenie {
	int id;
	int pojazd;
	int przesylka;
	int startX;
	int startY;
	int celX;
	int celY;
	Calendar dataNadania;
	Calendar dataDostarczenia;
	Calendar deadline;
	boolean wykonane;
	boolean wTrakcie;
	
	public Zlecenie(int id, int pojazd, int przesylka, int startX, int startY,
			int celX, int celY, Calendar dataNadania, Calendar dataDostarczenia,
			Calendar deadline, boolean wykonane, boolean wTrakcie) {
		this.id = id;
		this.pojazd = pojazd;
		this.przesylka = przesylka;
		this.startX = startX;
		this.startY = startY;
		this.celX = celX;
		this.celY = celY;
		this.dataNadania = dataNadania;
		this.dataDostarczenia = dataDostarczenia;
		this.deadline = deadline;
		this.wykonane = wykonane;
		this.wTrakcie = wTrakcie;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPojazd() {
		return pojazd;
	}
	public void setPojazd(int pojazd) {
		this.pojazd = pojazd;
	}
	public int getPrzesylka() {
		return przesylka;
	}
	public void setPrzesylka(int przesylka) {
		this.przesylka = przesylka;
	}
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getCelX() {
		return celX;
	}
	public void setCelX(int celX) {
		this.celX = celX;
	}
	public int getCelY() {
		return celY;
	}
	public void setCelY(int celY) {
		this.celY = celY;
	}
	public Calendar getDataNadania() {
		return dataNadania;
	}
	public void setDataNadania(Calendar dataNadania) {
		this.dataNadania = dataNadania;
	}
	public Calendar getDataDostarczenia() {
		return dataDostarczenia;
	}
	public void setDataDostarczenia(Calendar dataDostarczenia) {
		this.dataDostarczenia = dataDostarczenia;
	}
	public Calendar getDeadline() {
		return deadline;
	}
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}
	public boolean isWykonane() {
		return wykonane;
	}
	public void setWykonane(boolean wykonane) {
		this.wykonane = wykonane;
	}
	public boolean isWTrakcie() {
		return wTrakcie;
	}
	public void setWTrakcie(boolean wTrakcie) {
		this.wTrakcie = wTrakcie;
	}	
}
