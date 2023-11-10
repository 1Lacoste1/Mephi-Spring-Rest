package ru.hpclab.bd.module1.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import ru.hpclab.bd.module1.controller.exeption.UserException;
import ru.hpclab.bd.module1.model.User;

import java.util.*;

import static java.lang.String.format;

/**
 * Repository to access Users.
 */
@Repository
public class UserRepository {
    /**
     * User exception not found message.
     */
    public static final String USER_NOT_FOUND_MSG = "User with ID %s not found";

    public static final String USERS_NOT_FOUND_MSG = "Users not found";

    /**
     * User already exists message.
     */
    public static final String USER_EXISTS_MSG = "User with ID %s is already exists";

    private final Map<UUID, User> users = new HashMap<>();

    /**
     * Gets a random user from the repository.
     * @return a randomly selected User
     * @throws IllegalStateException if no users are found in the repository
     */
    public User getRandomUser() {
        if (users.isEmpty()) {
            throw new IllegalStateException(USERS_NOT_FOUND_MSG);
        }
        Random random = new Random();
        List<UUID> keys = new ArrayList<>(users.keySet());
        UUID randomKey = keys.get(random.nextInt(keys.size()));
        return users.get(randomKey);
    }

    /**
     * Fetches all users.
     * @return list of users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Fetches user by id.
     * @param id user id
     * @return user
     */
    public User findById(final UUID id) {
        final var user = users.get(id);
        if (user == null) {
            throw new UserException(format(USER_NOT_FOUND_MSG, id));
        }
        return user;
    }

    /**
     * Deletes user by id.
     * @param id user id
     */
    public void delete(final UUID id) {
        final var removed = users.remove(id);
        if (removed == null) {
            throw new UserException(format(USER_NOT_FOUND_MSG, id));
        }
    }

    /**
     * Creates or updates user.
     * @param user user to save
     * @return saved user
     */
    public User save(final User user) {
        if (ObjectUtils.isEmpty(user.getIdentifier())) {
            user.setIdentifier(UUID.randomUUID());
        }

        final var userData = users.get(user.getIdentifier());
        if (userData != null) {
            throw new UserException(format(USER_EXISTS_MSG, user.getIdentifier()));
        }

        users.put(user.getIdentifier(), user);

        return user;
    }

    /**
     * Updates user.
     * @param user user to update
     * @return updated user
     */
    public User put(final User user) {
        final var userData = users.get(user.getIdentifier());
        if (userData == null) {
            throw new UserException(format(USER_NOT_FOUND_MSG, user.getIdentifier()));
        }

        final var removed = users.remove(user.getIdentifier());
        if (removed != null) {
            users.put(user.getIdentifier(), user);
        } else {
            throw new UserException(format(USER_NOT_FOUND_MSG, user.getIdentifier()));
        }

        return user;
    }

    /**
     * Clears all users from the repository.
     */
    public void clear() {
        users.clear();
    }
}
