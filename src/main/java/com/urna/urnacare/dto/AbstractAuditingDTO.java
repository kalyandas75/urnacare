package com.urna.urnacare.dto;

import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for DTO which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@Data
public abstract class AbstractAuditingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ReadOnlyProperty
    private String createdBy;

    @ReadOnlyProperty
    private Instant createdDate = Instant.now();

    private String lastModifiedBy;

    private Instant lastModifiedDate = Instant.now();

}
