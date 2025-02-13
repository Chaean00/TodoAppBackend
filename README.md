# Clush Todo Application Frontend
## 1. 앱 설명 및 버전 정보
Spring Boot를 사용하여 Todo를 관리하는 백엔드 어플리케이션입니다.

Spring Boot: v3.4.2

JDK: 17

## 2. 주요 기능
회원가입/로그인/로그아웃: JWT를 이용하여 인증/인가를 구현하였습니다

Todo 관리: 로그인 후 사용자는 개인마다 Todo 생성, 조회, 수정, 삭제, 검색의 기능을 수행할 수 있습니다.

AOP 로깅: Service로직의 실행 시간을 AOP로 나누었고, 모든 로그를 logs/todoApp.log에 저장하도록 했습니다.

유효성 검증: Validation을 이용해 값 검증을 수행하도록 설정했습니다.

Swagger: API 명세 문서 자동화 및 테스트를 위해 설정하였습니다.

## 3. 빌드 및 실행 방법
### 3-1. 프로젝트 Clone
```
git clone https://github.com/Chaean00/TodoAppBackend.git
```

### 3-2. 환경 설정
application.properties의 DB관련 값들을 본인에 맞게 수정합니다.
```
spring.application.name=TodoAppBackend

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/todo
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```


### 3-3. 빌드 및 실행
루트 디렉토리에서 아래 3가지 방법 중 자신에게 맞는 방법을 실행시킵니다.

#### IDE
IDE에서 `TodoAppBackendApplication.java`를 직접 실행시킵니다.

#### Mac
빌드 
```
./gradlew clean build
```
실행
```
./gradlew bootRun
```

#### Windows
빌드
```
gradlew clean build
```
실행
```
gradlew bootRun
```

### 3-4. 데이터베이스 셋팅
  1. 스키마 생성.
  ```
  CREATE DATABASE todo;
  ```
  2. 애플리케이션 실행.
     
  JPA가 애플리케이션 실행 시 엔티티를 기반으로 테이블을 자동 생성합니다.
  
  4. 사용자 데이터 insert
  ```
  LOCK TABLES `user` WRITE;
  INSERT INTO `user` VALUES (1,'2025-02-12 08:01:23.812005','2025-02-12 08:01:23.812005','test1','$2a$10$By/Xz4aEXkEyOI91OsgPyOYFs1EXt0I0xP09KafySMzz.Dwsb9qLO','test1'),(2,'2025-02-12 08:01:31.049924','2025-02-12 08:01:31.049924','test2','$2a$10$G3Z37Wb6LPZb3U34yNBcy.Nl9KOkJNPFHM.96IqwOtonSQ.QIc.bu','test2'),(3,'2025-02-12 13:16:12.228320','2025-02-12 13:16:12.228320','test3','$2a$10$GxHQIh.ODvKeC9hHxCPeNObDHJV7uj3x5KKKmvdSNVFbrhLXf6UbC','test3'),(5,'2025-02-13 08:35:39.527180','2025-02-13 08:35:39.527180','test4','$2a$10$2m7MRt5sEhzNtXfa1oSFku9kd0lg8D9tRfDJ8i.IGsuUiLR7A3DgO','test4'),(6,'2025-02-13 08:35:44.966370','2025-02-13 08:35:44.966370','test5','$2a$10$V7ZlGc47Xm5m85YRsXRYL.f34iUBlN9Q.mpDKbqxc1BnjVicNyvTW','test5');
  UNLOCK TABLES;
  ```
  
  4. 할일(todo) 데이터 insert
  ```
  LOCK TABLES `todo` WRITE;
  INSERT INTO `todo` VALUES (1,'2025-02-13 08:36:18.755125','2025-02-13 08:36:18.755125','PERSONAL',_binary '\0','개인 할일 1',1),(2,'2025-02-13 08:36:22.623986','2025-02-13 08:36:22.623986','WORK',_binary '\0','업무 할일 1',1),(3,'2025-02-13 08:36:26.977190','2025-02-13 08:36:26.977190','HEALTH',_binary '\0','운동 할일 1',1),(4,'2025-02-13 08:36:33.757867','2025-02-13 08:36:33.757867','SHOPPING',_binary '\0','쇼핑 할일 1',1),(5,'2025-02-13 08:36:46.204162','2025-02-13 08:36:46.204162','PERSONAL',_binary '\0','개인 할일 2',2),(6,'2025-02-13 08:36:52.368745','2025-02-13 08:36:52.368745','WORK',_binary '\0','업무 할일 2',2),(7,'2025-02-13 08:37:00.898990','2025-02-13 08:37:00.898990','ETC',_binary '\0','기타 할일 2',2),(8,'2025-02-13 08:37:13.419837','2025-02-13 08:37:13.419837','PERSONAL',_binary '\0','개인 할일 3',3),(9,'2025-02-13 08:37:18.939813','2025-02-13 08:37:18.939813','WORK',_binary '\0','운동 할일 3',3),(10,'2025-02-13 08:37:23.933072','2025-02-13 08:37:39.838939','ETC',_binary '\0','수정된 기타 할일 3',3),(11,'2025-02-13 08:37:30.085456','2025-02-13 08:37:30.085456','PERSONAL',_binary '\0','개인 할일 3-1',3),(12,'2025-02-13 08:37:56.416597','2025-02-13 08:39:08.584292','PERSONAL',_binary '','개인 할일 4',5),(13,'2025-02-13 08:38:01.678961','2025-02-13 08:38:01.678961','PERSONAL',_binary '\0','개인 할일 4-1',5),(14,'2025-02-13 08:38:25.861112','2025-02-13 08:38:25.861112','PERSONAL',_binary '\0','개인 할일 4-2',5),(15,'2025-02-13 08:38:30.138801','2025-02-13 08:39:10.246228','PERSONAL',_binary '','개인 할일 4-3',5),(16,'2025-02-13 08:38:33.924102','2025-02-13 08:38:33.924102','PERSONAL',_binary '\0','개인 할일 4-4',5);
  UNLOCK TABLES;
  ```

## 4. 라이브러리에 대한 설명
```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-web'
compileOnly 'org.projectlombok:lombok'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'com.mysql:mysql-connector-j'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
// Spring Security
implementation 'org.springframework.boot:spring-boot-starter-security'
// JWT
implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.5'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.5'
// Swagger
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
```

### 4-1. Spring Boot Starter Web
- 내장 톰캣 서버 및 MVC 기반 REST API 구현을 위해 추가했습니다.

### 4-2. MySQL Connector
- MySQL DB와 애플리케이션 연결을 위한 JDBC 드라이버입니다.

### 4-3. Spring Security
- Spring에서 보안 관련 기술을 간단히 적용할 수 있도록 해줍니다. 현 프로젝트에서는 JWT와 연계해 인증/인가 로직을 구현했습니다.

### 4-4. JWT
- JWT기반의 인증/인가를 구현하기 위한 라이브러리입니다.

### 4-5. Lombok
- 반복되는 코드(Getter, Setter, 생성자 등)의 코드를 자동 생성해주는 라이브러리입니다.

### 4-6. Devtools
- 개발 시 코드 수정이 있으면 애플리케이션을 자동으로 재시작하여 개발 효율성을 높이기 위해 추가하였습니다.

### 4-7. Swagger
- API 명세서를 자동 생성해주며, 웹에서 API를 테스트해볼 수 있습니다.

### 4-8. Spring Boot Starter Test
- 테스트 코드 작성을 위한 라이브러리입니다.

## 5. API 명세
애플리케이션 실행 후 <a href="http://localhost:8080/swagger-ui/index.html#/">http://localhost:8080/swagger-ui/index.html#/</a> 에서 API 명세서를 확인할 수 있습니다.

todo관련 API를 정상적으로 동작 위해서는 쿠키에 JWT가 추가되어야 합니다.
