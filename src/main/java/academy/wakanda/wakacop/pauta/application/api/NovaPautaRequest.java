package academy.wakanda.wakacop.pauta.application.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

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
