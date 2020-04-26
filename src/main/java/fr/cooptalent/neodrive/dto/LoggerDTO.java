package fr.cooptalent.neodrive.dto;

import ch.qos.logback.classic.Logger;
import lombok.Data;

/**
 * View Model object for storing a Logback logger.
 */
@Data
public class LoggerDTO {

    private String name;

    private String level;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public LoggerDTO() {
        // Empty public constructor used by Jackson.
    }

}
