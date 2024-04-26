package academy.wakanda.wakacop.sessaoVotacao.application.infra;

import academy.wakanda.wakacop.sessaoVotacao.domain.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessaoVotacaoSpringDataJPARepository extends JpaRepository<SessaoVotacao, UUID> {
}
