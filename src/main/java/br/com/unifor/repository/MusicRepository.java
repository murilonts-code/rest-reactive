package br.com.unifor.repository;

import br.com.unifor.domain.MusicEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@Blocking
@ApplicationScoped
public class MusicRepository implements PanacheRepository<MusicEntity> {
    public Uni<List<MusicEntity>> findAllFromPlaylist(long playlistId) {
        return list("""
                select m from MusicEntity m
                LEFT JOIN m.playlists pl
                where pl.id = ?1
                """, playlistId);
    }
}

