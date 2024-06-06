package br.com.unifor.resource;

import br.com.unifor.domain.MusicEntity;
import br.com.unifor.service.MusicService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/musics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicResource {

    @Inject
    MusicService musicService;

    @POST
    public Uni<Response> create(MusicEntity musicRequest) {
        return musicService.create(musicRequest)
                .onItem()
                .transform(inserted -> Response.created(URI.create("/users/" + inserted.getId())).build());
    }

    @PUT
    public Uni<MusicEntity> update(MusicEntity musicRequest) {
        return musicService.update(musicRequest);
    }

    @GET
    public Uni<List<MusicEntity>> list() {
        return musicService.list();
    }

    @GET
    @Path("/playlist/{id}")
    public Uni<List<MusicEntity>> musicsFromPlaylist(@PathParam("id") long id) {
        return musicService.musicFromPlaylist(id);
    }

    @GET
    @Path("/{id}")
    public Uni<MusicEntity> findById(Long id) {
        return musicService.findById(id);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(Long id) {
        return musicService.delete(id).onItem()
                .transform(boolValue ->
                        boolValue ?
                                Response.ok().build() :
                                Response.noContent().build());
    }

}
