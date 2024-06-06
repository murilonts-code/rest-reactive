package br.com.unifor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PLAYLIST")
public class PlaylistEntity {

    @Id
    @SequenceGenerator(name = "users_id_seq", allocationSize = 1, initialValue = 5)
    @GeneratedValue(generator = "users_id_seq")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "playlist_music",
            joinColumns = @JoinColumn(name = "playlist_id", foreignKey = @ForeignKey(name = "fk_playlist_id")),
            inverseJoinColumns = @JoinColumn(name = "music_id", foreignKey = @ForeignKey(name = "fk_music_id")))
    private Set<MusicEntity> musics = new HashSet<>();
}
