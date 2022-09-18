## Getting Started

### 1. Local DB
#### (For Apple silicon)

> Mysql Docker Container 기동
```
    docker-compose -f docker-compose-m1.yml up -d
```
>  Mysql Docker Container 중지
```
    docker-compose -f docker-compose-m1.yml down -v
```
#### (For Intel Chip)

> Mysql Docker Container 기동
```
    docker-compose up -d
```
>  Mysql Docker Container 중지
```
    docker-compose down -v
```
### 2. Spring Boot App Run
IDE 로 Spring Boot App 을 기동하거나 아래의 gradlew 명령어로 어플리케이션 기동
```
  ./gradelw bootrun
```
App 이 기동되며 Flyway 스크립트로 테이블 스키마 생성됨

### 3. API 테스트  
API Spec - http://localhost:4000/swagger-ui/index.html 

HTTP Request file 로 테스트 가능
```
  /api-test/api-test.http 파일로 API 테스트 가능
```

### 4. 사용 외부 Library
1. Spring Boot Cloud Open Feign - 카카오, 네이버 외부 API 연동 위함
2. Resilience4j circuitbreaker - 외부 API 연동 실패시 Fallback 추가 위함
3. Spring Boot Cache - API response 캐싱 위함
4. Caffeine cache - High performance Java local cache
5. Spring Data JPA - 기본 JPA 레포지토리 구성 위함
6. Spring Boot Validation - Controller Request Validation 위함
7. Spring Doc - swagger 3.0 API 명세 작성 위함

### 5. 기술적 요구사항 구현
1. 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현

    키워드 검색횟수 저장시, Mysql "INSERT INTO ~ ON DUPLICATE KEY UPDATE" 구문으로 DB의 Record Write Lock 을 통한동시성 이슈 해결
  
2. 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 발생 상황에 대한 고려

    카카오, 네이버 API 서버 장애 시, Fallback 메소드가 실행되게 하여 에러 전파를 방지
  
3. 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려

    검색 API 제공자별로 Feign Client 패키지 분리 구성, 필요한 모델 및 Fallback 메소드 독립적으로 추가가능
  
    검색 서비스별 데이터 취합시, KAKAO 를 제외한 Others 파라미터를 List 로 받아 로직 변경사항 없이도 데이터 취합 및 정렬
  
4. 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려

    장소검색 API 의 경우, keyword 에 대한 검색결과가 빈번히 바뀌지 않을것으로 예상됨. Caffeine Cache 이용하여
  
    Keyword 에 해당하는 장소에 대한 1시간 expiretime 을 가진 캐싱 데이터 생성
    
    "서울" 키워드 검색시, 캐싱 전 (412 ms) --> 캐싱 후 (32 ms) 로 Reponse Time 개선
  
5. 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계

    BaseSearchService Interface 를 두어, 장소 검색 뿐아니라, 영화, 인물 등에 대한 SearchService 확장 가능
    
