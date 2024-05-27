package academy.wakanda.wakacop.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aws.config")
public class AwsConfigProperties {
    private String region;
    private String accesskey;
    private String secretkey;
    private String endpointuri;
    private String resultadoSessaoTopic;
}