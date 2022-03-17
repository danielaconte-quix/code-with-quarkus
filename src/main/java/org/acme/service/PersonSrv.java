package org.acme.service;

import org.acme.dao.PersonDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonSrv {

    @Inject
    PersonDAO personDAO;

    public String getFullName(int id) {
        return personDAO.getFullName(id);
    }

    public void updateFirstname(int id, String firstname) {
        personDAO.updateFirstname(id, firstname);
    }
}
