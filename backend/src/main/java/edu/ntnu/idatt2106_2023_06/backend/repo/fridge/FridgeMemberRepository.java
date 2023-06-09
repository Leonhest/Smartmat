package edu.ntnu.idatt2106_2023_06.backend.repo.fridge;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMemberId;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *  This repository provides CRUD operations for the RecipeItem entity.
 * <p>
 *  It extends JpaRepository and JpaSpecificationExecutor interfaces.
 * <p>
 *  JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 *  search functionality using specifications.
 *
 * @author Trym Hamer Gudvangen
 */
@Repository
public interface FridgeMemberRepository extends JpaRepository<FridgeMember, FridgeMemberId>, JpaSpecificationExecutor<FridgeMember> {

    /**
     * This method tries to find whether an entry with a given user and fridge exists, by checking the fridge id
     * and the username in the table.
     *
     * @param fridgeId The id of fridge for a given member, given as a Long object.
     * @param username The username of the member,
     * @return         Status whether an entry with the given fridge id and username exists, {@code true}, else {@code false}.
     */
    boolean existsFridgeMemberByFridge_FridgeIdAndUser_Username(Long fridgeId, String username);

    /**
     * This method tries to find whether an entry with a given user and fridge exists, by checking the fridge id
     * and the username in the table, and where the user is a super user.
     *
     * @param fridgeId The id of fridge for a given member, given as a Long object.
     * @param username The username of the member,
     * @return         Status whether an entry with the given fridge id and username exists, {@code true}, else {@code false}.
     */
    boolean existsFridgeMemberByFridge_FridgeIdAndUser_UsernameAndSuperUserIs(Long fridgeId, String username, boolean isSuperUser);

    /**
     * This method retrieves the user attached to a given fridge id and which has a given username.
     * @param fridgeId  The id of the fridge to be checked, given as a Long object.
     * @param username  The username of the person, given as a String
     * @return          The Fridge member with the specified info, given as an Optional object.
     */
    Optional<FridgeMember> findFridgeMemberByFridge_FridgeIdAndUser_Username(Long fridgeId, String username);

    /**
     * This method retrieves all the entries in the fridge member table for a given user.
     * @param username  The username of a user, given as a String
     * @return          List of fridge member entries, given as an Optional.
     */
    Optional<List<FridgeMember>> findFridgeMembersByUser_Username(String username);

    /**
     * This method retrieves all the entries in the fridge member table for a given fridge.
     * @param fridgeId  The id of a given fridge, given as a Long object.
     * @return          List of fridge member entries, given as an Optional.
     */
    Optional<List<FridgeMember>> findFridgeMembersByFridge_FridgeId(Long fridgeId);

    /**
     * This method deletes the user attached to a given fridge id and which has a given username.
     * @param fridgeId  The id of the fridge to be checked, given as a Long object.
     * @param username  The username of the person, given as a String
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Modifying
    @Transactional
    void deleteFridgeMemberByFridge_FridgeIdAndUser_Username(Long fridgeId, String username);

}

