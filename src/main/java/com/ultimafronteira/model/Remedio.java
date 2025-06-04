package com.ultimafronteira.model;

public class Remedio extends Item {
    private String tipo;
    private String efeito;

    public Remedio(String nome, double peso, String tipo, String efeito) {
        super(nome, peso, 1);
        this.tipo = tipo;
        this.efeito = efeito;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEfeito() {
        return efeito;
    }

    @Override
    public void usar(Personagem jogador) {
        System.out.println(jogador.getNome() + " usou o remédio: " + getNome() + " (" + tipo + "). Efeito: " + efeito);
        // A lógica de aplicar o efeito (ex: curar vida, sanidade) será adicionada aqui
        // Exemplo:
        if (this.efeito.toLowerCase().contains("cura")) {
        }
    }
}