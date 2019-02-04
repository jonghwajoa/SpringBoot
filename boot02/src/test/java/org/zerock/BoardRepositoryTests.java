package org.zerock;

import java.net.InterfaceAddress;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {
	@Autowired
	private BoardRepository boardRepo;

//	@Test
//	public void inspect() {
//		Class<?> clz = boardRepo.getClass();
//		System.out.println(clz.getName());
//
//		Class<?>[] interfaces = clz.getInterfaces();
//		Stream.of(interfaces).forEach(inter -> System.out.println(inter.getName()));
//
//		Class<?> superClasses = clz.getSuperclass();
//		System.out.println(superClasses.getName());
//	}

	@Test
	public void testInsert() {
		Board board = new Board();
		board.setTitle("게시물의 제목");
		board.setContent("게시물 내용 넣기...");
		board.setWriter("user00");

		boardRepo.save(board);
	}
}
