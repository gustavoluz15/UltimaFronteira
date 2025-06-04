package com.ultimafronteira.model;

public class Alimento extends Item {
    private int valorNutricional;
    private String tipo;

    public Alimento(String nome, double peso, int durabilidade, int valorNutricional, String tipo) {
        super(nome, peso, durabilidade);
        this.valorNutricional = valorNutricional;
        this.tipo = tipo;
    }

    public int getValorNutricional() {
        return valorNutricional;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public void usar(Personagem jogador) {
        // Lógica para consumir o alimento
        System.out.println(jogador.getNome() + " consumiu " + getNome() + ".");
        jogador.setFome(jogador.getFome() + valorNutricional);
    }
}