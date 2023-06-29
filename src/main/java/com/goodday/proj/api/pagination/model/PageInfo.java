package com.goodday.proj.api.pagination.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageInfo {
    int currentPage;
    int listCount;
    int pageLimit;
    int maxPage;
    int startPage;
    int endPage;
    int boardLimit;
}
