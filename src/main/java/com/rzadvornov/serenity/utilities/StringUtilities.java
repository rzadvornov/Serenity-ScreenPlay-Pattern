package com.rzadvornov.serenity.utilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtilities {

    private static final String GMT_TIMEZONE = "GMT";
    private static final String TIMESTAMP_PATTERN = "E, dd MMM yyyy HH:mm:ss zzz";

    public static String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
        ZonedDateTime dateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of(GMT_TIMEZONE));
        return formatter.format(dateTime);
    }
}
