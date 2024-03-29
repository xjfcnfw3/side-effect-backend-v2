package sideeffect.project.domain.penalty;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sideeffect.project.domain.recruit.RecruitBoard;
import sideeffect.project.domain.user.User;

@Builder
@Entity
@Getter
@Table(name = "penalties",
    indexes = @Index(name = "board_user_index", columnList = "recruit_board_id, user_id", unique = true)
)
@EntityListeners(value = AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Penalty penalize(User user, RecruitBoard recruitBoard) {
        Penalty penalty = new Penalty();
        penalty.setUser(user);
        penalty.setRecruitBoard(recruitBoard);
        return penalty;
    }

    private void setUser(User user) {
        this.user = user;
        this.user.addPenalty(this);
    }

    private void setRecruitBoard(RecruitBoard recruitBoard) {
        this.recruitBoard = recruitBoard;
        this.recruitBoard.addPenalty(this);
    }
}
