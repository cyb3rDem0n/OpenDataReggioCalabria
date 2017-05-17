package com.rekrux.OpenDataReggioCalabria;

/**
 * Created by rekrux on 04/08/2016.
 */
public class Corse {
    private String idCorsa;
    private String idLinea;
    private int idServizio;
    private String capolinea;
    private String corsa_nomebreve;

    public Corse() {
    }

    public Corse(String idCorsa, String idLinea, int idServizio, String capolinea, String corsa_nomebreve) {
        this.idCorsa = idCorsa;
        this.idLinea = idLinea;
        this.idServizio = idServizio;
        this.capolinea = capolinea;
        this.corsa_nomebreve = corsa_nomebreve;
    }
    public Corse(String idCorsa, String idLinea) {
        this.idCorsa = idCorsa;
        this.idLinea = idLinea;
    }

    public String getIdCorsa() {
        return idCorsa;
    }

    public void setIdCorsa(String idCorsa) {
        this.idCorsa = idCorsa;
    }

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(String idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdServizio() {
        return idServizio;
    }

    public void setIdServizio(int idServizio) {
        this.idServizio = idServizio;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public String getCorsa_nomebreve() {
        return corsa_nomebreve;
    }

    public void setCorsa_nomebreve(String corsa_nomebreve) {
        this.corsa_nomebreve = corsa_nomebreve;
    }
}
