package academy.wakanda.wakacop.pauta.application.api;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@ToString
public class NovaPautaRequest {
    @NotBlank(message = "O título não pode estar em branco")
    private String titulo;
    @NotBlank(message = "A descrição não pode ser nula")
    private String descricao;
    @NotNull
    private UUID idAssociadoAutor;
}
