package academy.wakanda.wakacop.sessaoVotacao.application.infra;

import academy.wakanda.wakacop.sessaoVotacao.application.sevice.SessaoVotacaoRepository;
import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Log4j2
@Repository
@RequiredArgsConstructor
public class SessaoVotacaoInfraRepository implements SessaoVotacaoRepository {
    private final SessaoVotacaoSpringDataJPARepository sessaoVotacaoSpringDataJPARepository;

    @Override
    public SessaoVotacao salva(SessaoVotacao sessaoVotacao) {
        log.info("[inicia] SessaoVotacaoInfraRepository - salva");
        sessaoVotacaoSpringDataJPARepository.save(sessaoVotacao);
        log.info("[finaliza] SessaoVotacaoInfraRepository - salva");
        return sessaoVotacao;
    }

    @Override
    public SessaoVotacao buscaPorId(UUID idSessao) {
        log.info("[inicia] SessaoVotacaoInfraRepository - buscaPorId");
        SessaoVotacao sessaoVotacao = sessaoVotacaoSpringDataJPARepository.findById(idSessao)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
        log.info("[finaliza] SessaoVotacaoInfraRepository - buscaPorId");
        return sessaoVotacao;
    }
}
