package com.example.calender.lv6.repository;

import com.example.calender.lv5.exception.ApiException;
import com.example.calender.lv6.entity.ScheduleLv6;
import com.example.calender.lv6.entity.UserLv6;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryLv6 {

    private static final String SELECT_QUERY = """
        SELECT\s
        *
        FROM schedule_challenge s
        INNER JOIN user u ON u.id = s.user_id
  \s""";

    private final JdbcTemplate jdbcTemplate;

    public ScheduleLv6 save(ScheduleLv6 schedule) {

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
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occur during Save Entity"));
    }

    public Optional<ScheduleLv6> findById(Integer id) {

        final String SELECT_SQL = SELECT_QUERY + " WHERE s.id = ?";

        List<ScheduleLv6> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<ScheduleLv6> findAll(Integer userId, LocalDate updatedDate, Pageable pageable) {

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

    private RowMapper<ScheduleLv6> scheduleRowMapper() {
        return (rs, rowNum) -> {

            UserLv6 user = new UserLv6(
                    rs.getInt("u.id"),
                    rs.getString("u.name"),
                    rs.getString("u.email"),
                   rs.getObject("u.created_datetime", LocalDateTime.class),
                   rs.getObject("u.updated_datetime", LocalDateTime.class)
            );

            return new ScheduleLv6(
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
