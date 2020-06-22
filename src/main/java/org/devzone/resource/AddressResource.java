package org.devzone.resource;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import org.devzone.Address;
import org.jboss.resteasy.annotations.SseElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/postalcodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

    private static final Logger logger = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private PgPool client;

    @GET
    @Path("/complete")
    public Multi<Address> get() {
        logger.info("Try to find postalcodes");
        return Address.findAll(client);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Address> getAllAtOnce(@org.jboss.resteasy.annotations.jaxrs.PathParam int count, @org.jboss.resteasy.annotations.jaxrs.PathParam String name) {
        return Address.findAll(client);
    }

    @GET
    @Path("/{postalcode}")
    public Multi<Address> getSingle(@PathParam(value = "postalcode") String postalcode) {
        logger.info("Try to find address for postalcode {}", postalcode);
        return Address.findByPostalcode(client, postalcode);
    }
}
