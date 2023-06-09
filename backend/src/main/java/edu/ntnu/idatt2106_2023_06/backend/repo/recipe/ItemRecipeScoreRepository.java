package edu.ntnu.idatt2106_2023_06.backend.repo.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRecipeScoreRepository extends JpaRepository<ItemRecipeScore, ItemRecipeScoreId>, JpaSpecificationExecutor<ItemRecipeScore> {

    /**
     * This method determines whether a given ItemRecipeScore is made, by checking the item id and recipe id.
     * @param itemId        ID of item to be checked, given as a Long object.
     * @param recipeId      ID of recipe to be checked, given as a Long object.
     * @return              Status whether the item recipe score exists or not.
     */
    boolean existsItemRecipeScoreByItem_ItemIdAndRecipe_RecipeId(Long itemId, Long recipeId);

    /**
     * This method retrieves the ItemRecipeScore based on the item id and recipe id provided.
     * @param itemId        ID of the item, given as a Long.
     * @param recipeId      ID of the recipe, given as a Long.
     * @return              Optional containing the item recipe score entity.
     */
    Optional<ItemRecipeScore> findItemRecipeScoreByItem_ItemIdAndRecipe_RecipeId(Long itemId, Long recipeId);

    /**
     * This method retrieves a list of Item Recipe Score entities for a given item.
     * @param itemId    ID of the item, given as a Long.
     * @return          An optional containing the list of ItemRecipeScores.
     */
    Optional<List<ItemRecipeScore>> findByItem_ItemId(Long itemId);

}
