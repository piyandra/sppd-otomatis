package org.sppd.otomatis.util;

import java.time.LocalDateTime;
import java.util.Map;

public class LocalDateTimeStart {

    public Map.Entry<LocalDateTime, LocalDateTime> getStartAndEndOfDay(LocalDateTime localDateTime) {
        LocalDateTime startDay = localDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endDay = localDateTime.toLocalDate().atTime(23, 59, 59);
        return Map.entry(startDay, endDay);
    }
}
