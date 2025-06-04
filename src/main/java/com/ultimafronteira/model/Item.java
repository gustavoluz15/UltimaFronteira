package com.ultimafronteira.model;

public abstract class Item {
    protected String nome;
    protected double peso;
    protected int durabilidade; // -1 para itens não duráveis ou com durabilidade infinita

    public Item(String nome, double peso, int durabilidade) {
        this.nome = nome;
        this.peso = peso;
        this.durabilidade = durabilidade;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

    public void setDurabilidade(int durabilidade) {
        this.durabilidade = Math.max(0, durabilidade);
    }

    // Método abstrato a ser implementado pelas subclasses
    // O parâmetro 'jogador' permite que o item afete o personagem
    public abstract void usar(Personagem jogador);

    @Override
    public String toString() {
        return nome + " (Peso: " + peso + (durabilidade != -1 ? ", Durabilidade: " + durabilidade : "") + ")";
    }
}