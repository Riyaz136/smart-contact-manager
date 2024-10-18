package com.scm.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private MessageType type;

    // No-argument constructor
    public Message() {
        this.type = MessageType.blue; // Default value
    }

    // All-argument constructor
    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    // Getters
    public String getContent() {
        return content;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    // Builder class
    public static class Builder {
        private String content;
        private MessageType type = MessageType.blue; // Default value

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public Message build() {
            return new Message(content, type);
        }
    }

    public static Builder builder() {
        return new Builder();
    }


}
