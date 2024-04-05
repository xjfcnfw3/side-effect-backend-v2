package sideeffect.project.dto.like;

import lombok.Builder;
import sideeffect.project.domain.like.RecruitLike;

@Builder
public record RecruitLikeResponse(Long recruitBoardId, String userNickname, String message) {

    public static RecruitLikeResponse of(RecruitLike recruitLike, LikeResult message) {
        return RecruitLikeResponse.builder()
                .recruitBoardId(recruitLike.getRecruitBoard().getId())
                .userNickname(recruitLike.getUser().getNickname())
                .message(message.getMessage())
                .build();
    }
}
