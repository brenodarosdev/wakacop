package academy.wakanda.wakacop.sessaoVotacao.domain;

import academy.wakanda.wakacop.sessaoVotacao.application.api.ResultadoSessaoResponse;

public interface PublicadorResultadoSessao {
    void publica(ResultadoSessaoResponse resultadoSessaoResponse);
}
