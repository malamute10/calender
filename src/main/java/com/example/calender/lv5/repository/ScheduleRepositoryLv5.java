package com.example.calender.lv5.repository;

import com.example.calender.lv5.entity.ScheduleLv5;
import com.example.calender.lv5.entity.UserLv5;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryLv5 {

    private static final String SELECT_QUERY = """
        SELECT\s
        *
        FROM schedule_challenge s
        INNER JOIN user u ON u.id = s.user_id
  \s""";

    private final JdbcTemplate jdbcTemplate;

    public ScheduleLv5 save(ScheduleLv5 schedule) {

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

    public Optional<ScheduleLv5> findById(Integer id) {

        final String SELECT_SQL = SELECT_QUERY + " WHERE s.id = ?";

        List<ScheduleLv5> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<ScheduleLv5> findAll(Integer userId, LocalDate updatedDate, Pageable pageable) {

        String whereClause = makeWhereClause(userId, updatedDate);

        final String SELECT_SQL = SELECT_QUERY + whereClause + " ORDER BY s.updated_datetime DESC LIMIT ? OFFSET ?";

        return this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    private String makeWhereClause(Integer userId, LocalDate updatedDate) {

        StringBuilder stringBuilder = new StringBuilder("WHERE 1=1");

        if (userId != null) {
            stringBuilder.append(" AND u.id = '")
                    .append(userId)
                    .append("'");
        }

        if(updatedDate != null) {

            LocalDateTime startUpdatedDatetime = updatedDate.atStartOfDay();
            LocalDateTime endUpdatedDatetime = updatedDate.plusDays(1).atStartOfDay();

            stringBuilder.append(" AND updated_datetime BETWEEN '")
                    .append(startUpdatedDatetime)
                    .append("' AND '")
                    .append(endUpdatedDatetime)
                    .append("'");
        }

        return stringBuilder.toString();
    }

    private RowMapper<ScheduleLv5> scheduleRowMapper() {
        return (rs, rowNum) -> {

            UserLv5 user = new UserLv5(
                    rs.getInt("u.id"),
                    rs.getString("u.name"),
                    rs.getString("u.email"),
                   rs.getObject("u.created_datetime", LocalDateTime.class),
                   rs.getObject("u.updated_datetime", LocalDateTime.class)
            );

            return new ScheduleLv5(
                    rs.getInt("s.id"),
                    user,
                    rs.getString("s.password"),
                    rs.getString("s.content"),
                    rs.getObject("s.created_datetime", LocalDateTime.class),
                    rs.getObject("s.updated_datetime", LocalDateTime.class)
            );
        };
    }

    public void updateContentById(int id, String content) {

        final String UPDATE_SQL = "UPDATE schedule_challenge SET content = ? WHERE id = ?";

        this.jdbcTemplate.update(UPDATE_SQL, content, id);
    }

    public void deleteById(int id) {

        final String DELETE_SQL = "DELETE FROM schedule_challenge WHERE id = ?";

        this.jdbcTemplate.update(DELETE_SQL, id);
    }
}
