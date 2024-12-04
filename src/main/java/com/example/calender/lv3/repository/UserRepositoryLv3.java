package com.example.calender.lv3.repository;

import com.example.calender.lv3.entity.UserLv3;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryLv3 {

    private final JdbcTemplate jdbcTemplate;

    public UserLv3 save(UserLv3 user) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);

        jdbcInsert.withTableName("user")
                .usingColumns("name", "email")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Error Occur during Save Entity"));
    }

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
}
