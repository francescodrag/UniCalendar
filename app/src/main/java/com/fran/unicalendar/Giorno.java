package com.fran.unicalendar;

import java.util.List;

@SuppressWarnings("all")
public class Giorno {

    private int giorno;
    private List<Evento> eventi;

    public Giorno() {
    }

    public Giorno(int giorno, List<Evento> eventi) {
        this.giorno = giorno;
        this.eventi = eventi;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public List<Evento> getEventi() {
        return eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

}
