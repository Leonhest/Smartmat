package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.notification.NotificationService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 This class represents the REST controller for managing items in a fridge and shopping list.
 It handles requests related to adding, retrieving, and deleting items from a fridge and a shopping list.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/item/fridge")
@RequiredArgsConstructor
public class FridgeItemsController implements IFridgeItemsController{

    private final ItemService itemService;
    private final UserService userService;
    private final NotificationService notificationService;

    /**
     * The logger for logging information about the operations performed by this controller.
     */
    private final Logger logger = LoggerFactory.getLogger(FridgeItemsController.class);

    /**
     * Adds an item to the fridge.
     *
     * @param itemDTO The item to add to the fridge.
     * @param fridgeId The id of the fridge to add the item to.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping(value="/add")
    @Operation(summary = "Add items to fridge")
    @Override
    public ResponseEntity<Object> addToFridge(@ParameterObject @RequestBody ItemDTO itemDTO,
                                              @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){

        logger.info("item to add: " + itemDTO);
        logger.info("fridge to be added in: " + fridgeId);
        itemService.addToFridge(itemDTO, fridgeId);
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    /**
     * This method updates and ultimately removes an item from a fridge based on amount of quantity in DTO.
     *
     * @param itemRemoveDTO The item to remove from the fridge.
     * @return              A ResponseEntity indicating the success or failure of the operation.
     */
    @DeleteMapping(value="/delete")
    @Operation(summary = "Delete item from fridge")
    @Override
    public ResponseEntity<Object> deleteItemFromFridge(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO){
        logger.info(String.valueOf(itemRemoveDTO));
        logger.info("User wants to remove a certain amount of an item from fridge");
        itemService.removeItemFromFridge(itemRemoveDTO);
        logger.info("Items have been removed!");
        return ResponseEntity.ok().build();
    }

    /**
     * This method updates a given fridge item to contain the information received.
     * @param fridgeItemUpdateDTO   New fridge item information, given as a FridgeItemUpdateDTO
     * @param authentication        The authentication of the user who sent in the request, given as an Authentication object.
     * @return                      A ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping(value="/update")
    @Operation(summary = "Update item in fridge")
    @Override
    public ResponseEntity<Object> updateFridgeItem(@ParameterObject @RequestBody FridgeItemUpdateDTO fridgeItemUpdateDTO,
                                                   Authentication authentication) {
        validateSuperUser(fridgeItemUpdateDTO.fridgeId(), authentication);
        logger.info("Attempting to update fridge item");
        itemService.updateFridgeItem(fridgeItemUpdateDTO);
        logger.info("Successfully updated item");

        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves items from a fridge.
     *
     * @param fridgeId The id of the fridge to retrieve items from.
     * @return A ResponseEntity containing the retrieved items, or indicating a failure if appropriate.
     */
    @GetMapping(value="/get")
    @Operation(summary = "Get items from fridge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given fridge",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class)) })}
    )
    @Override
    public ResponseEntity<Object> getFridge(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from fridge");
        List<FridgeItemLoadDTO> itemList = itemService.getFridgeItems(fridgeId);
        //TODO: use FridgeItemLoadDTO
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    /**
     * Search and filter items from a fridge.
     *
     * @param fridgeItemSearchDTO The search request containing the search parameters.
     */
    @PostMapping(value="/search")
    @Operation(summary = "Search items from fridge")
    public ResponseEntity<Object> searchFridgeItems(@RequestBody FridgeItemSearchDTO fridgeItemSearchDTO) {
        logger.info("User wants to search items from fridge");
        Page<FridgeItemLoadDTO> itemList = itemService.searchFridgeItems(fridgeItemSearchDTO);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }


    /**
     *  This method authenticates the given authentication object by checking if it is null or not authenticated.
     *  If the authentication is null or not authenticated, an UnauthorizedException is thrown.
     *  @param authentication           The authentication object to authenticate
     *  @throws UnauthorizedException   if the authentication is null or not authenticated
     */
    private void authenticate(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");
    }

    /**
     * This method validates whether the authenticated user is a superuser for the given fridge ID.
     * The method first authenticates the user using the provided authentication object.
     * It then checks if the authenticated user is a superuser for the given fridge ID using the userService.
     * If the user is not a superuser, an UnauthorizedException is thrown.
     *
     * @param fridgeId the ID of the fridge to check for superuser access
     * @param authentication the authentication object of the user
     * @throws UnauthorizedException if the authentication is null or not authenticated,
     *                              or if the authenticated user is not a superuser for the given fridge ID
     */
    private void validateSuperUser(Long fridgeId, Authentication authentication) {
        authenticate(authentication);

        boolean isSuperUser = userService.isSuperUser(fridgeId, authentication.getName());
        if(!isSuperUser) throw new UnauthorizedException(authentication.getName(), "User must be super user");
    }

}
