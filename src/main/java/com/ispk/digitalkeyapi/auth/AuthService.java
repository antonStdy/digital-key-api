package com.ispk.digitalkeyapi.auth;

import com.ispk.digitalkeyapi.auth.dto.RegistrationCredentialsDto;
import com.ispk.digitalkeyapi.domain.entity.Account;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final LockOwnerRepository lockOwnerRepository;
    private final PasswordEncoder passwordEncoder;

    public LockOwner registerUser(RegistrationCredentialsDto credentials) {
        if (lockOwnerRepository.existsByAccount_Email(credentials.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }

        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        var account = new Account();
        account.setEmail(credentials.getEmail());
        account.setPassword(credentials.getPassword());
        var lockOwner = new LockOwner();
        lockOwner.setAccount(account);
        return lockOwnerRepository.save(lockOwner);
    }
}
