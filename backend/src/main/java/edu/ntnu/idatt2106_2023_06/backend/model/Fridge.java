package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

/**
 * Fridge class represents a common storage of food items in the application. It can, therefore, include dry, frozen, or
 * wet items. It resembles more as a common gathering of people and food than a literal fridge.
 *
 *  @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "fridge")
public class Fridge {

    /**
     * The unique identifier for the fridge
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private Long fridgeId;

    /**
     * The name of the fridge, must be unique and not null
     */
    @Column(name = "fridge_name", length = 64, nullable = false)
    @NonNull
    private String fridgeName;

    /**
     * The members of the fridge.
     */
    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<FridgeMember> members = new HashSet<>();

    /**
     * The members of the fridge.
     */
    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<ShoppingItems> shoppingItems = new ArrayList<>();

    /**
     * The members of the fridge.
     */
    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<FridgeItems> fridgeItems = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fridge fridge)) return false;

        return Objects.equals(fridgeId, fridge.fridgeId);
    }

    @Override
    public int hashCode() {
        int result = fridgeId != null ? fridgeId.hashCode() : 0;
        result = 31 * result + fridgeName.hashCode();
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (shoppingItems != null ? shoppingItems.hashCode() : 0);
        result = 31 * result + (fridgeItems != null ? fridgeItems.hashCode() : 0);
        return result;
    }
}
