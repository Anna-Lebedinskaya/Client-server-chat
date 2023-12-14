package ru.cft.focus.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageNotification extends Message {
    @JsonProperty
    private String text;

    public MessageNotification(String text) {
        super(System.currentTimeMillis());
        this.text = text;
    }

    @Override
    public MessageType getType() {
        return MessageType.MESSAGE_NOTIFICATION;
    }
}
