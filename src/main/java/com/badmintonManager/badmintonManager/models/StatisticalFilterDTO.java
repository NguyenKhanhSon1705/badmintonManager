package com.badmintonManager.badmintonManager.models;

import java.sql.Time;
import java.time.LocalDateTime;

public class StatisticalFilterDTO {
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    public StatisticalFilterDTO(LocalDateTime timeStart, LocalDateTime timeEnd) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
    public LocalDateTime getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }
    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }
}
