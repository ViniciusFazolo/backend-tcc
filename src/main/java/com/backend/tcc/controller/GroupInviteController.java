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
    public ResponseEntity<Void> sendInvite(@RequestBody GroupInviteRequestDTO request) {
        inviteService.sendInvite(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<GroupInviteResponseDTO>> getPendingInvites(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(inviteService.getPendingInvites(userId));
    }

    @GetMapping("/pending/{id}")
    public ResponseEntity<List<GroupInviteResponseDTO>> getPendingInvitesByGroupId(@PathVariable String id) {
        return ResponseEntity.ok(inviteService.getPendingInvitesByGroupId(id));
    }

    @PostMapping("/{inviteId}/respond")
    public ResponseEntity<Void> respondInvite(@PathVariable String inviteId, @RequestParam boolean accept) {
        inviteService.respondInvite(inviteId, accept);
        return ResponseEntity.ok().build();
    }
}
