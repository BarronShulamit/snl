package com.interview.snl.bean;

import com.interview.snl.enums.Status;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlayerStatus {

    String id;

    Status status;

    int score;

    boolean isRankedFirst;
}
