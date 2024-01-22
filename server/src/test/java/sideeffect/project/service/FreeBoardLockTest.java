package sideeffect.project.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import sideeffect.project.domain.freeboard.FreeBoard;
import sideeffect.project.domain.user.User;
import sideeffect.project.dto.freeboard.DetailedFreeBoardResponse;
import sideeffect.project.dto.freeboard.FreeBoardRequest;
import sideeffect.project.repository.UserRepository;

@SpringBootTest
public class FreeBoardLockTest {

    @Autowired
    private FreeBoardService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionTemplate transaction;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@naver.com")
                .nickname("tester")
                .password("1234")
                .build();
        userRepository.save(user);
        transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Test
    void test() throws InterruptedException {
        int numberOfThread = 50;
        CountDownLatch latch = new CountDownLatch(numberOfThread);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThread);

        FreeBoardRequest freeBoardRequest = FreeBoardRequest.builder().title("게시판")
                .projectUrl("http://test.url")
                .content("게시판 입니다.")
                .build();;
        FreeBoard board = service.register(user, freeBoardRequest);

        for (int i = 0; i < numberOfThread; i++) {
            executorService.execute(() -> {
                try {
                    transaction.execute((status -> service.findBoard(board.getId(), null)));
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                }

            });
        }
        latch.await();
        DetailedFreeBoardResponse result = service.findBoard(board.getId(), null);
        Assertions.assertThat(result.getViews()).isEqualTo(numberOfThread + 1);
    }
}
