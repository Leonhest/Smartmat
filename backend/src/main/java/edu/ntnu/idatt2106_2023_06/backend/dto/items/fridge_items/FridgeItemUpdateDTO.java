package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FridgeItemUpdateDTO(@NonNull Long itemId, @NonNull Long fridgeId,
                                  @Nullable Integer quantity, @Nullable LocalDateTime purchaseDate,
                                  @Nullable LocalDateTime expirationDate) {
}