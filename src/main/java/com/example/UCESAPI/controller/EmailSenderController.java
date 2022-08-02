package com.example.UCESAPI.controller;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.model.ConfirmEmailRequest;
import com.example.UCESAPI.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/sendEmail")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;
    private final JWTUtils jwtUtils;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService, JWTUtils jwtUtils) {

        this.emailSenderService = emailSenderService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/confirmEmail")
    public ResponseEntity<Object> confirmEmail(@RequestBody ConfirmEmailRequest request) {
        final String jwt = jwtUtils.generateTokenByEmail(request.getEmail());
        emailSenderService.sendEmail(request.getEmail(),
                                      "Confirmación email Secu","Para confirmar el mail, " +
                                             "click en el siguiente link " +
                                              request.getUrlToRedirect()+"?token="+jwt);
        return ResponseEntity.ok().build();
    }

}
