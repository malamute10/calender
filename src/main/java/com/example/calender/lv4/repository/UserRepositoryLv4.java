package com.example.calender.lv4.repository;

import com.example.calender.lv4.entity.UserLv4;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryLv4 {

    private final JdbcTemplate jdbcTemplate;

    public Optional<UserLv4> findById(Integer id) {

        final String SELECT_SQL = "SELECT * FROM user WHERE id = ?";

        List<UserLv4> users = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return users.stream().findAny();
    }

    private RowMapper<UserLv4> scheduleRowMapper() {
        return (rs, rowNum) -> new UserLv4(
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
