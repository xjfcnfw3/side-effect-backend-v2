package sideeffect.project.domain.like;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sideeffect.project.domain.freeboard.FreeBoard;
import sideeffect.project.domain.user.User;

@Getter
@Entity
@Table(
    name = "likes",
    indexes = {
        @Index(name = "board_user_index", columnList = "free_board_id, user_id", unique = true)
    }
)
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Like like(User user, FreeBoard freeBoard) {
        Like like = new Like();
        like.setUser(user);
        like.setFreeBoard(freeBoard);
        return like;
    }

    public void setUser(User user) {
        this.user = user;
        user.addLike(this);
    }

    public void setFreeBoard(FreeBoard freeBoard) {
        this.freeBoard = freeBoard;
        freeBoard.addLike(this);
    }
}
