package academy.wakanda.wakacop.sessaoVotacao.application.api;

import academy.wakanda.wakacop.sessaoVotacao.domain.OpcaoVoto;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class VotoRequest {
    private String cpfAssociado;
    private OpcaoVoto opcao;
}
