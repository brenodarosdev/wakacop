package academy.wakanda.wakacop.sessaoVotacao.infra;

import academy.wakanda.wakacop.config.AwsConfigProperties;
import academy.wakanda.wakacop.sessaoVotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaoVotacao.domain.PublicadorResultadoSessao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Log4j2
@Component
@RequiredArgsConstructor
public class PublicadorResultadoSessaoInfraSNS implements PublicadorResultadoSessao {
    private final NotificationMessagingTemplate PublicadorResultadoSNS;
    private final AwsConfigProperties awsSnsProperties;

    @Override
    public void publica(ResultadoSessaoResponse resultadoSessaoResponse) {
        log.debug("[inicia] PublicadorResultadoSessaoInfraSNS - publica");
        PublicadorResultadoSNS.sendNotification(awsSnsProperties.getResultadoSessaoTopic(), resultadoSessaoResponse,
                resultadoSessaoResponse.getIdPauta().toString());
        log.debug("[finaliza] PublicadorResultadoSessaoInfraSNS - publica");
    }
}
