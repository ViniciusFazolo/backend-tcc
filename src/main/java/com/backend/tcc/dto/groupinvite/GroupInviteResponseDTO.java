package com.backend.tcc.dto.groupinvite;

import com.backend.tcc.dto.user.UserResponseDTO;

public record GroupInviteResponseDTO(
    String id,
    String groupId,
    String groupName,
    UserResponseDTO invitedBy,
    String status
) {
    
}
