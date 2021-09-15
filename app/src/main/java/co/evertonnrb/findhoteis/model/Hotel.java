package co.evertonnrb.findhoteis.model;/*
    @author everton.nrb@gmail.com
*/

import java.io.Serializable;

public class Hotel implements Serializable {

    private long id;
    private String nome;
    private String endereco;
    private float estrelas;

    public Hotel() {
    }

    public Hotel(long id, String nome, String endereco, float estrelas) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    public Hotel(String nome, String endereco, float estrelas) {
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        return "Hotel: "+ nome ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(float estrelas) {
        this.estrelas = estrelas;
    }
}
