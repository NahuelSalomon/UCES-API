package com.example.UCESAPI.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInsertRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private byte[] image;
}
