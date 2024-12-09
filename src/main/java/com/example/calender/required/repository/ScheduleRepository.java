package com.example.calender.required.repository;

import com.example.calender.required.entity.Schedule;
import java.time.LocalDate;
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

    public Optional<Schedule> findById(Integer id) {

        final String SELECT_SQL = "SELECT * FROM schedule WHERE id = ?";

        List<Schedule> schedules = this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper(), id);

        return schedules.stream().findAny();
    }

    public List<Schedule> findAll(String author, LocalDate updatedDate) {

        String whereClause = makeWhereClause(author, updatedDate);

        final String SELECT_SQL = "SELECT * FROM schedule " + whereClause + " ORDER BY updated_datetime DESC";

        return this.jdbcTemplate.query(SELECT_SQL, scheduleRowMapper());
    }

    /**
     * 입력된 파라미터에 따라 WHERE 조건을 생성해준다.
     *
     * @param author : 작성자명
     * @param updatedDate  수정일 (YYYY-MM-DD)
     *
     * @Return
     * 1. author == null, updatedDate == null 인경우 <p>
     *     return WHERE 1=1
     * <p>
     * 2. author != null, updatedDate == null 인경우 <p>
     *     return WHERE 1=1 AND author == '작성자'
     * <p>
     * 3. author == null, updatedDate != null 인경우 <p>
     *    return WHERE 1=1 AND updatedDateTime BETWEEN'2024-12-10 00:00:00' AND '2024-12-11 00:00:00'
     * <p>
     * 4. author != null, updatedDate != null 인경우 <p>
     *     return WHERE 1=1 AND author == '작성자' AND updatedDateTime BETWEEN'2024-12-10 00:00:00' AND '2024-12-11 00:00:00'
     */
    private String makeWhereClause(String author, LocalDate updatedDate) {

        StringBuilder stringBuilder = new StringBuilder("WHERE 1=1");

        if (author != null) {
            stringBuilder.append(" AND author = '")
                    .append(author)
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

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getInt("id"),
                rs.getString("author"),
                rs.getString("password"),
                rs.getString("content"),
                rs.getObject("created_datetime", LocalDateTime.class),
                rs.getObject("updated_datetime", LocalDateTime.class)
        );
    }

    public void updateAuthorAndContentById(int id, String author, String content) {

        final String UPDATE_SQL = "UPDATE schedule SET author = ?, content = ? WHERE id = ?";

        this.jdbcTemplate.update(UPDATE_SQL, author, content, id);
    }

    public void deleteById(int id) {

        final String DELETE_SQL = "DELETE FROM schedule WHERE id = ?";

        this.jdbcTemplate.update(DELETE_SQL, id);
    }
}
