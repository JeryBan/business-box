package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.user.UserInsertDTO;
import ban.jery.businessbox.dto.user.UserMapper;
import ban.jery.businessbox.dto.user.UserRoDTO;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final IUserService service;
    private final AuthenticationManager authManager;

    @Autowired
    public UserRestController(IUserService service, AuthenticationManager authManager) {
        this.service = service;
        this.authManager = authManager;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserRoDTO>> getAllUsers() {
        List<User> users;
        List<UserRoDTO> roUsers = new ArrayList<>();

        try {
            users = service.getAllUsers();
            for (User user : users) {
                roUsers.add(UserMapper.mapToRoUser(user));
            }

            return ResponseEntity.ok(roUsers);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Map<String, String>> findUserByEmail(@PathVariable("email") String email) {
        UserRoDTO roUser;
        Map<String, String> responseBody = new HashMap<>();

        try {
            roUser = UserMapper.mapToRoUser(service.findUserByEmail(email));
            responseBody.put("email", roUser.getEmail());

            return ResponseEntity.ok(responseBody);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserInsertDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            User user = service.insertUser(dto);
            UserRoDTO roUser = UserMapper.mapToRoUser(user);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("id")
                    .buildAndExpand(roUser.getId())
                    .toUri();

            return ResponseEntity.created(location).body(roUser);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserInsertDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

       try {

           Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
           SecurityContextHolder.getContext().setAuthentication(authentication);

           return ResponseEntity.ok("Login successful.");

       } catch (AuthenticationException e) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
    }
}
