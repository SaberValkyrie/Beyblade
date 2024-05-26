package com.example.demo.repository;

import com.example.demo.entity.TOP;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopRepository extends JpaRepository<TOP,Long> {
        @Query("select t from TOP t where t.user=:user")
        TOP getTopByUser(User user);

    @Query("select t from TOP t where t.win > 0 and t.top > 0 order by t.top asc")
    List<TOP> getTop();

    @Query("select t from TOP t where t.user.username=:user")
    TOP getTopByUserName(String user);

    @Query("select t.user from TOP t where t.top=:top")
    User getUserByTop(int top);
}
