package sideeffect.project.dto.freeboard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import sideeffect.project.domain.freeboard.FreeBoard;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreeBoardRequest {

    @NotBlank
    private String title;

    @URL
    @NotEmpty(message = "프로젝트 url은 작성해야 합니다.")
    private String projectUrl;

    @NotBlank
    private String content;

    @Size(min = 3, max = 20)
    private String projectName;

    private String subTitle;

    public FreeBoard toFreeBoard() {
        return FreeBoard.builder()
            .title(title)
            .content(content)
            .projectUrl(projectUrl)
            .projectName(projectName)
            .subTitle(subTitle)
            .build();
    }
}
