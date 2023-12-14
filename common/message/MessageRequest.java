package ru.cft.focus.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequest extends Message {
    @JsonProperty
    private String text;
    @JsonProperty
    private String userName;

    public MessageRequest(String text, String userName) {
        super(System.currentTimeMillis());
        this.text = text;
        this.userName = userName;
    }

    @Override
    public MessageType getType() {
        return MessageType.MESSAGE_REQUEST;
    }
}
