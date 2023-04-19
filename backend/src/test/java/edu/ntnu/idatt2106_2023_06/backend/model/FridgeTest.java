package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    @Nested
    class Fridge_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge(1L, "Fridge", new HashSet<>(), new ArrayList<>(), new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_args_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge("Fridge");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Fridge fridge = Fridge
                        .builder()
                        .fridgeId(1L)
                        .fridgeName("Fridge")
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Fridge_can_properly_get {
        Fridge getFridge() {
            return new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        }

        @Test
        void id() {
            Fridge fridge = getFridge();
            Long expectedId = 1L;
            Long actualId = fridge.getFridgeId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void name_of_fridge() {
            Fridge fridge = getFridge();
            String expectedName = "Norman family";

            String actualName = fridge.getFridgeName();

            assertEquals(expectedName, actualName);
        }

        @Test
        void members() {
            Fridge fridge = getFridge();
            Set<FridgeMember> expectedFridgeMembers = new HashSet<>();

            Set<FridgeMember> actualFridgeMembers = fridge.getMembers();

            assertEquals(expectedFridgeMembers, actualFridgeMembers);
        }

        @Test
        void shopping_items() {
            Fridge fridge = getFridge();
            List<ShoppingItems> expectedItems = new ArrayList<>();

            List<ShoppingItems> actualItems = fridge.getShoppingItems();

            assertEquals(expectedItems, actualItems);
        }

        @Test
        void fridge_items() {
            Fridge fridge = getFridge();
            List<FridgeItems> expectedItems = new ArrayList<>();

            List<FridgeItems> actualItems = fridge.getFridgeItems();

            assertEquals(expectedItems, actualItems);
        }


    }

    @Nested
    class Fridge_can_properly_set {
        Fridge getFridge() {
            return new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(), new ArrayList<>());
        }

        @Test
        void fridge_name() {
            Fridge fridge = getFridge();
            String expectedName = "Norman family";

            fridge.setFridgeName(expectedName);
            String actualFridgeName = fridge.getFridgeName();

            assertEquals(expectedName, actualFridgeName);
        }

        @Test
        void members() {
            Fridge fridge = getFridge();
            Set<FridgeMember> expectedFridgeMembers = new HashSet<>();


            fridge.setMembers(expectedFridgeMembers);
            Set<FridgeMember> actualFridgeMembers = fridge.getMembers();

            assertEquals(expectedFridgeMembers, actualFridgeMembers);
        }

        @Test
        void shopping_items_name() {
            Fridge fridge = getFridge();
            List<ShoppingItems> expectedItems = new ArrayList<>();

            fridge.setShoppingItems(expectedItems);
            List<ShoppingItems> actualShoppingItems = fridge.getShoppingItems();

            assertEquals(expectedItems, actualShoppingItems);
        }

        @Test
        void fridge_items_name() {
            Fridge fridge = getFridge();
            List<FridgeItems> expectedItems = new ArrayList<>();

            fridge.setFridgeItems(expectedItems);
            List<FridgeItems> actualFridgeItems = fridge.getFridgeItems();

            assertEquals(expectedItems, actualFridgeItems);
        }

    }

}