package com.ispk.digitalkeyapi;

import com.ispk.digitalkeyapi.domain.entity.Account;
import com.ispk.digitalkeyapi.domain.entity.DoorLock;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import com.ispk.digitalkeyapi.domain.service.lockvendor.LockVendorService;
import com.ispk.digitalkeyapi.domain.service.lockvendor.LockVendorServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataFiller implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final LockOwnerRepository userRepository;

    private final LockVendorServiceFactory lockVendorServiceFactory;

    private String testEmail = "test@email.com";
    private String testPassword = "password";

    @Override
    public void run(String... args) throws Exception {
        LockOwner user = new LockOwner();
        user.setAccount(getTestAccount());
        user.getDoorLocks().add(getTestLock());
        userRepository.save(user);
    }

    private DoorLock getTestLock() {
        LockVendorService stubVendorService =
                lockVendorServiceFactory.get("Stub vendor");

        return stubVendorService
                .getLock("dsklcmsdlkc", "lkcnslkdcn");
    }

    private Account getTestAccount() {
        String encrypted = passwordEncoder.encode(testPassword);
        var account = new Account();
        account.setEmail(testEmail);
        account.setPassword(encrypted);
        return account;
    }
}
