package co.com.api.pagos.client;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/compras")
@RegisterRestClient(configKey = "compras-api")
@Produces(MediaType.APPLICATION_JSON)
@ClientHeaderParam(name = "caller-from-pagos",value = "compras-api")
public interface SolicitudService {

    @GET
    @Path("/{idSolicitud}")
    SolicitudDTO listById(@PathParam("idSolicitud") String idSolicitud);

    @PUT
    SolicitudDTO update(SolicitudDTO solicitudDTO);
}


