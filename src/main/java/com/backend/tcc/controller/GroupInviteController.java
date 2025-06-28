package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.groupinvite.GroupInviteRequestDTO;
import com.backend.tcc.dto.groupinvite.GroupInviteResponseDTO;
import com.backend.tcc.services.GroupInviteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/group/invite")
@RequiredArgsConstructor
public class GroupInviteController {
    private final GroupInviteService inviteService;

    @PostMapping
    public ResponseEntity<Void> sendInvite(@RequestBody GroupInviteRequestDTO request, @RequestHeader("userId") String userId) {
        inviteService.sendInvite(request, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<GroupInviteResponseDTO>> getPendingInvites(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(inviteService.getPendingInvites(userId));
    }

    @PostMapping("/{inviteId}/respond")
    public ResponseEntity<Void> respondInvite(@PathVariable String inviteId, @RequestParam boolean accept) {
        inviteService.respondInvite(inviteId, accept);
        return ResponseEntity.ok().build();
    }
}
