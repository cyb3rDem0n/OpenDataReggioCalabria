package com.rekrux.OpenDataReggioCalabria;

/**
 * Created by rekrux on 04/08/2016.
 */
public class Linee {
    private String idLinea;
    private int idVettore;
    private String lina_nomebreve;
    private String linea_nome_esteso;

    public Linee() {
    }

    public Linee(String idLinea, int idVettore, String lina_nomebreve, String linea_nome_esteso) {
        this.idLinea = idLinea;
        this.idVettore = idVettore;
        this.lina_nomebreve = lina_nomebreve;
        this.linea_nome_esteso = linea_nome_esteso;
    }

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(String idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdVettore() {
        return idVettore;
    }

    public void setIdVettore(int idVettore) {
        this.idVettore = idVettore;
    }

    public String getLina_nomebreve() {
        return lina_nomebreve;
    }

    public void setLina_nomebreve(String lina_nomebreve) {
        this.lina_nomebreve = lina_nomebreve;
    }

    public String getLinea_nome_esteso() {
        return linea_nome_esteso;
    }

    public void setLinea_nome_esteso(String linea_nome_esteso) {
        this.linea_nome_esteso = linea_nome_esteso;
    }
}
