package com.tidyjava.bp.util;

import java.time.LocalDate;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

public class DateUtils {

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atOffset(UTC).toInstant());
    }
}
