package com.example.calender.lv3.repository;

import com.example.calender.lv3.entity.UserLv3;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryLv3 {

    private final JdbcTemplate jdbcTemplate;

    public Optional<UserLv3> findById(Integer id) {

        final String SELECT_SQL = "SELECT * FROM user WHERE id = ?";

        List<UserLv3> users = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return users.stream().findAny();
    }

    private RowMapper<UserLv3> scheduleRowMapper() {
        return (rs, rowNum) -> new UserLv3(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getObject("created_datetime", LocalDateTime.class),
                rs.getObject("updated_datetime", LocalDateTime.class)
        );
    }

    public void updateNameById(int id, String name) {
        final String UPDATE_SQL = "UPDATE user SET name = ? WHERE id = ?";
        this.jdbcTemplate.update(UPDATE_SQL, name, id);
    }
}
