package github.murillosnds.tma.security;

import github.murillosnds.tma.service.CustomUserDetailsService;
import github.murillosnds.tma.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    private JwtAuthenticationFilter filter; 

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Test
    void doFilterInternal_shouldAuthenticate_whenValidToken() throws Exception {
        String token = "validToken";
        String email = "joao@email.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(email);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticate_whenNoToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}