package com.ultimafronteira.events;

import com.ultimafronteira.model.Personagem;
import com.ultimafronteira.world.Ambiente;

public class EventoClimatico extends Evento {
    private String tipoDeClima;
    private int duracaoTurnosBase;
    private String efeitoNoAmbiente;

    public EventoClimatico(String nome, String descricao, double probabilidade,
                           String tipoDeClima, int duracaoTurnosBase, String efeitoNoAmbiente) {
        super(nome, descricao, probabilidade, "Clima/Ambiente");
        this.tipoDeClima = tipoDeClima;
        this.duracaoTurnosBase = duracaoTurnosBase;
        this.efeitoNoAmbiente = efeitoNoAmbiente;
    }

    public String getTipoDeClima() {
        return tipoDeClima;
    }

    public int getDuracaoTurnosBase() {
        return duracaoTurnosBase;
    }

    public String getEfeitoNoAmbiente() {
        return efeitoNoAmbiente;
    }

    @Override
    public String executar(Personagem jogador, Ambiente local, int numeroDoTurno) {
        StringBuilder sb = new StringBuilder();
        sb.append("EVENTO CLIMÁTICO: ").append(getNome()).append("!\n");
        sb.append(getDescricao()).append("\n");
        sb.append("Tipo de Clima: ").append(tipoDeClima).append("\n");
        sb.append("Efeito no Ambiente: ").append(efeitoNoAmbiente).append("\n");

        if (local != null) {
            sb.append("O ambiente ").append(local.getNome()).append(" é afetado por: ").append(tipoDeClima).append("\n");
        }

        if ("Nevasca".equals(tipoDeClima)) {
            sb.append(jogador.getNome()).append(" sente o frio intenso da nevasca!\n");
            jogador.setEnergia(jogador.getEnergia() - (15 + numeroDoTurno / 5)); // Efeito escala um pouco
            jogador.setSede(jogador.getSede() - (5 + numeroDoTurno / 10));
        } else if ("Tempestade".equals(tipoDeClima)) {
            sb.append("Uma forte tempestade dificulta a movimentação de ").append(jogador.getNome()).append("\n");
            jogador.setEnergia(jogador.getEnergia() - (10 + numeroDoTurno / 5));
        } else if ("Calor Extremo".equals(tipoDeClima)) {
            sb.append(jogador.getNome()).append(" sofre com o calor escaldante.\n");
            jogador.setSede(jogador.getSede() - (20 + numeroDoTurno / 3));
            jogador.setEnergia(jogador.getEnergia() - (5 + numeroDoTurno / 10));
        }
        sb.append("Este evento pode durar aproximadamente ").append(duracaoTurnosBase).append(" turnos.\n");
        return sb.toString();
    }
}