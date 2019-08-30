package com.fran.unicalendar;

import java.util.List;

public class TimeTable {

    private List<Giorno> giorni;

    public TimeTable(List<Giorno> giorni) {
        this.giorni = giorni;
    }

    public TimeTable() {
    }

    public List<Giorno> getGiorni() {
        return giorni;
    }

    public void setGiorni(List<Giorno> giorni) {
        this.giorni = giorni;
    }
}
