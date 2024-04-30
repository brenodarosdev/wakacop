package academy.wakanda.wakacop.pauta.infra;

import academy.wakanda.wakacop.pauta.application.service.PautaRepository;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PautaInfraRepository implements PautaRepository {
    private final PautaSpringDataJPARepository pautaSpringDataJPARepository;

    @Override
    public Pauta salva(Pauta pauta) {
        log.info("[inicia] PautaInfraRepository - salva");
        pautaSpringDataJPARepository.save(pauta);
        log.info("[finaliza] PautaInfraRepository - salva");
        return pauta;
    }

    @Override
    public Pauta buscaPautaPorId(UUID idPauta) {
        log.info("[inicia] PautaInfraRepository - buscaPautaPorId");
        Pauta pautaPorId = pautaSpringDataJPARepository.findById(idPauta)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
        log.info("[finaliza] PautaInfraRepository - buscaPautaPorId");
        return pautaPorId;
    }
}
