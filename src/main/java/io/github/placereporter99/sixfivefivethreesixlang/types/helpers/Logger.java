package io.github.placereporter99.sixfivefivethreesixlang.types.helpers;

import java.util.*;
import java.time.*;

enum LogType {
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    FATAL,
}

record LogLine(LogType type, String line, long timeNanos) {}

public class Logger {
    private final List<LogLine> logs = new ArrayList<>();

    private long getTimestamp() {
        Instant now = Instant.now();
        return (now.getEpochSecond() * 1000000000L) + now.getNano();
    }

    public void debug(String message) {
        logs.add(new LogLine(LogType.DEBUG, message, getTimestamp()));
    }

    public void info(String message) {
        logs.add(new LogLine(LogType.INFO, message, getTimestamp()));
    }

    public void warning(String message) {
        logs.add(new LogLine(LogType.WARNING, message, getTimestamp()));
    }

    public void error(String message) {
        logs.add(new LogLine(LogType.ERROR, message, getTimestamp()));
    }

    public void fatal(String message) {
        logs.add(new LogLine(LogType.FATAL, message, getTimestamp()));
    }

    public List<LogLine> getLogsSnapshot() {
        return List.copyOf(logs);
    }

    public static List<LogLine> compose(List<LogLine>... a) {
        List<LogLine> newLogs = new ArrayList<>();
        Arrays.stream(a).forEach(newLogs::addAll);
        newLogs.sort(Comparator.comparing(LogLine::timeNanos));
        return newLogs;
    }
}
