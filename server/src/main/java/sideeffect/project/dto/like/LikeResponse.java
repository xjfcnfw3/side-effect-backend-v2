package sideeffect.project.dto.like;

import lombok.Builder;
import sideeffect.project.domain.like.Like;

@Builder
public record LikeResponse(Long boardId, String userNickname, String message) {
    public static LikeResponse of(Like like, LikeResult message) {
        return LikeResponse.builder()
            .boardId(like.getFreeBoard().getId())
            .userNickname(like.getUser().getNickname())
            .message(message.getMessage())
            .build();
    }
}
