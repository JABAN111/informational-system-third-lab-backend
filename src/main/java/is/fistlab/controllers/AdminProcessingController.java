package is.fistlab.controllers;

import is.fistlab.database.entities.PotentialAdmin;
import is.fistlab.database.repositories.PotentialAdminRepository;
import is.fistlab.services.AdminProcessingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v0/admin/console")
@AllArgsConstructor
public class AdminProcessingController {
    private final AdminProcessingService adminProcessingService;

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<List<PotentialAdmin>>> getAllPotentialAdmin(){
        return ResponseEntity.ok(new Response<>(adminProcessingService.getAllPotentialAdmins()));
    }

}
