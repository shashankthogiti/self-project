package com.shashank.project.user;

import com.shashank.project.common.EntityNotFoundException;
import com.shashank.project.user.dto.PaginationRequest;
import com.shashank.project.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

/**
 * Repository for User entity operations using JOOQ.
 * Note: This uses raw SQL until JOOQ classes are generated.
 * After running ./gradlew jooqGen, you can use the generated User table class.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    private static final String USER_TABLE = "\"user\"";

    public List<UserDTO> findAll(PaginationRequest request) {
        int offset = (request.getPage() - 1) * request.getPageSize();

        Condition condition = buildSearchCondition(request);

        return dsl.select(
                        field("id", UUID.class),
                        field("name", String.class),
                        field("email", String.class),
                        field("mobile_number", String.class),
                        field("is_active", Boolean.class),
                        field("created_on", OffsetDateTime.class),
                        field("updated_on", OffsetDateTime.class),
                        field("created_by", UUID.class),
                        field("updated_by", UUID.class)
                )
                .from(table(USER_TABLE))
                .where(condition)
                .orderBy(field("created_on").desc())
                .limit(request.getPageSize())
                .offset(offset)
                .fetchInto(UserDTO.class);
    }

    public Integer count(PaginationRequest request) {
        Condition condition = buildSearchCondition(request);
        return dsl.selectCount()
                .from(table(USER_TABLE))
                .where(condition)
                .fetchOne(0, Integer.class);
    }

    public Optional<UserDTO> findById(UUID id) {
        return dsl.select(
                        field("id", UUID.class),
                        field("name", String.class),
                        field("email", String.class),
                        field("mobile_number", String.class),
                        field("is_active", Boolean.class),
                        field("created_on", OffsetDateTime.class),
                        field("updated_on", OffsetDateTime.class),
                        field("created_by", UUID.class),
                        field("updated_by", UUID.class)
                )
                .from(table(USER_TABLE))
                .where(field("id", UUID.class).eq(id))
                .fetchOptionalInto(UserDTO.class);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return dsl.select(
                        field("id", UUID.class),
                        field("name", String.class),
                        field("email", String.class),
                        field("mobile_number", String.class),
                        field("is_active", Boolean.class),
                        field("created_on", OffsetDateTime.class),
                        field("updated_on", OffsetDateTime.class),
                        field("created_by", UUID.class),
                        field("updated_by", UUID.class)
                )
                .from(table(USER_TABLE))
                .where(field("email", String.class).eq(email))
                .fetchOptionalInto(UserDTO.class);
    }

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(table(USER_TABLE))
                        .where(field("email", String.class).eq(email))
        );
    }

    public UserDTO insert(String name, String email, String password, String mobileNumber) {
        UUID id = UUID.randomUUID();
        dsl.insertInto(table(USER_TABLE))
                .columns(
                        field("id", UUID.class),
                        field("name", String.class),
                        field("email", String.class),
                        field("password", String.class),
                        field("mobile_number", String.class),
                        field("is_active", Boolean.class),
                        field("created_on", OffsetDateTime.class)
                )
                .values(id, name, email, password, mobileNumber, true, OffsetDateTime.now())
                .execute();

        return findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    public UserDTO update(UUID id, String name, String email, String mobileNumber, Boolean isActive) {
        dsl.update(table(USER_TABLE))
                .set(field("name", String.class), name)
                .set(field("email", String.class), email)
                .set(field("mobile_number", String.class), mobileNumber)
                .set(field("is_active", Boolean.class), isActive)
                .set(field("updated_on", OffsetDateTime.class), OffsetDateTime.now())
                .where(field("id", UUID.class).eq(id))
                .execute();

        return findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(table(USER_TABLE))
                .where(field("id", UUID.class).eq(id))
                .execute();
    }

    private Condition buildSearchCondition(PaginationRequest request) {
        Condition condition = DSL.noCondition();
        if (request.getNameFilter() != null && !request.getNameFilter().isBlank()) {
            condition = condition.and(field("name", String.class).containsIgnoreCase(request.getNameFilter()));
        }
        if (request.getEmailFilter() != null && !request.getEmailFilter().isBlank()) {
            condition = condition.and(field("email", String.class).containsIgnoreCase(request.getEmailFilter()));
        }
        return condition;
    }
}

