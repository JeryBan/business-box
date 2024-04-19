package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.user.UserInsertDTO;
import ban.jery.businessbox.dto.user.UserMapper;
import ban.jery.businessbox.dto.user.UserRoDTO;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.security.JwtService;
import ban.jery.businessbox.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor // Autowired
public class UserRestController {

    private final IUserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<UserRoDTO>> getAllUsers() {
        List<User> users;
        List<UserRoDTO> roUsers = new ArrayList<>();

        try {
            users = userService.getAllUsers();
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
            roUser = UserMapper.mapToRoUser(userService.findUserByEmail(email));
            responseBody.put("email", roUser.getEmail());

            return ResponseEntity.ok(responseBody);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        try {
            User user = userService.deleteUser(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            User user = userService.insertUser(dto);

//            UserRoDTO roUser = UserMapper.mapToRoUser(user);
//            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                    .path("id")
//                    .buildAndExpand(roUser.getId())
//                    .toUri();

            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(Collections.singletonMap("access_token", jwtToken));

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
           authManager.authenticate(
                   new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
           );

           User user = userService.findUserByEmail(loginDTO.getEmail());
           String jwtToken = jwtService.generateToken(user);

           return ResponseEntity.ok(Collections.singletonMap("access_token", jwtToken));

       } catch (AuthenticationException e) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
       }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody String token) {
        jwtService.invalidateToken(token);
        return ResponseEntity.ok("logout successful");
    }
}
