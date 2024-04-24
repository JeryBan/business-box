package ban.jery.businessbox.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class GoogleApiController {

    @GetMapping("/login")
    public String apiLogin() {
        return "api-login";
    }

    @GetMapping("/")
    public String reviewsPage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String accessToken = principal.getAttribute("access_token");
        String email = principal.getAttribute("email");

        // apiService.request(email, accessToken

        model.addAttribute("email", email);

        return "reviews-page";
    }
}
