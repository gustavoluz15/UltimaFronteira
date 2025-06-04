package com.ultimafronteira.model;

import com.ultimafronteira.world.Ambiente;
import com.ultimafronteira.events.GerenciadorDeEventos;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Personagem {
    private String nome;
    private int vida;
    private int vidaMaxima; // Novo atributo
    private int fome;
    private int sede;
    private int energia;
    private int sanidade;
    private Inventario inventario;
    private Ambiente localizacaoAtual;
    private ClassePersonagem classePersonagem;
    private List<String> habilidades;
    private Arma armaEquipada;

    public Personagem(String nome, ClassePersonagem classe, int vidaBase, int fomeBase, int sedeBase, int energiaBase, int sanidadeBase, Ambiente localizacaoInicial, double pesoMaximoInventario) {
        this.nome = nome;
        this.classePersonagem = classe;
        this.habilidades = new ArrayList<>();

        this.vida = vidaBase; // Vida inicial base
        this.fome = fomeBase;
        this.sede = sedeBase;
        this.energia = energiaBase;
        this.sanidade = sanidadeBase;
        this.armaEquipada = null;

        aplicarBonusDeClasseEAdicionarHabilidadesIniciais();

        // Define vidaMaxima após os bônus de classe serem aplicados à vida
        this.vidaMaxima = this.vida;
        // Garante que a vida atual não exceda a máxima recém-definida (caso a base já fosse alta)
        this.vida = Math.min(this.vida, this.vidaMaxima);


        this.localizacaoAtual = localizacaoInicial;
        this.inventario = new Inventario(pesoMaximoInventario);
    }

    private void aplicarBonusDeClasseEAdicionarHabilidadesIniciais() {
        if (this.classePersonagem == null) return;

        switch (this.classePersonagem) {
            case RASTREADOR:
                this.fome += 15;
                this.sede += 10;
                this.habilidades.add("Rastreamento Aguçado");
                this.habilidades.add("Conhecimento da Flora e Fauna");
                this.habilidades.add("Movimentação Silenciosa");
                break;
            case MECANICO:
                this.energia += 10;
                this.habilidades.add("Engenhosidade");
                this.habilidades.add("Fabricar Ferramentas Básicas");
                this.habilidades.add("Desmontar Sucata");
                break;
            case MEDICO:
                this.sanidade += 10;
                this.vida += 5; // Pequeno bônus de vida para o médico
                this.habilidades.add("Primeiros Socorros Avançados");
                this.habilidades.add("Conhecimento de Ervas Medicinais");
                this.habilidades.add("Resistência a Doenças Leves");
                break;
            case SOBREVIVENTE_NATO:
                this.vida += 15;
                this.fome += 25;
                this.sede += 25;
                this.energia += 5;
                this.habilidades.add("Metabolismo Eficiente");
                this.habilidades.add("Resiliência Climática");
                this.habilidades.add("Instinto de Perigo");
                break;
        }
        // Garante que os atributos base não fiquem abaixo de um mínimo após modificações
        this.vida = Math.max(1, this.vida);
        this.fome = Math.max(1, this.fome);
        this.sede = Math.max(1, this.sede);
        this.energia = Math.max(0, this.energia);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; } // Getter para vidaMaxima

    public void setVida(int vida) {
        this.vida = Math.max(0, vida); // Não pode ser menor que 0
        this.vida = Math.min(this.vida, this.vidaMaxima); // Não pode exceder vidaMaxima
    }

    public int getFome() { return fome; }
    public void setFome(int fome) { this.fome = Math.max(0, fome); }
    public int getSede() { return sede; }
    public void setSede(int sede) { this.sede = Math.max(0, sede); }
    public int getEnergia() { return energia; }
    public void setEnergia(int energia) { this.energia = Math.max(0, energia); }
    public int getSanidade() { return sanidade; }
    public void setSanidade(int sanidade) { this.sanidade = Math.max(0, sanidade); }
    public Inventario getInventario() { return inventario; }
    public Ambiente getLocalizacaoAtual() { return localizacaoAtual; }
    public void setLocalizacaoAtual(Ambiente localizacaoAtual) { this.localizacaoAtual = localizacaoAtual; }
    public ClassePersonagem getClassePersonagem() { return classePersonagem; }
    public List<String> getHabilidades() { return new ArrayList<>(habilidades); }
    public boolean temHabilidade(String nomeHabilidade) { return this.habilidades.contains(nomeHabilidade); }
    public Arma getArmaEquipada() { return armaEquipada; }

    public String equiparArma(String nomeArma) {
        Optional<Item> itemNoInventario = this.inventario.getItens().stream()
                .filter(i -> i instanceof Arma && i.getNome().equalsIgnoreCase(nomeArma))
                .findFirst();
        if (itemNoInventario.isPresent()) {
            Arma novaArma = (Arma) itemNoInventario.get();
            if (this.armaEquipada != null && this.armaEquipada.equals(novaArma)) {
                return novaArma.getNome() + " já está equipada.";
            }
            this.armaEquipada = novaArma;
            return novaArma.getNome() + " equipada.";
        } else {
            return "Arma '" + nomeArma + "' não encontrada no inventário.";
        }
    }

    public String desequiparArma() {
        if (this.armaEquipada != null) {
            String nomeArmaDesequipada = this.armaEquipada.getNome();
            this.armaEquipada = null;
            return nomeArmaDesequipada + " desequipada.";
        } else {
            return "Nenhuma arma equipada para desequipar.";
        }
    }

    public String getStatus() {
        String nomeLocalizacao = (localizacaoAtual != null) ? localizacaoAtual.getNome() : "Desconhecida";
        StringBuilder habilidadesStr = new StringBuilder();
        if (!habilidades.isEmpty()) {
            for(String hab : habilidades) { habilidadesStr.append(hab).append(", "); }
            if (habilidadesStr.length() > 0) { habilidadesStr.setLength(habilidadesStr.length() - 2); }
        } else {
            habilidadesStr.append("Nenhuma");
        }
        String armaEquipadaStr = (armaEquipada != null) ? armaEquipada.getNome() : "Nenhuma";

        return String.format(
                "%-15s: %s (%s)\n" +
                        "%-15s: %d / %d\n" + // Vida atual / Vida Máxima
                        "%-15s: %d\n" +
                        "%-15s: %d\n" +
                        "%-15s: %d\n" +
                        "%-15s: %d\n" +
                        "%-15s: %s\n" +
                        "%-15s: %s\n" +
                        "%-15s: [%s]",
                "Nome", nome, (classePersonagem != null ? classePersonagem.getNomeDisplay() : "Sem Classe"),
                "Vida", vida, vidaMaxima, // Exibe vida atual e máxima
                "Fome", fome,
                "Sede", sede,
                "Energia", energia,
                "Sanidade", sanidade,
                "Localização", nomeLocalizacao,
                "Arma Equipada", armaEquipadaStr,
                "Habilidades", habilidadesStr.toString()
        );
    }

    public String explorarAmbiente(GerenciadorDeEventos ge, int numeroDoTurno) {
        if (this.localizacaoAtual != null) {
            return this.localizacaoAtual.explorar(this, ge, numeroDoTurno);
        } else {
            return this.nome + " não está em nenhum ambiente conhecido para explorar.";
        }
    }
}