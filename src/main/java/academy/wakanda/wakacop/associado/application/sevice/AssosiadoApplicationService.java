package academy.wakanda.wakacop.associado.application.sevice;

import academy.wakanda.wakacop.associado.application.infra.client.ConsultaCpfResponse;
import academy.wakanda.wakacop.associado.infra.client.SerproClientFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AssosiadoApplicationService implements AssosiadoService {
    private final SerproClientFeign serproClientFeign;

    @Override
    public void validaAssociadoAptoVoto(String cpfAssociado) {
        log.debug("[start] AssociadoApplicationService - validaAssociadoAptoVoto");
        ConsultaCpfResponse consultaCPFResponse = serproClientFeign.consultaCPF(TOKEN,cpfAssociado);
        valida(consultaCPFResponse);
        log.debug("[finish] AssociadoApplicationService - validaAssociadoAptoVoto");
    }

    private void valida(ConsultaCpfResponse consultaCPFResponse) {
        if(consultaCPFResponse.isInvalid()) {
            throw new RuntimeException("CPF associado inválido");
        }
    }
    private static final String TOKEN = "Bearer 06aef429-a981-3ec5-a1f8-71d38d86481e";
}
