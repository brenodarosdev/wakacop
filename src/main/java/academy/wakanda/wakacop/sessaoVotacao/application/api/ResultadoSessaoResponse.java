package academy.wakanda.wakacop.sessaoVotacao.application.api;

import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import academy.wakanda.wakacop.sessaoVotacao.domain.StatusSessaoVotacao;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class ResultadoSessaoResponse {
    private UUID id;
    private UUID idPauta;
    private StatusSessaoVotacao status;
    private LocalDateTime momentoAbertura;
    private LocalDateTime momentoEncerramento;
    private Long totalVotos;
    private Long totalSim;
    private Long totalNao;

    public ResultadoSessaoResponse(SessaoVotacao sessao) {
        this.id = sessao.getId();
        this.idPauta = sessao.getIdPauta();
        this.status = sessao.getStatus();
        this.momentoAbertura = sessao.getMomentoAbertura();
        this.momentoEncerramento = sessao.getMomentoEncerramento();
        this.totalVotos = sessao.getTotalVotos();
        this.totalSim = sessao.getTotalSim();
        this.totalNao = sessao.getTotalNao();
    }
}
