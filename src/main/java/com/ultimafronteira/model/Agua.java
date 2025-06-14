package com.ultimafronteira.model;

public class Agua extends Item {
    private int valorHidratacao;
    private String pureza;
    private double volume;

    public Agua(String nome, double peso, int durabilidade, int valorHidratacao, String pureza, double volume) {
        super(nome, peso, durabilidade);
        this.valorHidratacao = valorHidratacao;
        this.pureza = pureza;
        this.volume = volume;
    }

    public int getValorHidratacao() {
        return valorHidratacao;
    }

    public String getPureza() {
        return pureza;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public void usar(Personagem jogador) {
        System.out.println(jogador.getNome() + " bebeu " + getNome() + ".");
        if ("potável".equalsIgnoreCase(pureza)) {
            jogador.setSede(jogador.getSede() + valorHidratacao);
        } else {
            System.out.println("A água estava contaminada!");
        }
    }
}