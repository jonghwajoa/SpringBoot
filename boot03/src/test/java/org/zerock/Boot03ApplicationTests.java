package org.zerock;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot03ApplicationTests {

	@Autowired
	BoardRepository boardRepo;

	public void testInser200() {

		for (int i = 0; i < 200; i++) {
			Board board = new Board();
			board.setTitle("제목...." + i);
			board.setContent("내용...." + i + "채우기..");
			board.setWriter("user0" + (i % 10));
			boardRepo.save(board);
		}
	}

	public void testByTitle() {
		boardRepo.findBoardByTitle("제목....9").forEach(board -> System.out.println(board));

	}

	public void testByWriter() {
		Collection<Board> results = boardRepo.findByWriter("user00");
		results.forEach(board -> System.out.println(board));
	}

	public void testByWriterContaining() {
		Collection<Board> results = boardRepo.findByWriterContaining("05");
		results.forEach(board -> System.out.println(board));
	}

	public void testByTitleAndBno() {
		Collection<Board> results = boardRepo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		results.forEach(board -> System.out.println(board));
	}

	public void testBnoOrderBy() {
		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(90L);
		results.forEach(board -> System.out.println(board));
	}

	public void testBnoOrderByPaging() {
		org.springframework.data.domain.Pageable paging = PageRequest.of(0, 10);

		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
		results.forEach(board -> System.out.println(board));
	}

	@Test
	public void TestBnoPagingSort() {
		org.springframework.data.domain.Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
//		Collection<Board> results = boardRepo.findByBnoGreaterThan(0L, paging);
//		results.forEach(board -> System.out.println(board));

		Page<Board> result = boardRepo.findByBnoGreaterThan(0L, paging);

		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());

		List<Board> list = result.getContent();
		list.forEach(board -> System.out.println(board));
	}

}
