package com.zuku.jira.authentication;

import com.zuku.jira.configuration.JwtService;
import com.zuku.jira.domain.repository.IAccountRepository;
import com.zuku.jira.domain.repository.IUserRepository;
import com.zuku.jira.domain.validator.Validator;
import com.zuku.jira.entity.Account;
import com.zuku.jira.entity.User;
import com.zuku.jira.domain.enums.ActionResultDescription;
import com.zuku.jira.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> register(RegisterRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Validator.loginValidate(request.getLogin()));
        stringBuilder.append(Validator.passwordValidate(request.getPassword()));
        if (!stringBuilder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(stringBuilder.toString());
        }
        if (accountRepository.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ActionResultDescription.LOGIN_EXIST.getDescription());
        }
        Account account = Account.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        accountRepository.save(account);

        User user = User.builder()
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .account(account)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthenticateResponse.builder()
                        .token(jwtToken)
                        .build());
    }

    public ResponseEntity<Object> authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));
        Optional<User> userOptional = userRepository.findUserByAccountLogin(request.getLogin());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ActionResultDescription.USER_NOT_EXIST.getDescription());
        }
        User user = userOptional.get();
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticateResponse.builder()
                .token(jwtToken)
                .build());
    }
}
