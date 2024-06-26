package academy.wakanda.wakacop.sessaoVotacao.application.api;

import academy.wakanda.wakacop.sessaoVotacao.domain.VotoPauta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/sessao")
public interface SessaoVotacaoAPI {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/abertura")
    SessaoAberturaResponse abreSessao(@RequestBody @Valid SessaoAberturaRequest sessaoAberturaRequest);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{idSessao}/voto")
    VotoResponse recebeVoto(@PathVariable UUID idSessao, @RequestBody @Valid VotoRequest novoVoto);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idSessao}/resultado")
    ResultadoSessaoResponse obtemResultado(@PathVariable UUID idSessao);
}
