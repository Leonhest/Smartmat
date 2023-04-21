package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * A data transfer object representing a user's basic information, used for loading user data.
 *
 * @param username  Username of the user, given as a String
 * @param firstName First name of the user, given as a String
 * @param lastName  Last name of the user, given as a String
 * @param email     Email of the user, given as a String
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@Builder
public record UserLoadDTO(@NonNull String username, @NonNull String firstName,
                          @NonNull String lastName, @NonNull String email) {
}
