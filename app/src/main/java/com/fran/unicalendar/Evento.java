package com.fran.unicalendar;

public class Evento {

    private String aula;
    private String docente;
    private String inizioLezione;
    private String fineLezione;
    private String tipologiaLezione;
    private String materia;

    public Evento() {
    }

    public Evento(String aula, String docente, String inizioLezione, String fineLezione, String tipologiaLezione, String materia) {
        this.aula = aula;
        this.docente = docente;
        this.inizioLezione = inizioLezione;
        this.fineLezione = fineLezione;
        this.tipologiaLezione = tipologiaLezione;
        this.materia = materia;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
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

    public String getTipologiaLezione() {
        return tipologiaLezione;
    }

    public void setTipologiaLezione(String tipologiaLezione) {
        this.tipologiaLezione = tipologiaLezione;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
