package de.unidue.inf.is.domain;

public class Einreichen {
    private int bnummer;
    private int kid;
    private int anummer;
    private int aid;
    private String abgabetext = "";

    public Einreichen() {

    }

    public int getBnummer() {
        return bnummer;
    }

    public void setBnummer(int bnummer) {
        this.bnummer = bnummer;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public int getAnummer() {
        return anummer;
    }

    public void setAnummer(int anummer) {
        this.anummer = anummer;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAbgabetext() {
        return abgabetext;
    }

    public void setAbgabetext(String abgabetext) {
        this.abgabetext = abgabetext;
    }
}
