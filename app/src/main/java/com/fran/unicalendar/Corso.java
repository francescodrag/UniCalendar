package com.fran.unicalendar;

import java.util.List;

public class Corso {

    private String materia;
    private String docente;
    private List<Lezione> lezioni;

    public Corso() {
    }

    public Corso(String materia, String docente, List<Lezione> lezioni) {
        this.materia = materia;
        this.docente = docente;
        this.lezioni = lezioni;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public List<Lezione> getLezioni() {
        return lezioni;
    }

    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public void addLezione(Lezione lezione) {
        lezioni.add(lezione);
    }
}
