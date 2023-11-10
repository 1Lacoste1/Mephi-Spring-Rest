package ru.hpclab.bd.module1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Entity representing a user.
 * It is automatically registered as a bean in the Spring context due to the {@code @Component} annotation.
 * Lombok annotations are used to generate boilerplate code such as getters, setters, and constructors.
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * The unique identifier of the user.
     */
    @NonNull
    private UUID identifier;

    /**
     * The date of birth of the user.
     */
    @NonNull
    private String dateBirthday;

    /**
     * The full name of the user.
     */
    @NonNull
    private String fio;

    /**
     * The ticket number associated with the user.
     */
    @NonNull
    private int ticketNumber;

    public User(UUID uuid, String name) {
        this.identifier = uuid;
        this.fio = name;
    }
}
