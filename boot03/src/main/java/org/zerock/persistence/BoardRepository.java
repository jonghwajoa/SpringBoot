package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
	public java.util.List<Board> findBoardByTitle(String title);

	public Collection<Board> findByWriter(String writer);

	// '%' + keyWord + '%'
	public Collection<Board> findByWriterContaining(String writer);

	// Or
	public Collection<Board> findByTitleOrContentContaining(String title, String content);

	// GreaterThan > ,,, LessThan <
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);

	// 역순정렬
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

	// bno > ? Order by DESC limit ?,?
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, org.springframework.data.domain.Pageable paging);

//	public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);

	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
}
