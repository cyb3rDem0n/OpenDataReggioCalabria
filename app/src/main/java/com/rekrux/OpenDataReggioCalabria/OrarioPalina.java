package com.rekrux.OpenDataReggioCalabria;

//Rappresentazione dell oggetto palina_orari json

public class OrarioPalina {
    public int id;
    public String idCorsa;
    public String orario_arrivo;
    public String orario_partenza;
    public String  idPalina;

    //costrutttore non param.
    public OrarioPalina() {
    }

    //costruttore parametrizzato
    public OrarioPalina(int id, String idCorsa, String orario_arrivo, String orario_partenza, String idPalina) {
        this.id = id;
        this.idCorsa = idCorsa;
        this.orario_arrivo = orario_arrivo;
        this.orario_partenza = orario_partenza;
        this.idPalina = idPalina;
    }

    public OrarioPalina(String idCorsa, String idPalina) {
        this.idCorsa = idCorsa;

        this.idPalina = idPalina;
    }
}