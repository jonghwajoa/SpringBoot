package org.zerock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.FreeBoard;
import org.zerock.domain.FreeBoardReply;
import org.zerock.persistence.FreeBoardReplyRepository;
import org.zerock.persistence.FreeBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class FreeBoardTests {

	@Autowired
	FreeBoardRepository boardRepo;

	@Autowired
	FreeBoardReplyRepository boardReplyRepo;

	public void insertDummy() {
		IntStream.range(1, 200).forEach(i -> {
			FreeBoard board = new FreeBoard();
			board.setTitle("Free Board...." + i);
			board.setContent("Free Content...." + i);
			board.setWriter("user" + i % 10);

			boardRepo.save(board);
		});
	}

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

		// Hibernate: select freeboard0_.bno as bno1_1_0_, freeboard0_.content as
		// content2_1_0_, freeboard0_.regdate as regdate3_1_0_, freeboard0_.title as
		// title4_1_0_, freeboard0_.updatedate as updateda5_1_0_, freeboard0_.writer as
		// writer6_1_0_ from tbl_freeboards freeboard0_ where freeboard0_.bno=?
		// Hibernate: insert into tbl_free_replies (board_bno, reply, replydate,
		// replyer, updatedate) values (?, ?, ?, ?, ?)
	}

	public void insertReply1way() {
		FreeBoard board = new FreeBoard();
		board.setBno(199L);
		FreeBoardReply reply = new FreeBoardReply();
		reply.setReply("REPLY....");
		reply.setReplyer("replyer00");
		reply.setBoard(board);
		boardReplyRepo.save(reply);

		// Hibernate: insert into tbl_free_replies (board_bno, reply, replydate,
		// replyer, updatedate) values (?, ?, ?, ?, ?)
	}

	public void testList1() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + ": " + board.getTitle() + ":" + board.getReplies().size());
		});
	}

	public void testList2() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + ": " + board.getTitle() + ":" + board.getReplies().size());
		});
	}

	@Test
	public void testList3() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		boardRepo.getPage(page).forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}
}
