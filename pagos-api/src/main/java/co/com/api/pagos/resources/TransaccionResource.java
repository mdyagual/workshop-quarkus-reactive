package co.com.api.pagos.resources;

import co.com.api.pagos.client.SolicitudService;
import co.com.api.pagos.dto.TransaccionDTO;
import co.com.api.pagos.service.TransaccionService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/pagos")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class TransaccionResource {

    private final TransaccionService service;

    @RestClient
    private final SolicitudService solicitudService;

    @GET
    public Multi<Response> obtenerTodo(){
        return service.listAll().onItem().transform(transaccionDTO -> Response.ok(transaccionDTO).build());
    }

    @GET
    @Path("/{idTransaccion}")
    public Uni<Response> obtenerPorId(@PathParam("idTransaccion") String idTransaccion){
        return service.listById(idTransaccion).onItem().transform(transaccionDTO -> Response.ok(transaccionDTO).build());
    }


    @POST
    public Uni<Response> guardar(@Valid TransaccionDTO transaccionDTO){
        return service.save(transaccionDTO).onItem().transform(transaccionDTO1 -> Response.status(201).entity(transaccionDTO1).build());
    }

    @PUT
    public Uni<Response> editar(@Valid TransaccionDTO transaccionDTO){
        return service.update(transaccionDTO).onItem().transform(transaccionDTO1 -> Response.status(202).entity(transaccionDTO1).build());
    }

    @DELETE
    @Path("/{idTransaccion}")
    public Uni<Response> eliminar(@PathParam("idTransaccion") String idTransaccion){
        return service.delete(idTransaccion).onItem().transform(aLong -> Response.ok(aLong).build());
    }

}
