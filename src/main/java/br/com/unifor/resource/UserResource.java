package br.com.unifor.resource;

import br.com.unifor.domain.UserEntity;
import br.com.unifor.service.UserServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServiceImpl userService;


    @POST
    public Uni<Response> create(UserEntity userRequest) {
        return userService.create(userRequest).onItem().transform(inserted -> Response.created(URI.create("/users/" + inserted.getId())).build());
    }

    @PUT
    public Uni<UserEntity> update(UserEntity userRequest) {
        return userService.update(userRequest);
    }

    @GET
    public Uni<List<UserEntity>> list() {
        return userService.list();
    }

    @GET
    @Path("/{id}")
    public Uni<UserEntity> findById(Long id) {
        return userService.findById(id);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(Long id) {
        return userService.delete(id).onItem()
                .transform(boolValue ->
                        boolValue ?
                                Response.ok().build() :
                                Response.noContent().build());
    }

}
