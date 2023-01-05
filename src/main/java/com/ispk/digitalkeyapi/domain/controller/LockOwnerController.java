package com.ispk.digitalkeyapi.domain.controller;

import com.ispk.digitalkeyapi.domain.dto.KeyDto;
import com.ispk.digitalkeyapi.domain.dto.LockDto;
import com.ispk.digitalkeyapi.domain.dto.SharedKeyUrlDto;
import com.ispk.digitalkeyapi.domain.entity.DoorKey;
import com.ispk.digitalkeyapi.domain.entity.DoorLock;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.service.LockOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lockOwners")
@Validated
@RequiredArgsConstructor
public class LockOwnerController {

    private final LockOwnerService lockOwnerService;

    @ModelAttribute("lockOwner")
    LockOwner getLockOwner() {
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return lockOwnerService.getByEmail(email);
    }

    @GetMapping("/locks")
    public List<DoorLock> getAllLocks(LockOwner lockOwner) {
        return lockOwner.getDoorLocks();
    }

    @PostMapping("/locks")
    public void addLock(LockOwner lockOwner, @Valid @RequestBody LockDto lockDto) {
        lockOwnerService.addLock(lockOwner, lockDto);
    }

    @DeleteMapping("/locks/{lockId}")
    public void disconnectLock(LockOwner lockOwner, @PathVariable Long lockId) {
        lockOwnerService.disconnectLock(lockOwner, lockId);
    }

    @GetMapping("/locks/{lockId}/keys")
    public List<DoorKey> getKeys(LockOwner lockOwner, @PathVariable Long lockId) {
        return lockOwner.getDoorKeys().stream()
                .filter(it -> it.getLock().getId().equals(lockId))
                .collect(Collectors.toList());
    }

    @PostMapping("/locks/{lockId}/keys")
    public void createKey(
            LockOwner lockOwner,
            @PathVariable Long lockId, @Valid @RequestBody KeyDto keyDto) {
        lockOwnerService.createKey(lockOwner, lockId, keyDto);
    }

    @DeleteMapping("/locks/{lockId}/keys/{keyId}")
    public void removeKey(
            LockOwner lockOwner,
            @PathVariable Long lockId, @PathVariable Long keyId) {
        lockOwnerService.removeKey(lockOwner, keyId);
    }

    @PostMapping("/locks/{lockId}/keys/{keyId}")
    public SharedKeyUrlDto shareKey(
            LockOwner lockOwner,
            @PathVariable Long lockId, @PathVariable Long keyId) {
        return lockOwnerService.createKeyShareUrl(lockOwner, keyId);
    }

}
