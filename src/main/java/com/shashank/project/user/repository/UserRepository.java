package com.shashank.project.user.repository;

import com.shashank.project.db.jooq.tables.daos.UserDao;
import com.shashank.project.db.jooq.tables.pojos.User;
import com.shashank.project.user.dto.request.GetAllUsersRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.shashank.project.db.jooq.Tables.USER;
import static org.jooq.impl.DSL.noCondition;

/**
 * Repository for User entity database operations.
 * <p>
 * This repository uses JOOQ DAO for basic CRUD operations and DSLContext
 * for custom queries with pagination and filtering.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final UserDao userDao;
    private final DSLContext dslContext;

    // ========================================
    // CRUD OPERATIONS (using DAO)
    // ========================================

    /**
     * Saves a new user to the database.
     *
     * @param user The User entity to save
     */
    public void save(User user) {
        log.debug("Saving user with ID: {}", user.getId());
        userDao.insert(user);
        log.info("Successfully saved user with ID: {}", user.getId());
    }

    /**
     * Fetches a user by their ID.
     *
     * @param id The user ID
     * @return Optional containing the User entity, or empty if not found
     */
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        log.debug("Fetching user by ID: {}", id);
        return userDao.fetchOptionalById(id);
    }

    /**
     * Fetches a user by their email.
     *
     * @param email The user email
     * @return Optional containing the User entity, or empty if not found
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        return userDao.fetchOptionalByEmail(email);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The User entity to update
     */
    public void update(User user) {
        log.debug("Updating user with ID: {}", user.getId());
        userDao.update(user);
        log.info("Successfully updated user with ID: {}", user.getId());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The user ID to delete
     */
    public void deleteById(UUID id) {
        log.debug("Deleting user with ID: {}", id);
        userDao.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }

    /**
     * Checks if a user exists with the given email.
     *
     * @param email The email to check
     * @return true if a user exists with this email, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.debug("Checking if user exists with email: {}", email);
        return userDao.fetchOptionalByEmail(email).isPresent();
    }

    // ========================================
    // PAGINATION OPERATIONS (using DSLContext)
    // ========================================

    /**
     * Finds all users matching the given criteria with pagination.
     *
     * @param request The request containing pagination and filter criteria
     * @return List of matching users
     */
    @Transactional(readOnly = true)
    public List<User> findAll(GetAllUsersRequestDTO request) {
        log.debug("Finding users with page: {}, pageSize: {}", request.getPage(), request.getPageSize());

        Condition condition = buildFilterConditions(request);
        SortField<?> sortField = buildSortField(request.getSortDirection());
        int offset = (request.getPage() - 1) * request.getPageSize();

        List<User> results = dslContext.select()
                .from(USER)
                .where(condition)
                .orderBy(sortField)
                .limit(request.getPageSize())
                .offset(offset)
                .fetchInto(User.class);

        log.debug("Found {} users on page {}", results.size(), request.getPage());
        return results;
    }

    /**
     * Counts total number of users matching the filter criteria.
     *
     * @param request The request containing filter criteria
     * @return Total count of users matching the criteria
     */
    @Transactional(readOnly = true)
    public int count(GetAllUsersRequestDTO request) {
        log.debug("Counting users with filters");

        Condition condition = buildFilterConditions(request);

        Integer count = dslContext.selectCount()
                .from(USER)
                .where(condition)
                .fetchOne(0, Integer.class);

        int result = count != null ? count : 0;
        log.debug("Found {} total users", result);
        return result;
    }

    // ========================================
    // PRIVATE HELPER METHODS
    // ========================================

    private Condition buildFilterConditions(GetAllUsersRequestDTO request) {
        Condition condition = noCondition();

        if (request.getNameFilter() != null && !request.getNameFilter().isBlank()) {
            condition = condition.and(USER.NAME.containsIgnoreCase(request.getNameFilter()));
        }
        if (request.getEmailFilter() != null && !request.getEmailFilter().isBlank()) {
            condition = condition.and(USER.EMAIL.containsIgnoreCase(request.getEmailFilter()));
        }

        return condition;
    }

    private SortField<?> buildSortField(GetAllUsersRequestDTO.SortDirection sortDirection) {
        return sortDirection == GetAllUsersRequestDTO.SortDirection.DESC
                ? USER.CREATED_ON.desc()
                : USER.CREATED_ON.asc();
    }
}

