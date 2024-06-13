package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.accountSettings.AccSettingsUpdateDTO;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.service.accSettings.IAccSettingsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/settings")
public class AccSettingsRestController {

    private final IAccSettingsService accSettingsService;

    public AccSettingsRestController(IAccSettingsService accSettingsService) {
        this.accSettingsService = accSettingsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AccountSettings> getAccSettings(@PathVariable("userId") Long userId) {
        AccountSettings accountSettings;

        try {
            accountSettings = accSettingsService.getAccSettings(userId);
            return ResponseEntity.ok(accountSettings);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AccountSettings> updateAccSettings(@PathVariable("userId") Long userId,
                                                             @RequestBody AccSettingsUpdateDTO dto) {
        AccountSettings accountSettings;

        try {
            accountSettings = accSettingsService.updateSettings(userId, dto);
            return ResponseEntity.ok(accountSettings);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
