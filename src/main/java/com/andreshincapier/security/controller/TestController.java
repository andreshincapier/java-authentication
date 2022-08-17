package com.andreshincapier.security.controller;

import com.andreshincapier.security.auth.MyUserDetailsService;
import com.andreshincapier.security.model.AuthenticationRequest;
import com.andreshincapier.security.model.AuthenticationResponse;
import com.andreshincapier.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;


    @RequestMapping(path = "/hello")
    public String testController() {
        return "Hello from controller";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) throws Exception {
        Authentication context = SecurityContextHolder.getContext().getAuthentication();

        log.info("USER {}", context.getPrincipal());
        log.info("PERMS {}", context.getAuthorities());
        log.info("IS LOG {}", context.isAuthenticated());

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password");
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().jwt(jwt).build());
    }


}
