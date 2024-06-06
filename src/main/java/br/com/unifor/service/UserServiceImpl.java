package br.com.unifor.service;

import br.com.unifor.domain.UserEntity;
import br.com.unifor.repository.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@ApplicationScoped
public class UserServiceImpl {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public Uni<UserEntity> create(UserEntity request) {
        log.info("Creating a new user.");
        request.setId(null);
        return repository.persistAndFlush(request);
    }

    public Uni<UserEntity> update(UserEntity request) {
        log.info("Updating the user. id: " + request.getId());
        return repository.findById(request.getId())
                .onItem().ifNull().fail()
                .onItem().ifNotNull().transformToUni(saved ->
                {
                    saved.setName(request.getName());
                    saved.setAge(request.getAge());
                    return repository.persistAndFlush(saved);
                });
    }

    public Uni<UserEntity> findById(long request) {
        log.info("Finding the user. id: " + request);
        Uni<UserEntity> entity = repository.findById(request);
        return entity.onItem().ifNull().fail();
    }

    public Uni<List<UserEntity>> list() {
        log.info("Listing all users.");
        return repository.listAll();
    }

    public Uni<Boolean> delete(long request) {
        log.info("Deleting user by id. id: " + request);
        return repository.deleteById(request);
    }
}
