package com.interview.snl.bean;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Player {

    String id;

    int position;

    int score;
}
