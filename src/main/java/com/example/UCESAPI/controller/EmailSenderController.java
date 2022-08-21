package com.example.UCESAPI.controller;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.model.SendEmailRequest;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.EmailSenderService;
import com.example.UCESAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/sendEmail")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService, UserService userService, JWTUtils jwtUtils) {

        this.emailSenderService = emailSenderService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/confirmEmail")
    public ResponseEntity<Object> confirmEmail(@RequestBody SendEmailRequest request) {
        User user = userService.getByEmail(request.getEmail());
        if(user != null)
        {
            final String jwt = jwtUtils.generateTokenByEmail(request.getEmail());
            emailSenderService.sendEmail(request.getEmail(),
                    "Confirmación email Secu","Para confirmar el mail, " +
                            "click en el siguiente link " +
                            request.getUrlToRedirect()+"?token="+jwt);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody SendEmailRequest request) {
        User user = userService.getByEmail(request.getEmail());
        if(user != null)
        {
            final String jwt = jwtUtils.generateTokenByEmail(request.getEmail());
            emailSenderService.sendEmail(request.getEmail(),
                    "Cambio de contraseña","Para cambiar la contraseña, " +
                            "click en el siguiente link " +
                            request.getUrlToRedirect()+"?token="+jwt);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

}
