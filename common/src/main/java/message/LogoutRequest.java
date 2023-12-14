package ru.cft.focus.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutRequest extends Message {
    @JsonProperty
    private String userName;

    public LogoutRequest(String userName) {
        super(System.currentTimeMillis());
        this.userName = userName;
    }

    @Override
    public MessageType getType() {
        return MessageType.LOGOUT;
    }
}
