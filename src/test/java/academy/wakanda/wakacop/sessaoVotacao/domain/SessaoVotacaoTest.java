package academy.wakanda.wakacop.sessaoVotacao.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SessaoVotacaoTest {

    @Test
    void deveFecharSessaoQuandoChamarMetodoFechaSessao() {
        SessaoVotacao sessao = buildSessao();
        PublicadorResultadoSessao publicador = new PublicadorResultadoSessaoMockTest();
        sessao.fechaSessao(publicador);

        assertEquals(StatusSessaoVotacao.FECHADA, sessao.getStatus());
    }

    @Test
    void deveFecharSessaoQuandoStatusAbertaEMomentoEncerramentoEstiverNoPassado() {
        SessaoVotacao sessao = buildSessao();
        PublicadorResultadoSessao publicador = new PublicadorResultadoSessaoMockTest();
        sessao.atualizaStatus(publicador);

        assertEquals(StatusSessaoVotacao.FECHADA, sessao.getStatus());
    }

    @Test
    void deveFicarAbertaSessaoQuandoStatusAbertaEMomentoEncerramentoEstiverNoFuturo() {
        SessaoVotacao sessao = buildSessaoEncerramentoFuturo();
        PublicadorResultadoSessao publicador = new PublicadorResultadoSessaoMockTest();
        sessao.atualizaStatus(publicador);

        assertEquals(StatusSessaoVotacao.ABERTA, sessao.getStatus());
    }

    private SessaoVotacao buildSessaoEncerramentoFuturo() {
        return SessaoVotacao.builder()
                .idSessao(UUID.randomUUID())
                .idPauta(UUID.randomUUID())
                .status(StatusSessaoVotacao.ABERTA)
                .momentoAbertura(LocalDateTime.of(2024, 1, 1, 1, 1))
                .momentoEncerramento(LocalDateTime.MAX)
                .votos(getVotos())
                .build();
    }

    private SessaoVotacao buildSessao() {
        return SessaoVotacao.builder()
                .idSessao(UUID.randomUUID())
                .idPauta(UUID.randomUUID())
                .status(StatusSessaoVotacao.ABERTA)
                .momentoAbertura(LocalDateTime.of(2024, 1, 1, 1, 1))
                .momentoEncerramento(LocalDateTime.of(2024, 1, 1, 1, 2))
                .votos(getVotos())
                .build();
    }

    private Map<String, VotoPauta> getVotos() {
        return Map.of("42af6ae9-faa1-4101-889c-f507d1a0933a", VotoPauta.builder().cpfAssociado(("03255436754")).opcaoVoto(OpcaoVoto.SIM).build(),
                "42af6ae9-faa1-4101-889c-f507d1a0934a", VotoPauta.builder().cpfAssociado(("03255436754")).opcaoVoto(OpcaoVoto.SIM).build());
    }

}