package de.unidue.inf.is.domain;

//Alle Kurs Daten

public class Kurs {
	
	private int kid, freiePlaetze, ersteller;
	private String beschreibungstext, schluessel, name;	
	
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public int getFreiePlaetze() {
		return freiePlaetze;
	}
	public void setFreiePlaetze(int freiePlaetze) {
		this.freiePlaetze = freiePlaetze;
	}
	public int getErsteller() {
		return ersteller;
	}
	public void setErsteller(int ersteller) {
		this.ersteller = ersteller;
	}
	public String getBeschreibungstext() {
		return beschreibungstext;
	}
	public void setBeschreibungstext(String beschreibungstext) {
		this.beschreibungstext = beschreibungstext;
	}
	public String getSchluessel() {
		return schluessel;
	}
	public void setSchluessel(String schluessel) {
		this.schluessel = schluessel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
