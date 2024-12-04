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

    private final JdbcTemplate jdbcTemplate;

    public ScheduleLv3 save(ScheduleLv3 schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);

        jdbcInsert.withTableName("schedule_challenge")
                .usingColumns("user_id", "password", "content")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("content", schedule.getContent());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Error Occur during Save Entity"));
    }

    public Optional<ScheduleLv3> findById(Integer id) {

        final String SELECT_SQL = "SELECT * FROM schedule_challenge WHERE id = ?";

        List<ScheduleLv3> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<ScheduleLv3> findAll(Integer userId, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        String whereClause = makeWhereClause(userId, startUpdatedDatetime, endUpdatedDatetime);

        final String SELECT_SQL = "SELECT * FROM schedule_challenge " + whereClause + " ORDER BY updated_datetime DESC";

        return this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper());
    }

    private String makeWhereClause(Integer userId, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        StringBuilder stringBuilder = new StringBuilder("WHERE 1=1");

        if (userId != null) {
            stringBuilder.append(" AND user_id = '")
                    .append(userId)
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

    private RowMapper<ScheduleLv3> scheduleRowMapper() {
        return (rs, rowNum) ->
            new ScheduleLv3(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("password"),
                    rs.getString("content"),
                    rs.getObject("created_datetime", LocalDateTime.class),
                    rs.getObject("updated_datetime", LocalDateTime.class)
            );
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
