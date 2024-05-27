package academy.wakanda.wakacop.sessaoVotacao.application.infra;

import academy.wakanda.wakacop.sessaoVotacao.application.sevice.SessaoVotacaoRepository;
import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import academy.wakanda.wakacop.sessaoVotacao.domain.StatusSessaoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        log.debug("[inicia] SessaoVotacaoInfraRepository - buscaPorId");
        SessaoVotacao sessaoVotacao = sessaoVotacaoSpringDataJPARepository.findById(idSessao)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
        log.debug("[finaliza] SessaoVotacaoInfraRepository - buscaPorId");
        return sessaoVotacao;
    }

    @Override
    public List<SessaoVotacao> buscaAbertas() {
        log.debug("[inicia] SessaoVotacaoInfraRepository - buscaAbertas");
        List<SessaoVotacao> sessoes = sessaoVotacaoSpringDataJPARepository.findByStatus(StatusSessaoVotacao.ABERTA);
        log.debug("[finaliza] SessaoVotacaoInfraRepository - buscaAbertas");
        return sessoes;
    }
}
