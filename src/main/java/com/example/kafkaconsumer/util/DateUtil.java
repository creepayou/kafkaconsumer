package com.example.kafkaconsumer.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility Class");
    }

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIMETZ_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String DATETIME_WITHOUT_SEC_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String HOUR_MINUTE_PATTERN = "HH:mm";
    private static final String HOUR_MINUTE_SECOND_PATTERN = "HH:mm:ss";

    public static String customFormatTimestamp(Timestamp timestamp, String pattern) {
        if (timestamp == null)
            return null;
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    public static String formatTimestamp(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, DATE_PATTERN);
    }

    public static String formatTimestamp2(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, DATETIME_PATTERN);
    }

    public static String formatTimestamp3(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, DATETIMETZ_PATTERN);
    }

    public static String formatTimestamp4(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, DATETIME_WITHOUT_SEC_PATTERN);
    }

    public static String formatTimestamp5(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, HOUR_MINUTE_PATTERN);
    }

    public static String formatTimestamp6(Timestamp timestamp) {
        return customFormatTimestamp(timestamp, HOUR_MINUTE_SECOND_PATTERN);
    }

    public static String customFormatTimestampWithTimezone(Timestamp timestamp, String timezone, String pattern) {
        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of(timezone))
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatTimestampWithTimezone(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, DATE_PATTERN);
    }

    public static String formatTimestampWithTimezone2(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, DATETIME_PATTERN);
    }

    public static String formatTimestampWithTimezone3(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, DATETIMETZ_PATTERN);
    }

    public static String formatTimestampWithTimezone4(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, DATETIME_WITHOUT_SEC_PATTERN);
    }

    public static String formatTimestampWithTimezone5(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, HOUR_MINUTE_PATTERN);
    }

    public static String formatTimestampWithTimezone6(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, HOUR_MINUTE_SECOND_PATTERN);
    }

    private static ZoneOffset convertToZoneOffset(ZoneId zoneId) {
        return zoneId.getRules().getOffset(Instant.now());
    }

    private static ZonedDateTime convertLocalDateTimeToTimezone(LocalDateTime localDateTime, String fromTimezone) {
        return localDateTime.atOffset(convertToZoneOffset(ZoneId.of(fromTimezone)))
                .toZonedDateTime();
    }

    private static Timestamp formatLocalDateTimeWithTimezone(LocalDateTime localDateTime, String fromTimezone) {
        ZonedDateTime zonedDateTime = convertLocalDateTimeToTimezone(localDateTime, fromTimezone);
        return Timestamp.valueOf(zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static Timestamp customFormatStringWithTimezone(String string, String fromTimezone, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.parse(string, DateTimeFormatter.ofPattern(pattern));
        return formatLocalDateTimeWithTimezone(localDateTime, fromTimezone);
    }

    public static Timestamp formatStringWithTimezone(String string, String fromTimezone) {
        LocalDate localDate = LocalDate.parse(string, DateTimeFormatter.ofPattern(DATE_PATTERN));
        return formatLocalDateTimeWithTimezone(localDate.atStartOfDay(), fromTimezone);
    }

    public static Timestamp formatStringWithTimezone2(String string, String fromTimezone) {
        return customFormatStringWithTimezone(string, fromTimezone, DATETIME_PATTERN);
    }

    public static Timestamp formatStringWithTimezone3(String string, String fromTimezone) {
        return customFormatStringWithTimezone(string, fromTimezone, DATETIMETZ_PATTERN);
    }

    public static Timestamp formatStringWithTimezone4(String string, String fromTimezone) {
        return customFormatStringWithTimezone(string, fromTimezone, DATETIME_WITHOUT_SEC_PATTERN);
    }

    public static Timestamp formatStringWithTimezone5(String string, String fromTimezone) {
        return customFormatStringWithTimezone(string, fromTimezone, HOUR_MINUTE_PATTERN);
    }

    public static String formatStringWithTimezone6(Timestamp timestamp, String timezone) {
        return customFormatTimestampWithTimezone(timestamp, timezone, HOUR_MINUTE_SECOND_PATTERN);
    }

    public static Timestamp customFormatString(String string, String pattern) {
        if (string == null)
            return null;
        try {
            return new Timestamp(new SimpleDateFormat(pattern).parse(string).getTime());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static Timestamp formatString(String string) {
        return customFormatString(string, DATE_PATTERN);
    }

    public static Timestamp formatString2(String string) {
        return customFormatString(string, DATETIME_PATTERN);
    }

    public static Timestamp formatString3(String string) {
        return customFormatString(string, DATETIMETZ_PATTERN);
    }

    public static Timestamp formatString4(String string) {
        return customFormatString(string, DATETIME_WITHOUT_SEC_PATTERN);
    }

    public static Timestamp formatString5(String string) {
        return customFormatString(string, HOUR_MINUTE_PATTERN);
    }

    public static Timestamp formatString6(String string) {
        return customFormatString(string, HOUR_MINUTE_SECOND_PATTERN);
    }

    /***
     *
     * @param timestamp
     * @param timestamp2
     * @return Calculation of timestamp - timestamp2 in days
     */
    public static Double countDiffDays(Timestamp timestamp, Timestamp timestamp2) {
        Long time1 = timestamp.getTime();
        Long time2 = timestamp2.getTime();

        Long diffMilis = time1 - time2;
        return diffMilis / (1000d * 60d * 60d * 24d);
    }

    public static boolean isTimestampSameDay(Timestamp timestamp, Timestamp timestamp2) {
        Long diffDays = Math.abs(countDiffDays(timestamp, timestamp2).longValue());

        return diffDays.equals(Long.valueOf(0));
    }

    public static Timestamp removeTimeFromTimestamp(Timestamp timestamp) {
        return formatString(formatTimestamp(timestamp));
    }

    public static Timestamp convertTimestampToTimezone(Timestamp timestamp, String timeZone) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = convertLocalDateTimeToTimezone(localDateTime, timeZone);

        return Timestamp.from(zonedDateTime.toInstant());
    }

    public static Timestamp minusMonth(Timestamp timestamp, Long minusValue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Timestamp.from(zonedDateTime.minusMonths(minusValue).toInstant());
    }

    public static Timestamp minusDays(Timestamp timestamp, Long minusValue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Timestamp.from(zonedDateTime.minusDays(minusValue).toInstant());
    }

    public static Timestamp plusDays(Timestamp timestamp, Long addValue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Timestamp.from(zonedDateTime.plusDays(addValue).toInstant());
    }

    public static Timestamp plusHours(Timestamp timestamp, Long addValue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Timestamp.from(zonedDateTime.plusHours(addValue).toInstant());
    }

    public static Timestamp minusHours(Timestamp timestamp, Long addValue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Timestamp.from(zonedDateTime.minusHours(addValue).toInstant());
    }

    public static boolean isValid(String dateStr, String pattern) {
        try {
            //DateTimeFormatter: y = year of era dan u = year. 2022-02-29 (true jika pattern y, sehingga perlu replace y = u agar aman)
            DateTimeFormatter.ofPattern(pattern.replace("y", "u")).withResolverStyle(ResolverStyle.STRICT)
                    .parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
