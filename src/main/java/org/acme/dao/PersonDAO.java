package org.acme.dao;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonDAO {

    @Inject
    Jdbi jdbi;

    @CacheResult(cacheName = "default")
    public String getFullName(int id) {
        return jdbi.withHandle(handle -> handle.createQuery(
                                " SELECT concat(firstname, ' ', lastname) FROM person " +
                                        "WHERE id = :id"
                        )
                        .bind("id", id)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }

    @CacheInvalidateAll(cacheName = "default")
    public void updateFirstname(int id, String firstname) {
            jdbi.withHandle(handle -> handle.createUpdate(
                                " UPDATE person SET firstname = :firstname " +
                                        "WHERE id = :id"
                        )
                        .bind("id", id)
                        .bind("firstname", firstname)
                        .execute()
        );
    }
}
