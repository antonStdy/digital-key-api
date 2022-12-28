package com.ispk.digitalkeyapi.auth;

import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.repository.LockOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SmartLockUserDetailService implements UserDetailsService {
    private final LockOwnerRepository lockOwnerRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return lockOwnerRepository.findById(Long.valueOf(userId))
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Not found by id: " + userId));
    }

    private UserDetails toUserDetails(LockOwner lockOwner) {
        return new org.springframework.security.core.userdetails
                .User(lockOwner.getAccount().getEmail(), lockOwner.getAccount().getPassword(), Collections.emptyList());
    }
}
