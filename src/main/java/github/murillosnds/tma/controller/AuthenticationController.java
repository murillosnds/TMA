package github.murillosnds.tma.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import github.murillosnds.tma.dto.LoginDTO;
import github.murillosnds.tma.dto.TokenDTO;
import github.murillosnds.tma.service.JwtService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Login successful",
                 content = @Content(schema = @Schema(implementation = TokenDTO.class))),
    @ApiResponse(responseCode = "400", description = "Invalid credentials format (empty email/password)",
                 content = @Content),
    @ApiResponse(responseCode = "401", description = "Invalid email or password",
                 content = @Content)
    })
    public TokenDTO login(@RequestBody LoginDTO request) {

        Authentication authentication = 
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
                )
            );

        String token = jwtService.generateToken(authentication);

        return new TokenDTO(token);
    }
}