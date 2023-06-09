package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *  This class represents the REST controller for managing notifications for a user.
 *  It handles requests related to retrieving, updating, and deleting notifications for a user.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * This method returns all notifications for a given user.
     * @return ResponseEntity containing a list of NotificationDTOs representing all notifications for a given user
     */
    @GetMapping("/get")
    @Operation(summary = "Get all notifications for a given user")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Object> getNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotificationsOfSingeUserAsDTO());
    }

    /**
     * This method updates notifications for a given user.
     * @return ResponseEntity indicating that the notifications have been updated
     */
    @GetMapping("/update")
    @Operation(summary = "Update notifications for a given user")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Object> updateNotifications() {
        notificationService.updateAllNotifications();
        return ResponseEntity.ok().build();
    }

    /**
     * This method deletes all notifications of a user.
     *
     * @param fridgeId The ID of the fridge the notifications belong to
     * @return         ResponseEntity indicating that all notifications have been deleted
     */
    @DeleteMapping("/delete/all/{fridgeId}")
    @Operation(summary = "Delete all notifications of a user")
    @ApiResponse(responseCode = "200", description = "OK")
    @Transactional
    public ResponseEntity<Object> deleteAllNotifications(@PathVariable Long fridgeId) {
        notificationService.deleteAllNotificationsForOneUser(fridgeId);
        return ResponseEntity.ok().build();
    }

    /**
     * This method deletes a single notification of a user.
     *
     * @param notificationId The ID of the notification to delete
     * @return               ResponseEntity indicating that the notification has been deleted
     */
    @DeleteMapping("/delete/{notificationId}")
    @Operation(summary = "Delete a single notification of a user")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    /**
     * Set all notifications as read.
     * @return ResponseEntity indicating that all notifications have been set as read
     */
    @PutMapping("/read/all")
    @Operation(summary = "Set all notifications as read")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Object> setNotificationAsRead() {
        notificationService.setAllNotificationsAsRead();
        return ResponseEntity.ok().build();
    }
}