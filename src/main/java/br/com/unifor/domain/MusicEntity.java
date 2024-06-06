package br.com.unifor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MUSIC")
public class MusicEntity {

    @Id
    @SequenceGenerator(name = "music_id_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "music_id_seq")
    private Long id;
    private String name;
    private String artist;

    @JsonIgnore
    @ManyToMany(mappedBy = "musics")
    private Set<PlaylistEntity> playlists = new HashSet<>();
}
