package com.lqkj.web.gnsc.modules.user.dao;

import com.lqkj.web.gnsc.modules.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface CcrUserRepository extends JpaRepository<User, Long> {

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")
    })
    @Query("select u from User u where u.userCode=:name")
    User findByUserName(@Param("name") String name);

    @Query("select u.userGroup,count(u) from User u group by u.userGroup order by u.userGroup")
    List<Object[]> userStatistics();

}
