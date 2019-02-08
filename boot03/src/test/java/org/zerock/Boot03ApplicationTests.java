package org.zerock;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.domain.QBoard;
import org.zerock.persistence.BoardRepository;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;

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

	public void testByTitle17() {
		boardRepo.findByTitle2("17").forEach(arr -> System.out.println(Arrays.toString(arr)));
	}

	public void testByTitle20() {
		boardRepo.findByTitle3("20").forEach(arr -> System.out.println(Arrays.toString(arr)));
	}

	public void testByPaging() {
		org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);
		boardRepo.findBypage(pageable).forEach(board -> System.out.println(board));

	}

	public void testPredicate() {
		String type = "t";
		String keyword = "17";
		com.querydsl.core.BooleanBuilder builder = new com.querydsl.core.BooleanBuilder();

		QBoard board = QBoard.board;

		if (type.equals("t")) {
			builder.and(board.title.like("%" + keyword + "%"));
		}

		builder.and(board.bno.gt(0L));
		org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);

		Page<Board> result = boardRepo.findAll(builder, pageable);

		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());

		List<Board> list = result.getContent();
		list.forEach(b -> System.out.println(b));
	}

}
