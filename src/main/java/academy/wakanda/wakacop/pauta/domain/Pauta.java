package academy.wakanda.wakacop.pauta.domain;

import academy.wakanda.wakacop.pauta.application.api.NovaPautaRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;
    private String titulo;
    private String descricao;
    private UUID idAssociadoAutor;
    private LocalDateTime dataHoraCriacao;

    public Pauta(NovaPautaRequest novaPauta) {
        this.titulo = novaPauta.getTitulo();
        this.descricao = novaPauta.getDescricao();
        this.idAssociadoAutor = novaPauta.getIdAssociadoAutor();
        this.dataHoraCriacao = LocalDateTime.now();
    }
}
