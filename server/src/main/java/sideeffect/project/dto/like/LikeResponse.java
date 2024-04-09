package sideeffect.project.dto.like;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideeffect.project.domain.user.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeResponse {
    private Long boardId;
    private String userNickname;
    private String message;

    public static LikeResponse of(User user, Long boardId, LikeResult message) {
        return LikeResponse.builder()
                .userNickname(user.getNickname())
                .boardId(boardId)
                .message(message.getMessage())
                .build();
    }
}
