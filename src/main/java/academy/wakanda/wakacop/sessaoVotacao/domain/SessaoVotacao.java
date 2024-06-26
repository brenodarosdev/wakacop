package academy.wakanda.wakacop.sessaoVotacao.domain;

import academy.wakanda.wakacop.associado.application.sevice.AssosiadoService;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.sessaoVotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoRequest;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Entity
@ToString
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID idSessao;
    private UUID idPauta;
    private Integer tempoDuracao;
    @Enumerated(EnumType.STRING)
    private StatusSessaoVotacao status;
    private LocalDateTime momentoAbertura;
    private LocalDateTime momentoEncerramento;

    @OneToMany(
            mappedBy = "sessaoVotacao",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKey(name = "cpfAssociado")
    private Map<String, VotoPauta> votos;

    public SessaoVotacao(SessaoAberturaRequest sessaoAberturaRequest, Pauta pauta) {
        this.idPauta = pauta.getId();
        this.tempoDuracao = sessaoAberturaRequest.getTempoDuracao().orElse(1);
        this.momentoAbertura = LocalDateTime.now();
        this.momentoEncerramento = momentoAbertura.plusMinutes(this.tempoDuracao);
        this.status = StatusSessaoVotacao.ABERTA;
        votos = new HashMap<>();
    }

    public VotoPauta recebeVoto(VotoRequest votoRequest, AssosiadoService assosiadoService, PublicadorResultadoSessao publicadorResultadoSessao) {
        validaSessaoAberta(publicadorResultadoSessao);
        validaAssociado(votoRequest.getCpfAssociado(), assosiadoService);
        VotoPauta voto = new VotoPauta(this, votoRequest);
        votos.put(votoRequest.getCpfAssociado(), voto);
        return voto;
    }

    void validaSessaoAberta(PublicadorResultadoSessao publicadorResultadoSessao) {
        atualizaStatus(publicadorResultadoSessao);
        if(this.status.equals(StatusSessaoVotacao.FECHADA)) {
            throw new RuntimeException("Sessão está fechada!");
        }
    }

    void atualizaStatus(PublicadorResultadoSessao publicadorResultadoSessao) {
        if(this.status.equals(StatusSessaoVotacao.ABERTA)) {
            if (LocalDateTime.now().isAfter(this.momentoEncerramento)) {
                fechaSessao(publicadorResultadoSessao);
            }
        }
    }

    void fechaSessao(PublicadorResultadoSessao publicadorResultadoSessao) {
        this.status = StatusSessaoVotacao.FECHADA;
        publicadorResultadoSessao.publica(new ResultadoSessaoResponse(this));
    }

    void validaAssociado(String cpfAssociado, AssosiadoService assosiadoService) {
        assosiadoService.validaAssociadoAptoVoto(cpfAssociado);
        validaVotoDuplicado(cpfAssociado);
    }

    void validaVotoDuplicado(String cpfAssociado) {
        if(this.votos.containsKey(cpfAssociado)) {
            throw new RuntimeException("Associado já votou nessa sessão!");
        }
    }

    public ResultadoSessaoResponse obtemResultado(PublicadorResultadoSessao publicadorResultadoSessao) {
        atualizaStatus(publicadorResultadoSessao);
        return new ResultadoSessaoResponse(this);
    }

    public Long getTotalVotos() {
        return Long.valueOf(this.votos.size());
    }

    public Long getTotalSim() {
        return calculaVotosPorOpcao(OpcaoVoto.SIM);
    }

    public Long getTotalNao() {
        return calculaVotosPorOpcao(OpcaoVoto.NAO);
    }

    private Long calculaVotosPorOpcao(OpcaoVoto opcao) {
        return votos.values().stream()
                .filter(voto -> voto.opcaoIgual(opcao))
                .count();
    }
}
