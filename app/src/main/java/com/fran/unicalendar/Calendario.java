package com.fran.unicalendar;

import java.util.ArrayList;

public class Calendario {

    private String id;
    private String university;
    private String department;
    private String universityType;
    private String tipoSuddivisione;
    private String suddivisione;
    private String anno;
    private String semestre;
    private ArrayList<Lezione> lezioni;

    public Calendario() {
    }

    public Calendario(String id, String university, String department, String universityType, String tipoSuddivisione, String suddivisione, String anno, String semestre, ArrayList<Lezione> lezioni) {
        this.id = id;
        this.university = university;
        this.department = department;
        this.universityType = universityType;
        this.tipoSuddivisione = tipoSuddivisione;
        this.suddivisione = suddivisione;
        this.anno = anno;
        this.semestre = semestre;
        this.lezioni = lezioni;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public ArrayList<Lezione> getLezioni() {
        return lezioni;
    }

    public void setLezione(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public void addLezione(Lezione lezione) {
        lezioni.add(lezione);
    }

}
