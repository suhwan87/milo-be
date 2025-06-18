package com.example.milo_be.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

public class ChatDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChatRequest {
        private String message;
    }

    public static class ChatResponse {
        private String output;

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }
    }
}