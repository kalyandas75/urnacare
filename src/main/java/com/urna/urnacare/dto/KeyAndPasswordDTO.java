package com.urna.urnacare.dto;

import lombok.Data;

/**
 * View Model object for storing the user's key and password.
 */
@Data
public class KeyAndPasswordDTO {

    private String key;

    private String newPassword;

}
