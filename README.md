# SpringBoot
스프링 부트 공부시작

<br>



# CH1

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



## 페이징

- Pageable 인터페이스 ; 페이징 처리에 필요한 정보 제공
- 스프링 2.0부터는 PageRequest.of()를 사용함.
- 리턴타입을 Collection< > 대신 List를 사용함....



### Page<T> 타입 사용

List<T>를 사용하기도 하지만 Page<T> 타입을 사용하면 Spring MVC와 연동할 떄 편리함을 제공함...



### Query 

select시 필요한 컬럼만 추출 가능



### nativeQuery

종속적인 SQL문을 그대로 사용할 수 있기 때문에 복잡한 쿼리를 작성할 떄에는 유용할 수 있음. 다만 독립적이라는 장점을 어느정도 포기해야하므로 **남용하지 않도록 주의**



### Querydsl

다양한 검색 조건에 대해서 쿼리를 실행해야 하는 경우라고 할 수 있음

@Query를 이용하는 경우에 개발 속도는 좋지만 고정적인 쿼리만을 생산함.

따라서 동적인 상황에 대한 처리를 위해 Querydsl을 이용하는것이 좋음

Domain Specific Language의 약자로 특정 도메인 객체를 조회 한다는 의미



Querydsl을 이용하기 위해 필요한 과정

1. pom.xml의 라이브러리와 Maven설정의 변경 및 실행
2. Predicate의 개발
3. Repository를 통한 실행



### Predicate

'이 조건이 맞다'고 판단하는 근거를 함수로 제공함

함수형 패러다임을 가진 언어들에서 자주 사용되는데 Java8에도 포홤되어 있음

  

<br>  

# CH2

## 연관 관계

* 1:1 (@OneToOne)
* 1:N (@OneToMany)
* N:1 (@ManyToOne)
* N:M (@ManyToMany)



* 단방향 참조 : 한쪽의 클래스만이 다른 클래스의 인스턴스를 참조하도록 설정
* 양방향 참조: 양쪽 클래스 모두 다른 클래스의 인스턴스를 참조



### GenerationType

* Auto : Sprinb boot 2.0 이상부터는 hibernate_sequence라는 테이블을 생성하고 번호를 유지하는 방식이됨..

  hibernate_sequence라는 테이블을 생성함

* IDENTITY : AI 설정




### 1:N 관계

특이하게도 M:N관계가 아닌 1:N관계에서도 별도의 테이블을 생성함 
두개의 테이블 각각의 기본키를 저장하는 테이블을 생성함..
이것이 싫다면 @JoinTable , @JoinColumn을 이용함

* JoinTable : 자동으로 생성되는 테이블 대신 별도의 이름을 가진 테이블을 생성
* JoinColumn : 이미 존재하는 테이블에 칼럼을 추가할 때 사용

> 다에 해당하는 정보를 보관하기 위해서 추가 테이블을 만드는것



### Repository 작성

여러 엔티티들의 Repository 개수를 판단하는 데 가장 중요한 영향을 미치는 것은 엔티티 객체의 **라이프사이클**

각 엔티티가 별도의 라이프사이클을 가진다면 별도의 Repository를 생성하는 것이좋음.

### 

### Transactional

기본적으로 롤백 처리를 시도하기 때문에 @Commit을 추가해서 자동 Commit 처리를 지정할 수 있음



### 양방향 처리

양방향 처리는 단방향의 제한적인 접근에 비해 운용의 폭이 넓은 것은 사실임.. 다만.. 

최종적으로 실행되는 SQL이 성능에 나쁜 영향을 주는지를 항상 체크해주어야함..



### 지연 로딩

JPA는 연관관계가 있는 엔티티를 조회할 때 기본적으로 '지연로딩'이라는 방식을 이용함

> 정보가 필요하기 전까지는 최대한 테이블에 접근하지 않는 방식을 의미함.

가장 큰 이유는 **성능**

조인이 복잡해질수록 성능이 저하되기 때문..

연관관계의 Collection타입을 처리할 때 **지연 로딩**을 기본으로 사용함.

즉시로딩은 조인을 이용해서 필요한 모든 정보를 처리하게 됨..

```java
@OneToMany(fetch=FetchType.EAGER)
```





```java
@Transactional
	@Test
	public void insertReply2Way() {
		Optional<FreeBoard> result = boardRepo.findById(199L);
		result.ifPresent(board -> {
			List<FreeBoardReply> replies = board.getReplies();
			FreeBoardReply reply = new FreeBoardReply();
			reply.setReply("reply....");
			reply.setReplyer("replayer00");
			reply.setBoard(board);

			replies.add(reply);
			board.setReplies(replies);
			boardRepo.save(board);
		});
	}
```



# 어노테이션

- @Log : lombok의 로그를 사용할 떄 이용하는 어노테이션
- @commit 테스트 결과를 데이터 베이스에 commit하는 용도



