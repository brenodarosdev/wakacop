package academy.wakanda.wakacop.sessaoVotacao.domain;

import academy.wakanda.wakacop.associado.application.sevice.AssosiadoService;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.sessaoVotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaoVotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaoVotacao.application.api.VotoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoVotacaoTest {

    @InjectMocks
    private SessaoVotacao sessaoVotacao;

    @Mock
    private AssosiadoService assosiadoService;

    @Mock
    private PublicadorResultadoSessao publicadorResultadoSessao;

    private SessaoAberturaRequest sessaoAberturaRequest;
    private Pauta pauta;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sessaoAberturaRequest = SessaoAberturaRequest.builder()
                .tempoDuracao(1)
                .build();

        pauta = Pauta.builder()
                .id(UUID.randomUUID())
                .build();

        sessaoVotacao = new SessaoVotacao(sessaoAberturaRequest, pauta);
    }

    @Test
    void testRecebeVoto() {
        VotoRequest votoRequest = VotoRequest.builder()
                .cpfAssociado("12345678900")
                .opcao(OpcaoVoto.SIM)
                .build();

        sessaoVotacao.recebeVoto(votoRequest, assosiadoService, publicadorResultadoSessao);
        assertEquals(1, sessaoVotacao.getTotalVotos());
        assertEquals(1, sessaoVotacao.getTotalSim());
    }

    @Test
    void testValidaSessaoAberta() {
        sessaoVotacao.validaSessaoAberta(publicadorResultadoSessao);
        assertEquals(StatusSessaoVotacao.ABERTA, sessaoVotacao.getStatus());
    }

    @Test
    void testAtualizaStatusFecharSessao() {
        SessaoVotacao sessao = SessaoVotacao.builder()
                .idSessao(UUID.randomUUID())
                .idPauta(UUID.randomUUID())
                .status(StatusSessaoVotacao.ABERTA)
                .momentoAbertura(LocalDateTime.of(2023, 1, 1, 1, 1))
                .momentoEncerramento(LocalDateTime.of(2023, 1, 1, 1, 3))
                .votos(getVotos())
                .build();
        sessao.atualizaStatus(publicadorResultadoSessao);
        assertEquals(StatusSessaoVotacao.FECHADA, sessao.getStatus());
    }

    private Map<String, VotoPauta> getVotos() {
        return Map.of("50693166568", VotoPauta.builder().cpfAssociado("50693266568").opcaoVoto(OpcaoVoto.SIM).build(),
                "50693466568", VotoPauta.builder().cpfAssociado("50693166568").opcaoVoto(OpcaoVoto.SIM).build());
    }

    @Test
    void testValidaVotoDuplicado() {
        VotoRequest votoRequest = VotoRequest.builder()
                .cpfAssociado("12345678900")
                .opcao(OpcaoVoto.SIM)
                .build();

        sessaoVotacao.recebeVoto(votoRequest, assosiadoService, publicadorResultadoSessao);

        Throwable exception = assertThrows(RuntimeException.class, () -> {
            sessaoVotacao.recebeVoto(votoRequest, assosiadoService, publicadorResultadoSessao);
        });
        assertEquals("Associado já votou nessa sessão!", exception.getMessage());
    }

    @Test
    void testObtemResultado() {
        ResultadoSessaoResponse resultado = sessaoVotacao.obtemResultado(publicadorResultadoSessao);
        assertNotNull(resultado);
    }

    @Test
    void testFechaSessao() {
        sessaoVotacao.fechaSessao(publicadorResultadoSessao);
        assertEquals(StatusSessaoVotacao.FECHADA, sessaoVotacao.getStatus());
        verify(publicadorResultadoSessao).publica(any(ResultadoSessaoResponse.class));
    }

    @Test
    void testValidaSessaoAbertaQuandoSessaoFechada() {
        sessaoVotacao.fechaSessao(publicadorResultadoSessao);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sessaoVotacao.validaSessaoAberta(publicadorResultadoSessao);
        });
        assertEquals("Sessão está fechada!", exception.getMessage());
    }
}