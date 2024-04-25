package academy.wakanda.wakacop.pauta.application.api;

import academy.wakanda.wakacop.pauta.application.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/pauta")
@RequiredArgsConstructor
public class PautaController implements PautaAPI {
    private final PautaService pautaService;

    @Override
    public PautaCadastradaResponse cadastraPauta(NovaPautaRequest novaPauta) {
        log.info("[inicia] PautaController - cadastraPauta");
        PautaCadastradaResponse pautaCadastrada = pautaService.cadastraPauta(novaPauta);
        log.info("[finaliza] PautaController - cadastraPauta");
        return pautaCadastrada;
    }
}
