package br.com.unifor.service;

import br.com.unifor.domain.MusicEntity;
import br.com.unifor.repository.MusicRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@WithTransaction
public class MusicService {

    @Inject
    MusicRepository repository;

    public Uni<MusicEntity> create(MusicEntity request) {
        log.info("Creating a new user.");
        request.setId(null);
        return repository.persistAndFlush(request);
    }

    public Uni<MusicEntity> update(MusicEntity request) {
        log.info("Updating the user. id: " + request.getId());
        return repository.findById(request.getId())
                .onItem().ifNull().fail()
                .onItem().ifNotNull()
                .transformToUni(saved -> {
                    saved.setName(request.getName());
                    saved.setArtist(request.getArtist());
                    return repository.persistAndFlush(saved);
                });
    }

    public Uni<MusicEntity> findById(long request) {
        var entity = repository.findById(request);
        return entity.onItem().ifNull().fail();
    }

    public Uni<List<MusicEntity>> list() {
        log.info("Listing all musics.");
        return repository.listAll();
    }

    public Uni<List<MusicEntity>> musicFromPlaylist(long request) {
        return repository.findAllFromPlaylist(request);
    }

    public Uni<Boolean> delete(long request) {
        log.info("Deleting music by id. id: " + request);
        return repository.deleteById(request);
    }
}
