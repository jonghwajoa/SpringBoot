package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
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

	@Query("select b.bno, b.title, b.writer, b.regdate "
			+ "from Board b where b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);

	@Query(value = "select bno, title, writer from tbl_boards where title like CONCAT('%',?1, '%') and bno > 0 order by bno desc", nativeQuery = true)
	public List<Object[]> findByTitle3(String title);

	@Query("select b from Board b where b.bno > 0 order by b.bno desc")
	public List<Board> findBypage(Pageable pageable);
}
