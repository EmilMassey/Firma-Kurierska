package komunikacja;

public class Typ {
	private String nazwa;
	private Boolean istnieje = true;
	
	public Typ(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public Typ(Boolean istnieje) {
		this.istnieje = istnieje;
	}
	
	public String getNazwa() { return this.nazwa; }
	public Boolean czyIstnieje() { return istnieje; }
}
