# SpringBoot
스프링 부트 공부시작



## 기초

- lombok 설치

  > https://projectlombok.org/download

- @Data

  - getter
  - setter
  - equals() ,hashCode(), toString(), 파라미터 없는 기본 생성자 자동 생성
  - 너무 강력한 기능이기 때문에 라인이 길어지더라도 getter,setter를 사용하는 것이 좋을 수도 있음





## DataSource 설정

1. application.properties 를 이용해서 필요한 구성을 설정하는 방법
2. 어노테이션
3. XML 등등



```properties
# dataSource 설정
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/스키마?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username= user_name
spring.datasource.password= user_pw
```





- @GeneratedValue : 식별키의 생성 전략을 지정
- @CreationTimestamp : 
- @UpdateTimestamp : org.hibernate로 시작하는 패키지는 Hibernate의 고유기능



* spring.jpa.hibernate.ddl-auto

  - create : 기존 테이블 삭제 후 다시 생성
  - create-drop : create와 같으나 종료 시점에 테이블 DROP
  - update : 변경된 부분만 반영
  - validate 엔티티와 테이블이 정상적으로 매핑되었는지만 확인
  - none 사용하지 않음
