package com.tidyjava.bp.util;

import java.time.LocalDate;
import java.util.Date;

import static java.time.ZoneId.systemDefault;

public class DateUtils {

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(systemDefault()).toInstant());
    }
}
