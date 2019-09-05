package com.fran.unicalendar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
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
    private String universityType;
    private String tipoSuddivisione;
    private String suddivisione;
    private String anno;
    private String semestre;
    private boolean calendario;

    public User() {
    }

    private User(Parcel in) {
        id = in.readString();
        nome = in.readString();
        cognome = in.readString();
        email = in.readString();
        password = in.readString();
        university = in.readString();
        department = in.readString();
        universityType = in.readString();
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
        return universityType;
    }

    public void setUniversityTipe(String universityType) {
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

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public boolean isCalendario() {
        return calendario;
    }

    public void setCalendario(boolean calendario) {
        this.calendario = calendario;
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
        dest.writeString(universityType);
        dest.writeString(suddivisione);
        dest.writeString(anno);
        dest.writeString(semestre);

    }

    public Map<String, Object> getHashMapUser() {

        Map<String, Object> user = new HashMap<>();
        user.put("Id", id);
        user.put("Name", nome);
        user.put("Surname", cognome);
        user.put("Email", email);
        user.put("Password", password);
        user.put("UniversityType", universityType);
        user.put("Year", anno);
        user.put("University", university);
        user.put("Department", department);
        user.put("Semester", semestre);
        user.put("SubdivisionType", tipoSuddivisione);
        user.put("Subdivision", suddivisione);
        user.put("HasCalendario", calendario);

        return user;
    }

    @Override
    public String toString() {
        return "Nome = " + nome + "\nCognome = " + cognome + "\nUniversita' = " + university
                + "\nDipartimento = " + department + "\nTipo = " + universityType +
                "\nAnno = " + anno + "\nSemestre = " + semestre;
    }
}
