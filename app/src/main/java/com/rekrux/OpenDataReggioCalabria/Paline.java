package com.rekrux.OpenDataReggioCalabria;

/**
 * Created by rekrux on 04/08/2016.
 */
public class Paline {
    private int idPalina;
    private int idZona;
    private String nome_palina;
    private double latitudine;
    private double longitudine;

    public Paline() {};
    public Paline(double latitudine, double longitudine){
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public Paline(int idPalina, int idZona, String nome_palina, double latitudine, double longitudine) {
        this.idPalina = idPalina;
        this.idZona = idZona;
        this.nome_palina = nome_palina;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public int getIdPalina() {
        return idPalina;
    }

    public void setIdPalina(int idPalina) {
        this.idPalina = idPalina;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNome_palina() {
        return nome_palina;
    }

    public void setNome_palina(String nome_palina) {
        this.nome_palina = nome_palina;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
}
