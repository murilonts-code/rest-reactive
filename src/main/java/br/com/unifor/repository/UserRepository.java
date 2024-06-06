package br.com.unifor.repository;

import br.com.unifor.domain.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Blocking
public class UserRepository implements PanacheRepository<UserEntity> {
}
