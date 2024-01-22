package sideeffect.project.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sideeffect.project.domain.freeboard.FreeBoard;
import sideeffect.project.repository.freeboard.FreeBoardRepositoryCustom;

@Transactional(readOnly = true)
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>, FreeBoardRepositoryCustom {

    boolean existsByProjectUrl(String projectUrl);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b from FreeBoard b "
        + "left outer join fetch b.freeBoardLikes.likes "
        + "where b.id = :boardId")
    Optional<FreeBoard> searchBoardFetchJoin(@Param("boardId") Long boardId);
}
