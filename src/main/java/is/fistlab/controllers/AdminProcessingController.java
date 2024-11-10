package is.fistlab.controllers;

import is.fistlab.database.entities.PotentialAdmin;
import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.PotentialAdminRepository;
import is.fistlab.services.AdminProcessingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/admin/console")
@AllArgsConstructor
public class AdminProcessingController {
    private final AdminProcessingService adminProcessingService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<Response<List<PotentialAdmin>>> getAllPotentialAdmin() {
        return ResponseEntity.ok(new Response<>(adminProcessingService.getAllPotentialAdmins()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/approve/{id}")
    public ResponseEntity<Response<User>> approveAdminRole(@PathVariable Long id, @RequestBody PotentialAdmin potentialAdmin) {
        var newAdmin = adminProcessingService.giveAdminRoleToUser(id, potentialAdmin.getUser());
         return ResponseEntity.ok(
                 new Response<>("Пользователю " + potentialAdmin.getUser().getUsername() + " успешна выдана роль админа",
                         potentialAdmin.getUser()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reject")
    public ResponseEntity<Response<User>> rejectAdminRole(@RequestBody PotentialAdmin potentialAdmin) {
        adminProcessingService.rejectUserForAdminRole(potentialAdmin.getUser());
        return ResponseEntity.ok(new Response<>("Пользователь " + potentialAdmin.getUser().getUsername()
                + " успешно получил отказ"));
    }

}
