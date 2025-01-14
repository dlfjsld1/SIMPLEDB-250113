package com.ll.simpleDb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Article {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean isBlind;

    public static Article fromMap(Map<String, Object> map) {
        return new Article(
            (long) map.get("id"),
            (String) map.get("title"),
            (String) map.get("body"),
            (LocalDateTime) map.get("createdDate"),
            (LocalDateTime) map.get("modifiedDate"),
            (boolean) map.get("isBlind")
        );
    }
}
