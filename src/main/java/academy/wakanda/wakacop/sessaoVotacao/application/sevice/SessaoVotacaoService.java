package academy.wakanda.wakacop.sessaoVotacao.application.sevice;

import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaResponse;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoResponse;
import academy.wakanda.wakacop.sessaoVotacao.domain.VotoPauta;

import java.util.UUID;

public interface SessaoVotacaoService {
    SessaoAberturaResponse abreSessao(SessaoAberturaRequest sessaoAberturaRequest);
    VotoResponse recebeVoto(UUID idSessao, VotoRequest novoVoto);
}
