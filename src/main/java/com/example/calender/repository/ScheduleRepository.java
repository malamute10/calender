package com.example.calender.repository;

import com.example.calender.entity.Schedule;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public Schedule save(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);

        jdbcInsert.withTableName("schedule")
                .usingColumns("author", "password", "content")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", schedule.getAuthor());
        parameters.put("password", schedule.getPassword());
        parameters.put("content", schedule.getContent());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Error Occur during Save Entity"));
    }

    private Optional<Schedule> findById(Integer id) {

        final String SELECT_SQL = "SELECT * FROM schedule WHERE id = ?";

        List<Schedule> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<Schedule> findAll(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        String whereClause = makeWhereClause(author, startUpdatedDatetime, endUpdatedDatetime);

        final String SELECT_SQL = "SELECT * FROM schedule " + whereClause + " ORDER BY updated_datetime DESC";

        return this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper());
    }

    private String makeWhereClause(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        StringBuilder stringBuilder = new StringBuilder("WHERE 1=1");

        if (author != null) {
            stringBuilder.append(" AND author = '")
                    .append(author)
                    .append("'");
        }

        if(startUpdatedDatetime != null) {
            stringBuilder.append(" AND updated_datetime >= '")
                    .append(startUpdatedDatetime)
                    .append("'");
        }

        if(endUpdatedDatetime != null) {
            stringBuilder.append(" AND updated_datetime < '")
                    .append(endUpdatedDatetime)
                    .append("'");
        }

        return stringBuilder.toString();
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getInt("id"),
                rs.getString("author"),
                rs.getString("content"),
                rs.getObject("created_datetime", LocalDateTime.class),
                rs.getObject("updated_datetime", LocalDateTime.class)
        );
    }
}
