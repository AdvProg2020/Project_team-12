package logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void log(LogLevel logLevel, String content) {
        System.out.printf("%9s | %s | %s\n", logLevel, this.dtf.format(LocalDateTime.now()), content);
    }

}
