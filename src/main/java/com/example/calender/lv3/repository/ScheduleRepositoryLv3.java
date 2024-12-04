package com.example.calender.lv3.repository;

import com.example.calender.lv3.entity.ScheduleLv3;
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
public class ScheduleRepositoryLv3 {

    private static final String SELECT_QUERY = """
        SELECT\s
        *
        FROM schedule_challenge s
        INNER JOIN user u ON u.id = s.user_id
  \s""";

    private final JdbcTemplate jdbcTemplate;

    public ScheduleLv3 save(ScheduleLv3 schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);

        jdbcInsert.withTableName("schedule_challenge")
                .usingColumns("user_id", "password", "content")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUser().getId());
        parameters.put("password", schedule.getPassword());
        parameters.put("content", schedule.getContent());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Error Occur during Save Entity"));
    }

    public Optional<ScheduleLv3> findById(Integer id) {

        final String SELECT_SQL = SELECT_QUERY + " WHERE s.id = ?";

        List<ScheduleLv3> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<ScheduleLv3> findAll(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        String whereClause = makeWhereClause(author, startUpdatedDatetime, endUpdatedDatetime);

        final String SELECT_SQL = SELECT_QUERY + whereClause + " ORDER BY s.updated_datetime DESC";

        return this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper());
    }

    private String makeWhereClause(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        StringBuilder stringBuilder = new StringBuilder("WHERE 1=1");

        if (author != null) {
            stringBuilder.append(" AND u.name = '")
                    .append(author)
                    .append("'");
        }

        if(startUpdatedDatetime != null) {
            stringBuilder.append(" AND s.updated_datetime >= '")
                    .append(startUpdatedDatetime)
                    .append("'");
        }

        if(endUpdatedDatetime != null) {
            stringBuilder.append(" AND s.updated_datetime < '")
                    .append(endUpdatedDatetime)
                    .append("'");
        }

        return stringBuilder.toString();
    }

    private RowMapper<ScheduleLv3> scheduleRowMapper() {
        return (rs, rowNum) -> {

            UserLv3 user = new UserLv3(rs.getInt("u.id"), rs.getString("u.name"), rs.getString("u.email"),
                                       rs.getObject("u.created_datetime", LocalDateTime.class),
                                       rs.getObject("u.updated_datetime", LocalDateTime.class));

            return new ScheduleLv3(
                    rs.getInt("s.id"),
                    user,
                    rs.getString("s.password"),
                    rs.getString("s.content"),
                    rs.getObject("s.created_datetime", LocalDateTime.class),
                    rs.getObject("s.updated_datetime", LocalDateTime.class)
            );
        };
    }

    public void updateAuthorAndContentById(int id, String author, String content) {

        final String UPDATE_SQL = "UPDATE schedule_challenge SET author = ?, content = ? WHERE id = ?";

        this.jdbcTemplate.update(UPDATE_SQL, author, content, id);
    }

    public void deleteById(int id) {

        final String DELETE_SQL = "DELETE FROM schedule_challenge WHERE id = ?";

        this.jdbcTemplate.update(DELETE_SQL, id);
    }
}
