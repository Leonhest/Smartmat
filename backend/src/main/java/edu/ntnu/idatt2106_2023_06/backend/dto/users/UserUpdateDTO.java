package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record UserUpdateDTO(@NonNull String username,
                            @Nullable String newUsername,
                            @Nullable String fullName,
                            @Nullable String email,
                            @Nullable Date birthDate,
                            @Nullable String phone,
                            @Nullable byte[] picture) {
}