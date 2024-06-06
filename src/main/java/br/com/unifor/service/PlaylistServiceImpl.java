package br.com.unifor.service;

import br.com.unifor.domain.PlaylistEntity;
import br.com.unifor.dto.request.PlaylistMusicRequest;
import br.com.unifor.repository.MusicRepository;
import br.com.unifor.repository.PlaylistRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@ApplicationScoped
public class PlaylistServiceImpl {

    private final PlaylistRepository repository;
    private final MusicRepository musicRepository;

    public PlaylistServiceImpl(PlaylistRepository repository, MusicRepository musicRepository) {
        this.repository = repository;
        this.musicRepository = musicRepository;
    }

    public Uni<PlaylistEntity> create(PlaylistEntity request) {
        log.info("Creating a new Playlist.");
        request.setId(null);
        return repository.persistAndFlush(request);
    }

    public Uni<PlaylistEntity> addMusicToPlaylist(PlaylistMusicRequest request) {
        log.info("Creating a new Playlist.");
        var playlistEntityUni = repository.findById(request.getPlaylistId());
        var musicEntityUni = musicRepository.findById(request.getPlaylistId());

        return playlistEntityUni.onItem()
                .ifNull().fail()
                .onItem()
                .transformToUni(playlistEntity -> musicEntityUni.onItem()
                        .ifNull().fail()
                        .onItem().transformToUni(musicEntity -> {
                            playlistEntity.getMusics().add(musicEntity);
                            return repository.persistAndFlush(playlistEntity);
                        }));
    }

    public Uni<PlaylistEntity> update(PlaylistEntity request) {
        log.info("Updating the Playlist. id: " + request.getId());
        return repository.findById(request.getId())
                .onItem().ifNull().fail()
                .onItem().ifNotNull().transformToUni(saved ->
                {
                    saved.setName(request.getName());
                    saved.setName(request.getName());
                    return repository.persistAndFlush(saved);
                });
    }

    public Uni<PlaylistEntity> findById(long request) {
        log.info("Finding the Playlist. id: " + request);
        var entity = repository.findById(request);
        return entity.onItem().ifNull().fail();
    }

    public Uni<List<PlaylistEntity>> list() {
        log.info("Listing all Playlists.");
        return repository.listAll();
    }

    public Uni<List<PlaylistEntity>> findByUserId(long request) {
        log.info("Listing all Playlists From User.");
        return repository.list("user.id", request);
    }

    public Uni<Boolean> delete(long request) {
        log.info("Deleting Playlist by id. id: " + request);
        return repository.deleteById(request);
    }
}
