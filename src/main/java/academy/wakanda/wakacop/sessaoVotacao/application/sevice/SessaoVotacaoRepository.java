package academy.wakanda.wakacop.sessaoVotacao.application.sevice;

import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;

import java.util.UUID;

public interface SessaoVotacaoRepository {
    SessaoVotacao salva(SessaoVotacao sessaoVotacao);
    SessaoVotacao buscaPorId(UUID idSessao);
}
