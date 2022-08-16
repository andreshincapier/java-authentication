package com.bolsadeideas.springboot.backend.apirest.models.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    private String username;
    private String password;

}
