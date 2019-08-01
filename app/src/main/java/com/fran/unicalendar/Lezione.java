package com.fran.unicalendar;

public class Lezione {

    public String aula;
    public String oraDiInizio;
    public String oraDiFine;
    public String tipologia;
    public String giornoDellaLezione;

    public Lezione() {
    }

    public Lezione(String aula, String oraDiInizio, String oraDiFine, String tipologia, String giornoDellaLezione) {
        this.aula = aula;
        this.oraDiInizio = oraDiInizio;
        this.oraDiFine = oraDiFine;
        this.tipologia = tipologia;
        this.giornoDellaLezione = giornoDellaLezione;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getOraDiInizio() {
        return oraDiInizio;
    }

    public void setOraDiInizio(String oraDiInizio) {
        this.oraDiInizio = oraDiInizio;
    }

    public String getOraDiFine() {
        return oraDiFine;
    }

    public void setOraDiFine(String oraDiFine) {
        this.oraDiFine = oraDiFine;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getGiornoDellaLezione() {
        return giornoDellaLezione;
    }

    public void setGiornoDellaLezione(String giornoDellaLezione) {
        this.giornoDellaLezione = giornoDellaLezione;
    }
}
