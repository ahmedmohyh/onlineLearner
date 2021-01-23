package de.unidue.inf.is.domain;

public class Aufgabe {
    private int kid ;
    private int anummer;
    String name;
    String beschreibung ;
 public Aufgabe () {
 }
    public void setKid(int kid) {
        this.kid = kid;
    }

    public void setAnummer(int anummer) {
        this.anummer = anummer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getKid() {
        return kid;
    }

    public int getAnummer() {
        return anummer;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
