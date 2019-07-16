package com.fran.unicalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String university;
    private String department;
    private String universityTipe;
    private String suddivisione;
    private String anno;
    private String semestre;

    public User() {
    }

    public User(String id, String nome, String cognome, String email, String password, String university, String department, String universityTipe, String suddivisione, String anno, String semestre) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.university = university;
        this.department = department;
        this.universityTipe = universityTipe;
        this.suddivisione = suddivisione;
        this.anno = anno;
        this.semestre = semestre;
    }

    public User(String id, String nome, String cognome, String email, String password, String university, String department, String universityTipe, String anno, String semestre) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.university = university;
        this.department = department;
        this.universityTipe = universityTipe;
        this.anno = anno;
        this.semestre = semestre;
    }

    private User(Parcel in) {
        id = in.readString();
        nome = in.readString();
        cognome = in.readString();
        email = in.readString();
        password = in.readString();
        university = in.readString();
        department = in.readString();
        universityTipe = in.readString();
        suddivisione = in.readString();
        anno = in.readString();
        semestre = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUniversityTipe() {
        return universityTipe;
    }

    public void setUniversityTipe(String universityTipe) {
        this.universityTipe = universityTipe;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(university);
        dest.writeString(department);
        dest.writeString(universityTipe);
        dest.writeString(suddivisione);
        dest.writeString(anno);
        dest.writeString(semestre);
    }

    @Override
    public String toString() {
        return "Nome = " + nome + " Cognome = " + cognome + " Universita' = " + university
                + " Dipartimento = " + department + " Tipo = " + universityTipe +
                " Anno = " + anno + " Semestre = " + semestre;
    }
}
