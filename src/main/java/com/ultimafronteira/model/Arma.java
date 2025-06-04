package com.ultimafronteira.model;

public class Arma extends Item {
    private String tipoArma;
    private int dano;
    private int alcance;

    public Arma(String nome, double peso, int durabilidade, String tipoArma, int dano, int alcance) {
        super(nome, peso, durabilidade);
        this.tipoArma = tipoArma;
        this.dano = dano;
        this.alcance = alcance;
    }

    public String getTipoArma() {
        return tipoArma;
    }

    public int getDano() {
        return dano;
    }

    public int getAlcance() {
        return alcance;
    }

    @Override
    public void usar(Personagem jogador) {
        System.out.println(jogador.getNome() + " equipou a arma: " + getNome() + ". Para atacar, use o método atacar().");
    }

    public void atacar(/* Alvo inimigo - será adicionado depois */) {
        if (getDurabilidade() > 0 || getDurabilidade() == -1) {
            System.out.println("Atacando com " + getNome() + ", causando " + dano + " de dano.");
            if (getDurabilidade() != -1) {
                setDurabilidade(getDurabilidade() -1);
                if (getDurabilidade() == 0) {
                    System.out.println(getNome() + " quebrou!");
                }
            }
        } else {
            System.out.println(getNome() + " está quebrada e não pode ser usada para atacar.");
        }
    }
}