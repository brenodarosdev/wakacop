package academy.wakanda.wakacop.associado.application.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AssosiadoApplicationService implements AssosiadoService {
    @Override
    public void validaAssociadoAptoVoto(String cpfAssociado) {
        log.info("[inicia] AssosiadoApplicationService - validaAssociadoAptoVoto");

        log.info("[finaliza] AssosiadoApplicationService - validaAssociadoAptoVoto");
    }
}
