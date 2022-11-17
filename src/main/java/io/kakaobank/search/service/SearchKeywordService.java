package io.kakaobank.search.service;

import io.kakaobank.search.exception.BusinessException;
import io.kakaobank.search.model.dto.Keyword;
import io.kakaobank.search.model.entity.SearchKeyword;
import io.kakaobank.search.repository.SearchKeywordRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;
    private final RedissonClient redissonClient;

    @SneakyThrows
    @Transactional
    public void addKeyword(final String keyword) {
        RLock lock = redissonClient.getLock("searchKeywordAddLock");
        try {
            if(lock.tryLock(3, 10, TimeUnit.MINUTES)){
                log.info("lock acquire SUCCESS!!!!!!");
                Thread.sleep(1000);

                //do whatever I want!
                Optional<SearchKeyword> searchKeyword = searchKeywordRepository.findById(keyword);
                if(searchKeyword.isPresent()) {
                    searchKeyword.get().increaseHit();
                }else{
                    SearchKeyword newSearchKeyword = SearchKeyword.newSearchKeyword(keyword);
                    searchKeywordRepository.save(newSearchKeyword);
                }
            }else{
                log.error("cannot acquire lock, wait time is over...");
                throw new BusinessException(HttpStatus.CONFLICT, "Cannot get Lock...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("release lock");
            if(lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }

//        searchKeywordRepository.insertOrUpdateKeywordHit(keyword);
    }

    @Transactional(readOnly = true)
    public List<Keyword> getTop10Keywords() {
        return searchKeywordRepository.findTop10ByOrderByHitDesc().stream()
                .map(Keyword::from)
                .collect(Collectors.toList());
    }
}
