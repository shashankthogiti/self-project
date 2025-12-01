package com.shashank.project.table.repository;

import com.shashank.project.db.jooq.tables.pojos.Table;
import com.shashank.project.db.jooq.tables.daos.TableDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TableRepository {

    private final TableDao tableDao;

    public void save(Table table) {
        tableDao.insert(table);
    }

    public Table getById(UUID id){
        return tableDao.fetchOneById(id);
    }

    public void update(Table table){
        tableDao.insert(table);
    }

}
