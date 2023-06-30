package com.goodday.proj.api.help.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpReply {
    long helpReNo;
    String helpReContent;
    Date helpReCreateDate;
    long memberNo;
    String nickname;
    long helpNo;
}
