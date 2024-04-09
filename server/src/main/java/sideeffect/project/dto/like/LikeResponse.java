package sideeffect.project.dto.like;

import lombok.Builder;
import sideeffect.project.domain.user.User;

@Builder
public record LikeResponse(Long boardId, String userNickname, String message) {
    public static LikeResponse of(User user, Long boardId, LikeResult message) {
        return LikeResponse.builder()
            .boardId(boardId)
            .userNickname(user.getNickname())
            .message(message.getMessage())
            .build();
    }
}
