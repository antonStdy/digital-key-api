package com.ispk.digitalkeyapi.domain.controller;

import com.ispk.digitalkeyapi.domain.dto.*;
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
    public List<GetLockDto> getAllLocks(LockOwner lockOwner) {
        return lockOwner.getDoorLocks()
                .stream().map(it -> GetLockDto.builder()
                            .id(it.getId())
                            .serialNumber(it.getSerialNumber())
                            .lockStatus(it.getLockStatus())
                            .build()
                ).toList();
    }

    @PostMapping("/locks")
    public void addLock(LockOwner lockOwner, @Valid @RequestBody AddLockDto addLockDto) {
        lockOwnerService.addLock(lockOwner, addLockDto);
    }

    @DeleteMapping("/locks/{lockId}")
    public void disconnectLock(LockOwner lockOwner, @PathVariable Long lockId) {
        lockOwnerService.disconnectLock(lockOwner, lockId);
    }

    @GetMapping("/locks/{lockId}/keys")
    public List<GetKeyDto> getKeys(LockOwner lockOwner, @PathVariable Long lockId) {
        return lockOwner.getDoorKeys().stream()
                .filter(it -> it.getLock().getId().equals(lockId))
                .map(it -> GetKeyDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .duration(it.getDuration())
                        .sharingUrls(it.getSharingUrls())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/locks/{lockId}/keys")
    public void createKey(
            LockOwner lockOwner,
            @PathVariable Long lockId, @Valid @RequestBody AddKeyDto addKeyDto) {
        lockOwnerService.createKey(lockOwner, lockId, addKeyDto);
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
