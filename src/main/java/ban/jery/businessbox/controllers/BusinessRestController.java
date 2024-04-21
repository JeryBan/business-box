package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.business.BusinessDeleteDTO;
import ban.jery.businessbox.dto.business.BusinessInsertDTO;
import ban.jery.businessbox.dto.business.BusinessMapper;
import ban.jery.businessbox.dto.business.BusinessRoDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.service.business.IBusinessService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/business")
@AllArgsConstructor
public class BusinessRestController {

    private final IBusinessService businessService;

    @GetMapping("/{email}")
    public ResponseEntity<List<BusinessRoDTO>> getUserBusinesses(@PathVariable("email") String email) {
        List<Business> businesses;
        List<BusinessRoDTO> roBusinesses = new ArrayList<>();

        try {
            businesses = businessService.getUserBusiness(email);
            for (Business business : businesses) {
                roBusinesses.add(BusinessMapper.mapToRoBusiness(business));
            }

            return ResponseEntity.ok(roBusinesses);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addBusinessToUser(@Valid @RequestBody BusinessInsertDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Business business = businessService.insertBusinessToUser(dto);
            BusinessRoDTO roBusiness = BusinessMapper.mapToRoBusiness(business);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(roBusiness.getId())
                    .toUri();

            return ResponseEntity.created(location).body(roBusiness);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteBusinessFromUser(@Valid @RequestBody BusinessDeleteDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Business business = businessService.removeBusinessFromUser(dto);
            BusinessRoDTO roBusiness = BusinessMapper.mapToRoBusiness(business);

            return ResponseEntity.ok(roBusiness);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
