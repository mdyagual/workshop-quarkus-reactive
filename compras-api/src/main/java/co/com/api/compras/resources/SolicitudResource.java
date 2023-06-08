package co.com.api.compras.resources;

import co.com.api.compras.dto.SolicitudDTO;
import co.com.api.compras.service.SolicitudService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class SolicitudResource {
    private final SolicitudService service;

    @GET
    public Multi<Response> obtenerTodo(){
        return service.listAll().onItem().transform(solicitudDTO -> Response.ok(solicitudDTO).build());
    }

    @GET
    @Path("/{idSolicitud}")
    public Uni<Response> obtenerPorId(@PathParam("idSolicitud") String idSolicitud){
        return service.listById(idSolicitud).onItem().transform(solicitudDTO -> Response.ok(solicitudDTO).build());
    }

    @GET
    @Path("/{propietario}")
    public Multi<Response> obtenerPorUsuario(@PathParam("propietario") String propietario){
        return  service.listByPropietario(propietario).onItem().transform(solicitudDTO -> Response.ok(solicitudDTO).build());
    }

    @POST
    public Uni<Response> guardar(SolicitudDTO solicitudDTO){
        return service.save(solicitudDTO).onItem().transform(solicitudDTO1 -> Response.status(201).entity(solicitudDTO1).build());
    }

    @PUT
    public Uni<Response> editar(SolicitudDTO solicitudDTO){
        return service.update(solicitudDTO).onItem().transform(solicitudDTO1 -> Response.status(202).entity(solicitudDTO1).build());
    }

    @DELETE
    @Path("/{idSolicitud}")
    public Uni<Response> eliminar(@PathParam("idSolicitud") String idSolicitud){
        return service.delete(idSolicitud).onItem().transform(aLong -> Response.ok(aLong).build());
    }
}
