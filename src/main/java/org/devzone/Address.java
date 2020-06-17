package org.devzone;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.StreamSupport;

public class Address {

    private static final Logger logger = LoggerFactory.getLogger(Address.class);

    private final String postalCode;
    private final String locality;
    private final String state;

    public Address(String postalCode, String locality, String state) {
        this.postalCode = postalCode;
        this.locality = locality;
        this.state = state;
    }

    public String getLocality() {
        return locality;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    private static Address from(Row row) {
        return new Address(row.getString("postalcode"), row.getString("locality"), row.getString("state"));
    }

    public static Multi<Address> findAll(PgPool client) {
        logger.info("Looking for address with postalcodes");
        return client.query("SELECT postalcode , locality, state FROM postalcode ORDER BY locality ASC, state ASC")
                .execute()
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                .onItem().apply(Address::from);
    }

    public static Multi<Address> findByPostalcode(PgPool client, String postalcode) {
        logger.info("Looking for address with postalcode {}", postalcode);
        return client.preparedQuery("SELECT postalcode , locality, state FROM postalcode WHERE postalcode = $1")
                .execute(Tuple.of(postalcode))
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                .onItem().apply(Address::from);
    }
}
