package sideeffect.project.domain.like;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
@Embeddable
public class FreeBoardLikes {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Like> likes;

    private Integer likeNumber;

    public FreeBoardLikes() {
        this.likes = new HashSet<>();
        this.likeNumber = 0;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public void deleteLike(Like like) {
        this.likes.remove(like);
    }

    public int getLikeNumber() {
        return likes.size();
    }

    public void increaseLikeNumber() {
        this.likeNumber++;
    }

    public void decreaseLikeNumber() {
        this.likeNumber--;
    }
}
