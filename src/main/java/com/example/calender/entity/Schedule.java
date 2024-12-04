package com.example.calender.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private int id;
    private String author;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public Schedule(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public Schedule(int id, String author, String content, LocalDateTime createdDatetime,
                    LocalDateTime updatedDatetime) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdDatetime = createdDatetime;
        this.updatedDatetime = updatedDatetime;
    }
}
