package com.example.UCESAPI.auth.filter;

import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.auth.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class JwtRequestFilterTest {

    @Mock
    private UserService userService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilterInternal_ValidToken() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String jwtToken = "validJwtToken";
        String email = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.extractUsername(jwtToken)).thenReturn(email);
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtils.validateToken(jwtToken, userDetails)).thenReturn(true);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationTokenArgumentCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(userService).loadUserByUsername(email);
        verify(jwtUtils).validateToken(jwtToken, userDetails);
        verify(SecurityContextHolder.getContext()).setAuthentication(authenticationTokenArgumentCaptor.capture());

        UsernamePasswordAuthenticationToken capturedAuthRequest = authenticationTokenArgumentCaptor.getValue();
        assertNotNull(capturedAuthRequest);
        assertEquals(userDetails, capturedAuthRequest.getPrincipal());
        assertNull(capturedAuthRequest.getCredentials());
        assertEquals(userDetails.getAuthorities(), capturedAuthRequest.getAuthorities());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Simular la falta de encabezado de autorización
        when(request.getHeader("Authorization")).thenReturn(null);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que no se realizan llamadas a métodos relacionados con la extracción del token JWT
        verifyNoInteractions(jwtUtils, userService);

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_InvalidAuthorizationHeader() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Simular un encabezado de autorización inválido
        when(request.getHeader("Authorization")).thenReturn("invalid_token_format");

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que no se realizan llamadas a métodos relacionados con la extracción del token JWT
        verifyNoInteractions(jwtUtils, userService);

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_ValidJwtToken() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "valid_jwt_token";
        String email = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);

        // Simular un encabezado de autorización con un token JWT válido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.extractUsername(jwtToken)).thenReturn(email);
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtils.validateToken(jwtToken, userDetails)).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que se realizan las llamadas esperadas
        verify(jwtUtils).extractUsername(jwtToken);
        verify(userService).loadUserByUsername(email);
        verify(jwtUtils).validateToken(jwtToken, userDetails);
        verify(SecurityContextHolder.getContext()).setAuthentication(any(UsernamePasswordAuthenticationToken.class));

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_InvalidJwtToken() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String jwtToken = "invalid_jwt_token";
        String email = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);

        // Simular un encabezado de autorización con un token JWT inválido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.extractUsername(jwtToken)).thenReturn(email);
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtils.validateToken(jwtToken, userDetails)).thenReturn(false);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que se realizan las llamadas esperadas
        verify(jwtUtils).extractUsername(jwtToken);
        verify(userService).loadUserByUsername(email);
        verify(jwtUtils).validateToken(jwtToken, userDetails);

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_NullAuthentication() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "valid_jwt_token";
        String email = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);

        // Simular un encabezado de autorización con un token JWT válido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.extractUsername(jwtToken)).thenReturn(email);
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtils.validateToken(jwtToken, userDetails)).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Simular la ausencia de autenticación en el contexto de seguridad
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que se realizan las llamadas esperadas
        verify(jwtUtils).extractUsername(jwtToken);
        verify(userService).loadUserByUsername(email);
        verify(jwtUtils).validateToken(jwtToken, userDetails);
        verify(SecurityContextHolder.getContext()).setAuthentication(any(UsernamePasswordAuthenticationToken.class));

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_MailNull() throws ServletException, IOException {
        // Configurar el escenario
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "valid_jwt_token";
        UserDetails userDetails = mock(UserDetails.class);

        // Simular un encabezado de autorización con un token JWT válido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.extractUsername(jwtToken)).thenReturn(null);
        when(jwtUtils.validateToken(jwtToken, userDetails)).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Simular la ausencia de autenticación en el contexto de seguridad
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);

        // Ejecutar el filtro
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verificar que se realizan las llamadas esperadas
        verify(jwtUtils).extractUsername(jwtToken);
        //verify(SecurityContextHolder.getContext()).setAuthentication(any(UsernamePasswordAuthenticationToken.class));

        // Verificar que se continúa con la cadena de filtros
        verify(filterChain, times(1)).doFilter(request, response);
    }


}
