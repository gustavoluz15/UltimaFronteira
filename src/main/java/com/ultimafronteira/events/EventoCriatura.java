package com.ultimafronteira.events;

import com.ultimafronteira.model.Personagem;
import com.ultimafronteira.model.Alimento;
import com.ultimafronteira.world.Ambiente;

public class EventoCriatura extends Evento {
    private String tipoDeCriatura;
    private String nivelDePerigo;
    private int baseDanoCriatura;
    private int vidaBaseCriatura;
    private transient int vidaAtualCriatura;
    private String[] opcoesDeAcao;

    public EventoCriatura(String nome, String descricao, double probabilidade,
                          String tipoDeCriatura, String nivelDePerigo,
                          int baseDanoCriatura, int vidaBaseCriatura, String[] opcoesDeAcao) {
        super(nome, descricao, probabilidade, "Confronto com Criatura");
        this.tipoDeCriatura = tipoDeCriatura;
        this.nivelDePerigo = nivelDePerigo;
        this.baseDanoCriatura = baseDanoCriatura;
        this.vidaBaseCriatura = vidaBaseCriatura;
        this.opcoesDeAcao = opcoesDeAcao;
        // vidaAtualCriatura será inicializada no executar, com base no turno
    }

    public String getTipoDeCriatura() { return tipoDeCriatura; }
    public String getNivelDePerigo() { return nivelDePerigo; }
    public int getBaseDanoCriatura() { return baseDanoCriatura; }
    public int getVidaBaseCriatura() { return vidaBaseCriatura; }
    public int getVidaAtualCriatura() { return vidaAtualCriatura; }
    public String[] getOpcoesDeAcao() { return opcoesDeAcao; }

    public int calcularDanoEfetivoCriatura(int numeroDoTurno) {
        return this.baseDanoCriatura + (numeroDoTurno / 4); // Exemplo de escalonamento
    }

    public String receberDano(int danoRecebido) {
        this.vidaAtualCriatura -= danoRecebido;
        this.vidaAtualCriatura = Math.max(0, this.vidaAtualCriatura); // Não deixa vida negativa
        if (this.vidaAtualCriatura == 0) {
            return tipoDeCriatura + " foi derrotado(a)!\n";
        }
        return tipoDeCriatura + " recebeu " + danoRecebido + " de dano. (PV restantes: " + this.vidaAtualCriatura + ")\n";
    }

    @Override
    public String executar(Personagem jogador, Ambiente local, int numeroDoTurno) {
        StringBuilder sb = new StringBuilder();

        this.vidaAtualCriatura = this.vidaBaseCriatura + (numeroDoTurno / 2);
        this.vidaAtualCriatura = Math.max(1, this.vidaAtualCriatura);

        sb.append("ALERTA DE CRIATURA: ").append(getNome().toUpperCase()).append("!\n");
        sb.append(getDescricao()).append("\n");
        sb.append("Um(a) hostil ").append(tipoDeCriatura).append(" (PV: ").append(this.vidaAtualCriatura).append(") apareceu!\n");
        sb.append("Nível de Perigo Percebido: ").append(nivelDePerigo).append(".\n");

        if (local != null) {
            sb.append("Local do Encontro: ").append(local.getNome()).append("\n");
        }

        if (opcoesDeAcao != null && opcoesDeAcao.length > 0) {
            sb.append("Você pode tentar: ");
            for (int i = 0; i < opcoesDeAcao.length; i++) {
                sb.append("[").append(opcoesDeAcao[i]).append("]");
                sb.append(i < opcoesDeAcao.length - 1 ? ", " : "");
            }
            sb.append(".\n");
        }
        sb.append("Prepare-se para o confronto!\n");

        return sb.toString();
    }
}