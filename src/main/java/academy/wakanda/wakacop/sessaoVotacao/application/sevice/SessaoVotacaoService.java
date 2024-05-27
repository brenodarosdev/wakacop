package academy.wakanda.wakacop.sessaoVotacao.application.sevice;

import academy.wakanda.wakacop.sessaoVotacao.application.api.*;
import academy.wakanda.wakacop.sessaoVotacao.domain.VotoPauta;

import java.util.UUID;

public interface SessaoVotacaoService {
    SessaoAberturaResponse abreSessao(SessaoAberturaRequest sessaoAberturaRequest);
    VotoResponse recebeVoto(UUID idSessao, VotoRequest novoVoto);
    ResultadoSessaoResponse obtemResultado(UUID idSessao);
}
