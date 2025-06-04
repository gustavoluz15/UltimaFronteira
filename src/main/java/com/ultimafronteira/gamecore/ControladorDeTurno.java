package com.ultimafronteira.gamecore;

import com.ultimafronteira.model.Personagem;
import com.ultimafronteira.events.GerenciadorDeEventos;
import com.ultimafronteira.world.Ambiente;

public class ControladorDeTurno {
    private Personagem jogador;
    private GerenciadorDeEventos gerenciadorDeEventos;
    private int numeroDoTurno;
    private boolean jogoTerminou;
    private String mensagemFimDeJogo;

    private final int TURNOS_PARA_VITORIA_TEMPO = 10;

    public ControladorDeTurno(Personagem jogador, GerenciadorDeEventos gerenciadorDeEventos) {
        this.jogador = jogador;
        this.gerenciadorDeEventos = gerenciadorDeEventos;
        this.numeroDoTurno = 0;
        this.jogoTerminou = false;
        this.mensagemFimDeJogo = "";
    }

    public boolean isJogoTerminou() {
        return jogoTerminou;
    }

    public String getMensagemFimDeJogo() {
        return mensagemFimDeJogo;
    }

    private void terminarJogo(String mensagem) {
        this.jogoTerminou = true;
        this.mensagemFimDeJogo = mensagem;
    }

    private String verificarCondicoesDeFimDeJogo() {
        if (jogoTerminou) { // Se já terminou em uma fase anterior deste turno
            return "";
        }

        // Condições de Derrota
        if (jogador.getVida() <= 0) {
            terminarJogo(jogador.getNome() + " não resistiu aos perigos e sucumbiu.\nFIM DE JOGO - DERROTA");
            return this.mensagemFimDeJogo + "\n";
        }
        if (jogador.getSanidade() <= 0) {
            terminarJogo(jogador.getNome() + " perdeu completamente a sanidade, entregando-se à loucura.\nFIM DE JOGO - DERROTA");
            return this.mensagemFimDeJogo + "\n";
        }

        // Condições de Vitória
        if (this.numeroDoTurno >= TURNOS_PARA_VITORIA_TEMPO) {
            terminarJogo(jogador.getNome() + " sobreviveu por " + TURNOS_PARA_VITORIA_TEMPO + " longos turnos! Uma façanha incrível!\nFIM DE JOGO - VITÓRIA!");
            return this.mensagemFimDeJogo + "\n";
        }

        return ""; // Continua
    }

    public String executarProximoTurno() {
        if (jogoTerminou) {
            return "O jogo já terminou.\n" + mensagemFimDeJogo;
        }

        this.numeroDoTurno++;
        StringBuilder logDoTurno = new StringBuilder();

        logDoTurno.append("--- INÍCIO DO TURNO ").append(numeroDoTurno).append(" ---\n");
        logDoTurno.append(faseDeInicio());

        String fimDeJogoAposInicio = verificarCondicoesDeFimDeJogo();
        if(jogoTerminou) {
            logDoTurno.append(fimDeJogoAposInicio);
            return logDoTurno.toString();
        }

        logDoTurno.append("--- Fase de Ação do Jogador (use os botões de ação) ---\n");

        logDoTurno.append(faseDeEventoAleatorio());
        String fimDeJogoAposEvento = verificarCondicoesDeFimDeJogo();
        if(jogoTerminou) {
            logDoTurno.append(fimDeJogoAposEvento);
            return logDoTurno.toString();
        }

        logDoTurno.append(faseDeManutencao());
        String fimDeJogoAposManutencao = verificarCondicoesDeFimDeJogo();
        if(jogoTerminou) {
            logDoTurno.append(fimDeJogoAposManutencao);
        }

        logDoTurno.append("--- FIM DO TURNO ").append(numeroDoTurno).append(" ---\n");
        return logDoTurno.toString();
    }

    private String faseDeInicio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fase de Início:\n");
        Ambiente ambienteAtual = jogador.getLocalizacaoAtual();
        if (ambienteAtual != null) {
            String climaMsg = ambienteAtual.modificarClima();
            sb.append(climaMsg);
            sb.append("Condições atuais em ").append(ambienteAtual.getNome()).append(": ").append(ambienteAtual.getCondicoesClimaticasPredominantes()).append("\n");
        }
        return sb.toString();
    }

    private String faseDeEventoAleatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fase de Evento Aleatório:\n");
        String resultadoEvento = gerenciadorDeEventos.sortearEExecutarEvento(jogador, jogador.getLocalizacaoAtual(), this.numeroDoTurno);
        sb.append(resultadoEvento).append("\n");
        return sb.toString();
    }

    private String faseDeManutencao() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fase de Manutenção:\n");

        int fomePerdida = 5 + (numeroDoTurno / 10);
        int sedePerdida = 7 + (numeroDoTurno / 8);

        jogador.setFome(Math.max(0, jogador.getFome() - fomePerdida));
        sb.append(jogador.getNome()).append(" perdeu ").append(fomePerdida).append(" pontos de fome.\n");
        if (jogador.getFome() == 0 && jogador.getVida() > 0) { // Só aplica dano se estiver vivo
            int danoFome = 2 + numeroDoTurno / 15;
            jogador.setVida(jogador.getVida() - danoFome);
            sb.append("FOME CRÍTICA! ").append(jogador.getNome()).append(" perde ").append(danoFome).append(" de vida.\n");
        }

        jogador.setSede(Math.max(0, jogador.getSede() - sedePerdida));
        sb.append(jogador.getNome()).append(" perdeu ").append(sedePerdida).append(" pontos de sede.\n");
        if (jogador.getSede() == 0 && jogador.getVida() > 0) { // Só aplica dano se estiver vivo
            int danoSede = 3 + numeroDoTurno / 12;
            jogador.setVida(jogador.getVida() - danoSede);
            sb.append("SEDE CRÍTICA! ").append(jogador.getNome()).append(" perde ").append(danoSede).append(" de vida.\n");
        }

        return sb.toString();
    }

    public int getNumeroDoTurno() {
        return numeroDoTurno;
    }
}