package org.zerock.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

	@Query("SELECT m.uid, count(p) from Member m left outer join Profile p "
			+ " on m.uid = p.member where m.uid = ?1 group by m")
	public List<Object[]> getMemberWithProfileCount(String uid);

	@Query("SELECT m, p FROM Member m LEFT OUTER JOIN Profile p "
			+ "ON m.uid = p.member where m.uid = ?1 and p.current = true")
	public List<Object[]> getMemberWithProfile(String uid);


}
