package io.playground.search;

import io.playground.search.repository.SearchKeywordRepository;
import io.playground.search.service.SearchKeywordService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LocationApplicationTests {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    @Autowired
    private SearchKeywordService searchKeywordService;

    @Test
    void contextLoads() {}

    @Test
    public void testConcurrency() throws InterruptedException {
        Long curCount = searchKeywordRepository.findById("서울").get().getHit();
        logger.info("start : " + curCount);

        final Long threadCount = 100L;
        CountDownLatch countDownLatch = new CountDownLatch(Math.toIntExact(threadCount));

        List<Thread> threads = Stream
                .generate(() -> new Thread(new Increase(countDownLatch)))
                .limit(threadCount)
                .collect(Collectors.toList());

        threads.forEach(Thread::start);
        countDownLatch.await();

        Long lastCount = searchKeywordRepository.findById("서울").get().getHit();
        logger.info("End : " + lastCount);
        assertEquals(lastCount, curCount + threadCount);
    }

    private class Increase implements Runnable {

        private CountDownLatch countDownLatch;
        public Increase(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            searchKeywordService.addKeyword("서울");
            countDownLatch.countDown();
        }
    }
}
