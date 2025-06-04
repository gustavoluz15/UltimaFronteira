package com.ultimafronteira.events;

import com.ultimafronteira.model.Personagem;
import com.ultimafronteira.world.Ambiente;

public class EventoDoencaFerimento extends Evento {
    private String tipoDeCondicao;
    private String impactoDescricao;
    private String curaSugerida;

    public EventoDoencaFerimento(String nome, String descricao, double probabilidade,
                                 String tipoDeCondicao, String impactoDescricao, String curaSugerida) {
        super(nome, descricao, probabilidade, "Saúde/Condição");
        this.tipoDeCondicao = tipoDeCondicao;
        this.impactoDescricao = impactoDescricao;
        this.curaSugerida = curaSugerida;
    }

    public String getTipoDeCondicao() {
        return tipoDeCondicao;
    }

    public String getImpactoDescricao() {
        return impactoDescricao;
    }

    public String getCuraSugerida() {
        return curaSugerida;
    }

    @Override
    public String executar(Personagem jogador, Ambiente local, int numeroDoTurno) {
        StringBuilder sb = new StringBuilder();
        sb.append("EVENTO DE DOENÇA/FERIMENTO: ").append(getNome()).append("!\n");
        sb.append(getDescricao()).append("\n");
        sb.append("Condição Adquirida: ").append(tipoDeCondicao).append("\n");
        sb.append("Impacto: ").append(impactoDescricao).append("\n");

        if (curaSugerida != null && !curaSugerida.isEmpty()) {
            sb.append("Tratamento Sugerido: ").append(curaSugerida).append("\n");
        }

        if ("Hipotermia".equals(tipoDeCondicao)) {
            jogador.setVida(jogador.getVida() - (10 + numeroDoTurno / 7));
            jogador.setEnergia(jogador.getEnergia() - (15 + numeroDoTurno / 5));
            sb.append(jogador.getNome()).append(" está tremendo de frio e perde vida e energia.\n");
        } else if ("Infecção Alimentar".equals(tipoDeCondicao)) {
            jogador.setVida(jogador.getVida() - (5 + numeroDoTurno / 8));
            jogador.setFome(Math.max(0, jogador.getFome() - (20 + numeroDoTurno / 4)));
            jogador.setEnergia(jogador.getEnergia() - (10 + numeroDoTurno / 6));
            sb.append(jogador.getNome()).append(" sente-se fraco e enjoado devido à infecção.\n");
        } else if ("Desidratação Severa".equals(tipoDeCondicao)) {
            jogador.setVida(jogador.getVida() - (10 + numeroDoTurno / 5));
            jogador.setSede(Math.max(0, jogador.getSede() - (30 + numeroDoTurno / 3)));
            jogador.setSanidade(jogador.getSanidade() - (5 + numeroDoTurno / 10));
            sb.append(jogador.getNome()).append(" está gravemente desidratado, afetando sua vida e sanidade.\n");
        } else if ("Fratura Exposta".equals(tipoDeCondicao)) {
            jogador.setVida(jogador.getVida() - (25 + numeroDoTurno / 3));
            jogador.setEnergia(jogador.getEnergia() - (20 + numeroDoTurno / 4));
            sb.append(jogador.getNome()).append(" sofreu uma fratura grave! Precisa de cuidados médicos urgentes.\n");
        }

        if (jogador.getVida() <= 0) {
            sb.append(jogador.getNome()).append(" sucumbiu à sua condição...\n");
        }
        return sb.toString();
    }
}