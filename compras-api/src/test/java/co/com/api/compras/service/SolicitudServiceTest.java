package co.com.api.compras.service;

import co.com.api.compras.config.SolicitudMapper;
import co.com.api.compras.config.SolicitudMapperImpl;
import co.com.api.compras.entity.Solicitud;
import co.com.api.compras.repository.SolicitudRepository;
import co.com.api.compras.service.impl.SolicitudServiceImpl;
import co.com.api.compras.utils.Tarjeta;
import co.com.api.compras.utils.Usuario;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


@QuarkusTest
class SolicitudServiceTest {

    @InjectMock
    SolicitudRepository mongoDbMock;

    SolicitudServiceImpl service;

    SolicitudMapper mapper;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        mapper = new SolicitudMapperImpl();
        service = new SolicitudServiceImpl(mongoDbMock, mapper);

    }

    @Test
    @DisplayName("success_get_all()")
    void getAllTestSuccess(){
        var solicitud1 = new Solicitud();
        var solicitud2 = new Solicitud();

        var solicitudDTO1 = mapper.toDto(solicitud1);
        var solicitudDTO2 = mapper.toDto(solicitud2);

        var multiSolicitud = Multi.createFrom().items(solicitud1,solicitud2);

        Mockito.when(mongoDbMock.getAll()).thenReturn(multiSolicitud);

        var result = service.listAll();
        result.subscribe()
                .withSubscriber(AssertSubscriber.create(2))
                .awaitCompletion()
                .assertItems(solicitudDTO1,solicitudDTO2);

        /*NOPE JUNIT NOPE
        // Convert both streams to lists
        var solicitudDTOList = multiSolicitudDTO.collect().asList().await().indefinitely();
        var resultList = result.collect().asList().await().indefinitely();

        // Compare the lists
        assertEquals(solicitudDTOList, resultList);*/
        Mockito.verify(mongoDbMock).getAll();

    }

    @Test
    @DisplayName("get_all_empty()")
    void getAllTestEmpty(){
        Mockito.when(mongoDbMock.getAll()).thenReturn(Multi.createFrom().items());

        var result = service.listAll();
        result.subscribe()
                .withSubscriber(AssertSubscriber.create(0))
                .awaitCompletion();

        Mockito.verify(mongoDbMock).getAll();
    }

    @Test
    @DisplayName("success_get_by_id()")
    void getByIdTest(){
        var solicitud = new Solicitud();
        var solicitudDT0 = mapper.toDto(solicitud);

        Mockito.when(mongoDbMock.searchById(Mockito.any(String.class))).thenReturn(Uni.createFrom().item(solicitud));

        var result = service.listById(solicitudDT0.getSolicitudId());
        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem()
                .assertItem(solicitudDT0);

        Mockito.verify(mongoDbMock).searchById(Mockito.any(String.class));
    }

    @Test
    @DisplayName("notfound_get_by_id()")
    void getByIdNotFoundTest(){

        Mockito.when(mongoDbMock.searchById(Mockito.any(String.class))).thenReturn(Uni.createFrom().nullItem());

        var result = service.listById("");
        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                        .awaitItem().assertItem(null);

        Mockito.verify(mongoDbMock).searchById(Mockito.any(String.class));
    }

    @Test
    @DisplayName("success_get_by_propietario()")
    void getByPropietarioTest(){
        var tarjeta = new Tarjeta();
        tarjeta.setPropietario("Mishell Yagual");

        var usuario = new Usuario();
        usuario.setTarjeta(tarjeta);

        var solicitud = new Solicitud();
        solicitud.setUsuario(usuario);
        var solicitudDT0 = mapper.toDto(solicitud);

        Mockito.when(mongoDbMock.searchByPropietario(Mockito.any(String.class))).thenReturn(Multi.createFrom().items(solicitud));

        var result = service.listByPropietario(solicitudDT0.getUsuario().getTarjeta().getPropietario());
        result.subscribe()
                .withSubscriber(AssertSubscriber.create(1))
                .awaitCompletion()
                .assertItems(solicitudDT0);

        Mockito.verify(mongoDbMock).searchByPropietario(Mockito.any(String.class));
    }
    @Test
    @DisplayName("success_save_solicitud()")
    void saveSolicitudTest(){

        var solicitud = new Solicitud();
        var solicitudDT0 = mapper.toDto(solicitud);

        Mockito.when(mongoDbMock.add(Mockito.any(Solicitud.class))).thenReturn(Uni.createFrom().item(solicitud));

        var result = service.save(solicitudDT0);
        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem().assertItem(solicitudDT0);

        Mockito.verify(mongoDbMock).add(Mockito.any(Solicitud.class));
    }

    @Test
    @DisplayName("success_edit_solicitud()")
    void editSolicitudTest(){

        var tarjeta = new Tarjeta();
        tarjeta.setPropietario("Mishell Yagual");

        var usuario = new Usuario();
        usuario.setTarjeta(tarjeta);

        var solicitud = new Solicitud();
        solicitud.setUsuario(usuario);

        var solicitudDT0 = mapper.toDto(solicitud);

        Mockito.when(mongoDbMock.modify(Mockito.any(Solicitud.class))).thenReturn(Uni.createFrom().item(solicitud));

        var result = service.update(solicitudDT0);
        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem().assertItem(solicitudDT0);

        Mockito.verify(mongoDbMock).modify(Mockito.any(Solicitud.class));
    }

    @Test
    @DisplayName("success_delete_solicitud()")
    void deleteSolicitudTest(){
        var solicitud = new Solicitud();
        var solicitudDT0 = mapper.toDto(solicitud);

        Mockito.when(mongoDbMock.remove(Mockito.any(String.class))).thenReturn(Uni.createFrom().item(1L));

        var result = service.delete(solicitudDT0.getSolicitudId());
        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem().assertItem(1L);

        Mockito.verify(mongoDbMock).remove(Mockito.any(String.class));
    }

}