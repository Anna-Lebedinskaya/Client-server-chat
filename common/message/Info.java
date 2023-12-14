package ru.cft.focus.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Info extends Message {
    @JsonProperty
    private String text;

    public Info(String text) {
        super(System.currentTimeMillis());
        this.text = text;
    }

    @Override
    public MessageType getType() {
        return MessageType.INFO;
    }
}
