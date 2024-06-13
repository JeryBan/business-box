package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.accountSettings.AccSettingsDTO;
import ban.jery.businessbox.dto.accountSettings.AccSettingsMapper;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.service.accSettings.IAccSettingsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/settings")
public class AccSettingsRestController {

    private final IAccSettingsService accSettingsService;

    public AccSettingsRestController(IAccSettingsService accSettingsService) {
        this.accSettingsService = accSettingsService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<AccSettingsDTO> getAccSettings(@PathVariable("email") String email) {
        AccSettingsDTO accountSettings;

        try {
            accountSettings = AccSettingsMapper.mapToAccSettingsDTO(
                    accSettingsService.getAccSettings(email)
            );
            return ResponseEntity.ok(accountSettings);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<AccSettingsDTO> updateAccSettings(@PathVariable("email") String email,
                                                             @RequestBody AccSettingsDTO dto) {
        AccSettingsDTO accountSettings;

        try {
            accountSettings = AccSettingsMapper.mapToAccSettingsDTO(
                    accSettingsService.updateSettings(email, dto)
            );
            return ResponseEntity.ok(accountSettings);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
