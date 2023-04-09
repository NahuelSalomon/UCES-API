package com.example.UCESAPI.service;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.exception.UserAlreadyExistException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.UserType;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.dto.LoginResponseDto;
import com.example.UCESAPI.model.dto.user.UserInsertRequestDto;
import com.example.UCESAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public User add(User user) {
        return this.userRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public User getById(Integer id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    public void update(Integer id, User user) throws UserNotFoundException {
        this.getById(id);
        this.userRepository.save(user);
    }

    public UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException {

        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
            User user = userRepository.findByEmail(loginRequestDto.getEmail());
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().toString())));

        } catch (BadCredentialsException e) {
            throw e;
        }
        return userDetails;
    }

    public LoginResponseDto register(UserInsertRequestDto user) throws UserAlreadyExistException, IOException {

        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser !=null){
            throw new UserAlreadyExistException();
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setActive(true);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserType(UserType.ROLE_STUDENT);
        //newUser.setImage(Base64.getDecoder().decode(user.getImage()));
        newUser.setImage(user.getImage());
        newUser = userRepository.save(newUser);

        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword()));*/

        return new LoginResponseDto(jwtUtils.generateToken(
                this.loadUserByUsername(newUser.getEmail())
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Email not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().toString())));
    }

    /*Método para asegurar el acceso de un endpoint a un administrador o
    a un usuario que corresponda al id pasado por parámetro. */
    public Boolean validateRole(Integer id, HttpServletRequest req){

        String token = req.getHeader("Authorization").replace("Bearer ", "");
        String email = jwtUtils.extractUsername(token);
        UserDetails userDetails = this.loadUserByUsername(email);

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"))){
            return true;
        }else{
            User user = userRepository.findByEmail(email);

            if (id.equals(user.getId()))
                return true;
            else
                return false;
        }
    }
}
