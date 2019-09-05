package com.fran.unicalendar;

import java.util.List;

@SuppressWarnings("all")
public class Calendario {

    private String university;
    private String department;
    private String universityType;
    private String anno;
    private String tipoSuddivisione;
    private String suddivisione;
    private String semestre;
    private List<Corso> corsi;

    public Calendario() {
    }

    public Calendario(String university, String department, String universityType, String anno, String tipoSuddivisione, String suddivisione, String semestre, List<Corso> corsi) {
        this.university = university;
        this.department = department;
        this.universityType = universityType;
        this.anno = anno;
        this.tipoSuddivisione = tipoSuddivisione;
        this.suddivisione = suddivisione;
        this.semestre = semestre;
        this.corsi = corsi;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getTipoSuddivisione() {
        return tipoSuddivisione;
    }

    public void setTipoSuddivisione(String tipoSuddivisione) {
        this.tipoSuddivisione = tipoSuddivisione;
    }

    public String getSuddivisione() {
        return suddivisione;
    }

    public void setSuddivisione(String suddivisione) {
        this.suddivisione = suddivisione;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<Corso> getCorsi() {
        return corsi;
    }

    public void setCorsi(List<Corso> corsi) {
        this.corsi = corsi;
    }

    public void addCorso(Corso corso) {
        corsi.add(corso);
    }
}
