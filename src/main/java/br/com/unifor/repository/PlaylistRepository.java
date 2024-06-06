package br.com.unifor.repository;

import br.com.unifor.domain.PlaylistEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Blocking
public class PlaylistRepository implements PanacheRepository<PlaylistEntity> {
}
