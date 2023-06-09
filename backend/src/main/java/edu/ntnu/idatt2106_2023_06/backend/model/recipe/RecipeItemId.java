package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A class that serves as the id for the RecipeItems entity in the app.
 * It is an Embeddable class that contains two fields, items and recipe.
 *
 * @author Trym Hamer Gudvangen
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeItemId implements Serializable {

    /**
     * The id of the items.
     */
    @Column(name = "item_id")
    private Long item;

    /**
     * The id of the recipe.
     */
    @Column(name = "recipe_part_id")
    private Long recipePart;

}
