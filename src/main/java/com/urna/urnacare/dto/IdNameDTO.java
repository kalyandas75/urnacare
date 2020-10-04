package com.urna.urnacare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class IdNameDTO implements Serializable {
    private Long id;
    private String name;
}
