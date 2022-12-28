package com.ispk.digitalkeyapi.domain.controller;

import com.ispk.digitalkeyapi.domain.dto.LockDto;
import com.ispk.digitalkeyapi.domain.entity.DoorLock;
import com.ispk.digitalkeyapi.domain.entity.LockOwner;
import com.ispk.digitalkeyapi.domain.service.LockOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
