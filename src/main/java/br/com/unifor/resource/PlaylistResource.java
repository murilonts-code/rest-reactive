package br.com.unifor.resource;

import br.com.unifor.domain.PlaylistEntity;
import br.com.unifor.dto.request.PlaylistMusicRequest;
import br.com.unifor.service.PlaylistServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaylistResource {

    @Inject
    PlaylistServiceImpl playlistService;

    @POST
    public Uni<Response> create(PlaylistEntity playlistRequest) {
        return playlistService.create(playlistRequest)
                .onItem()
                .transform(inserted -> Response.created(URI.create("/playlists/" + inserted.getId())).build());
    }

    @POST
    @Path("/music")
    public Uni<Response> addMusicToPlaylist(PlaylistMusicRequest playlistMusicRequest) {
        return playlistService.addMusicToPlaylist(playlistMusicRequest)
                .onItem()
                .transform(inserted -> Response.created(URI.create("/playlists/" + inserted.getId())).build());
    }

    @PUT
    public Uni<PlaylistEntity> update(PlaylistEntity playlistRequest) {
        return playlistService.update(playlistRequest);
    }

    @GET
    public Uni<List<PlaylistEntity>> list() {
        return playlistService.list();
    }


    @GET
    @Path("/user/{id}")
    public Uni<List<PlaylistEntity>> list(@PathParam("id") long id) {
        return playlistService.findByUserId(id);
    }

    @GET
    @Path("/{id}")
    public Uni<PlaylistEntity> findById(Long id) {
        return playlistService.findById(id);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(Long id) {
        return playlistService.delete(id).onItem()
                .transform(boolValue ->
                        boolValue ?
                                Response.ok().build() :
                                Response.noContent().build());
    }

    @DELETE
    @Path("/music")
    public Uni<Response> removeMusicOnPlaylist(PlaylistMusicRequest playlistMusicRequest) {
        return playlistService.addMusicToPlaylist(playlistMusicRequest)
                .onItem()
                .transform(inserted -> Response.created(URI.create("/playlists/" + inserted.getId())).build());
    }

}
