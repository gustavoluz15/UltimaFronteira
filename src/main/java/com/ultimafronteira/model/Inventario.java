package com.ultimafronteira.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventario {
    private List<Item> itens;
    private double pesoMaximo;
    private double pesoAtual;

    public Inventario(double pesoMaximo) {
        this.itens = new ArrayList<>();
        this.pesoMaximo = pesoMaximo;
        this.pesoAtual = 0;
    }

    public List<Item> getItens() {
        return new ArrayList<>(itens);
    }

    public double getPesoAtual() {
        return pesoAtual;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public double getEspacoDisponivel() {
        return pesoMaximo - pesoAtual;
    }

    public boolean adicionarItem(Item item) {
        if (item == null) {
            return false;
        }
        if (pesoAtual + item.getPeso() <= pesoMaximo) {
            itens.add(item);
            pesoAtual += item.getPeso();
            return true;
        } else {
            return false;
        }
    }

    public boolean removerItem(String nomeItem) {
        Optional<Item> itemParaRemover = itens.stream()
                .filter(item -> item.getNome().equalsIgnoreCase(nomeItem))
                .findFirst();
        if (itemParaRemover.isPresent()) {
            Item item = itemParaRemover.get();
            itens.remove(item);
            pesoAtual -= item.getPeso();
            return true;
        } else {
            return false;
        }
    }

    public void usarItem(String nomeItem, Personagem jogador) {
        Optional<Item> itemParaUsarOpt = itens.stream()
                .filter(item -> item.getNome().equalsIgnoreCase(nomeItem))
                .findFirst();
        if (itemParaUsarOpt.isPresent()) {
            Item item = itemParaUsarOpt.get();
            item.usar(jogador);

            if (item instanceof Alimento || item instanceof Agua) {
                removerItem(item.getNome());
            } else if (item.getDurabilidade() > 0) {
                item.setDurabilidade(item.getDurabilidade() - 1);
                if (item.getDurabilidade() == 0) {
                    removerItem(item.getNome());
                }
            }
        } else {
        }
    }

    public void listarItensConsole() {
        if (itens.isEmpty()) {
            System.out.println("Inventário vazio.");
            return;
        }
        System.out.println("--- Inventário (Console) ---");
        for (Item item : itens) {
            System.out.println("- " + item.toString());
        }
        System.out.println("Peso atual: " + String.format("%.2f", pesoAtual) + "/" + String.format("%.2f", pesoMaximo));
        System.out.println("----------------------------");
    }
}