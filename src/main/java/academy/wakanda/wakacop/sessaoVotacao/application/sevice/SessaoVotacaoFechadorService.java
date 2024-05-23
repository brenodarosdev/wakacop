package academy.wakanda.wakacop.sessaoVotacao.application.sevice;

import academy.wakanda.wakacop.sessaoVotacao.domain.PublicadorResultadoSessao;
import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SessaoVotacaoFechadorService {
    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final PublicadorResultadoSessao publicador;

    @Scheduled(cron = "0 * * * * *")
    public void fechaSessoesEncerradas() {
        log.debug("[inicia] SessaoVotacaoFechadorService - fechaSessoesEncerradas");
        List<SessaoVotacao> sessoesAbertas = sessaoVotacaoRepository.buscaAbertas();
        log.debug("[sessoesAbertas] {}", sessoesAbertas);
        sessoesAbertas.forEach(sessaoVotacao -> {
            sessaoVotacao.obtemResultado(publicador);
            sessaoVotacaoRepository.salva(sessaoVotacao);
    });
        log.debug("[finaliza] SessaoVotacaoFechadorService - fechaSessoesEncerradas");
    }
}
