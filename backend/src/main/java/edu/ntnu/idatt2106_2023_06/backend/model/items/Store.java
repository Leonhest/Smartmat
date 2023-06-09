package edu.ntnu.idatt2106_2023_06.backend.model.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a store from which the food came from.
 *
 * @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "store")
public class Store {

    /**
     * The unique identifier for this store.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    @JsonIgnore
    private Long storeId;

    /**
     * The name of the store, must be unique and not null
     */
    @Column(name = "store_name", length = 64, nullable = false)
    @NonNull
    private String storeName;

    /**
     * The items from the given store.
     */
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Item> itemsInStore = new ArrayList<>();


}
