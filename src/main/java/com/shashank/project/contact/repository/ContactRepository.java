package com.shashank.project.contact.repository;

import com.shashank.project.db.jooq.tables.pojos.Contact;
import com.shashank.project.db.jooq.tables.daos.ContactDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.shashank.project.db.jooq.Tables.CONTACT;
import static org.jooq.impl.DSL.noCondition;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContactRepository {

    private final DSLContext dslContext;
    private final ContactDao contactDao;

    public void save(Contact contact) {

        contactDao.insert(contact);

    }

    public Contact findById(UUID id) {

        return contactDao.fetchOneById(id);
    }

    public void update(Contact contact) {
        contactDao.update(contact);
    }
}
