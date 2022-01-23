package com.example.sweater.controller;

import com.example.sweater.domain.UserAndDetails;
import com.example.sweater.domain.dto.CaptchaResponseDto;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Controller
public class SignupController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secter=%s&response";
    //@Autowired private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Value("${recaptcha.secret}")
    String secret;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("/signup")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            UserAndDetails user,
            BindingResult bindingResult,
            Model//Map<String, Object>
                    model) {
        String url = String.format(CAPTCHA_URL, secret, recaptchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!response.isSuccess()){
            model.addAttribute("captchaError","Fill captcha");
        }
        boolean isConfirmEmpty = !StringUtils.hasText(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error","password confirmation cannot be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(//user.getPassword2()
                                                                        passwordConfirm)) {
            model.addAttribute("passwordError", "Password are different!");
        }
        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors((bindingResult));
            model.mergeAttributes(errors);
            return "signup";
        }
//        UserAndDetails userFromDb = userRepo.findByUsername(user.getUsername());
        if (!userService.addUser(user)){
            model//.put
                    .addAttribute("message", "User exists!");
            return "signup";
        }
//        user.setActive(true);
//        user.setRoles(Collections.singleton(RoleAndGrantedAuthority.USER));
//        userRepo.save(user);
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated=userService.activateUser(code);
        if (isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation cod is not found !");
        }
        return "login";
    }
}
