package com.sviridov.resource.error_handling;

import lombok.Data;

import java.util.*;

@Data
public class InstructionsError {
    private int status;
    private List<String> messages;
    private Date timestamp;

    public InstructionsError(int status, String... messages) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(messages));
        this.timestamp = new Date();
    }

    public InstructionsError(int status, Collection<String> messages) {
        this.status = status;
        this.messages = new ArrayList<>(messages);
        this.timestamp = new Date();
    }
}