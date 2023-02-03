package com.company.appmyalllearnsproject.service;

import com.company.appmyalllearnsproject.entity.User;
import com.company.appmyalllearnsproject.exception.ResourceNotFoundException;
import com.company.appmyalllearnsproject.payload.ApiResponse;
import com.company.appmyalllearnsproject.payload.LoginDto;
import com.company.appmyalllearnsproject.payload.RegisterDto;
import com.company.appmyalllearnsproject.repository.RoleRepository;
import com.company.appmyalllearnsproject.repository.UserRepository;
import com.company.appmyalllearnsproject.security.JwtProvider;
import com.company.appmyalllearnsproject.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    @Lazy
    @Autowired
    JwtProvider jwtProvider;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPrePassword())){
            return new ApiResponse("PrePassword can not be null", false);
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ApiResponse("User already exists", false);
        }
        User user = userRepository.save(new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException("roles", "name", AppConstants.USER)),
                UUID.randomUUID().toString(),
                true
        ));
        boolean sendingEmail = sendingEmail(dto.getUsername(), user.getEmailCode());
        if (sendingEmail){
            System.out.println("Email sending");
        }
        return new ApiResponse("Register successfully, Confirm email " + user.getUsername(), true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername(), ((User) authenticate.getPrincipal()).getRole());
            return new ApiResponse("Token", token, true);
        }
        catch (BadCredentialsException e){
            return new ApiResponse("Username or Password failed", false);
        }
    }

    public boolean sendingEmail(String sendingEmail, String emailCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("MyOffice@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Confirm Email");
            mailMessage.setText("http://localhost:8000/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail);
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email){
        Optional<User> optionalUser = userRepository.findByUsernameAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmailCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return new ApiResponse("Confirm successfully email :-)", true);
        }
        return new ApiResponse("Confirm already email :-(", false);
    }
}
