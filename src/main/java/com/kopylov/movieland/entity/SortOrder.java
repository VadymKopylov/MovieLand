package com.kopylov.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortOrder {

    ASC("asc"),
    DESC("desc");

    private final String value;
}