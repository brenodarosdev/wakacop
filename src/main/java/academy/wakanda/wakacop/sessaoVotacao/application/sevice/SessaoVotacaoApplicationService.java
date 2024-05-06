package academy.wakanda.wakacop.sessaoVotacao.application.sevice;

import academy.wakanda.wakacop.pauta.application.service.PautaRepository;
import academy.wakanda.wakacop.pauta.application.service.PautaService;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.pauta.infra.PautaInfraRepository;
import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaResponse;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoResponse;
import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import academy.wakanda.wakacop.sessaoVotacao.domain.VotoPauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class SessaoVotacaoApplicationService implements SessaoVotacaoService {
    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final PautaService pautaService;

    @Override
    public SessaoAberturaResponse abreSessao(SessaoAberturaRequest sessaoAberturaRequest) {
        log.info("[inicia] SessaoVotacaoApplicationService - abreSessao");
        Pauta pauta = pautaService.getPautaPorId(sessaoAberturaRequest.getIdPauta());
        SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.salva(new SessaoVotacao(sessaoAberturaRequest, pauta));
        log.info("[finaliza] SessaoVotacaoApplicationService - abreSessao");
        return new SessaoAberturaResponse(sessaoVotacao);
    }

    @Override
    public VotoResponse recebeVoto(UUID idSessao, VotoRequest novoVoto) {
        log.info("[inicia] SessaoVotacaoApplicationService - recebeVoto");
        SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.buscaPorId(idSessao);
        VotoPauta voto = sessaoVotacao.recebeVoto(novoVoto);
        sessaoVotacaoRepository.salva(sessaoVotacao);
        log.info("[finaliza] SessaoVotacaoApplicationService - recebeVoto");
        return new VotoResponse(voto);
    }
}
