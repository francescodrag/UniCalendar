package com.fran.unicalendar;

public class Lezione {

    public String aula;
    public String professore;
    public String inizioLezione;
    public String fineLezione;
    public String tipo;
    //public String durata; //settimane


    public Lezione() {
    }

    public Lezione(String aula, String professore, String inizioLezione, String fineLezione, String tipo) {
        this.aula = aula;
        this.professore = professore;
        this.inizioLezione = inizioLezione;
        this.fineLezione = fineLezione;
        this.tipo = tipo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getProfessore() {
        return professore;
    }

    public void setProfessore(String professore) {
        this.professore = professore;
    }

    public String getInizioLezione() {
        return inizioLezione;
    }

    public void setInizioLezione(String inizioLezione) {
        this.inizioLezione = inizioLezione;
    }

    public String getFineLezione() {
        return fineLezione;
    }

    public void setFineLezione(String fineLezione) {
        this.fineLezione = fineLezione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
