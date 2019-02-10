package maria;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;

import maria.domain.User;
import maria.persistence.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@Autowired
	UserRepository userRepo;

	public void insertTest() {
		User newUser = new User();
		newUser.setUid("유저아이디입니다^^");
		newUser.setUpw("유저 비밀번호 입니다.^^");
		userRepo.save(newUser);

	}

	public void selectTest() {
		userRepo.findById(1L).ifPresent(user -> {
			System.out.println(user.toString());
		});
	}

	public void updateTest() {
		userRepo.findById(2L).ifPresent(user -> {
			user.setUid("수정된 아이디 입니다.^^");
			user.setUpw("수정된 비밀번호 입니다.^^");
			userRepo.save(user);
		});
		/*
		 * Hibernate: select user0_.no as no1_0_0_, user0_.uid as uid2_0_0_, user0_.upw
		 * as upw3_0_0_ from tbl_users user0_ where user0_.no=?
		 */
		/*
		 * Hibernate: select user0_.no as no1_0_0_, user0_.uid as uid2_0_0_, user0_.upw
		 * as upw3_0_0_ from tbl_users user0_ where user0_.no=? Hibernate: update
		 * tbl_users set uid=?, upw=? where no=?
		 */
	}

	@Test
	public void deleteTest() {
		userRepo.deleteById(1L);
	}
}
