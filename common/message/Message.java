package ru.cft.focus.common.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "AUTHORIZATION", value = AuthRequest.class),
        @JsonSubTypes.Type(name = "LOGOUT", value = LogoutRequest.class),
        @JsonSubTypes.Type(name = "MESSAGE_REQUEST", value = MessageRequest.class),
        @JsonSubTypes.Type(name = "MESSAGE_NOTIFICATION", value = MessageNotification.class),
        @JsonSubTypes.Type(name = "LIST_USERS", value = ActiveUsersList.class),
        @JsonSubTypes.Type(name = "CONNECTION_REFUSED", value = ConnectionRefused.class),
        @JsonSubTypes.Type(name = "CONNECTION_ACCEPTED", value = ConnectionAccepted.class),
        @JsonSubTypes.Type(name = "INFO", value = Info.class),
})
public abstract class Message implements Serializable {
    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("type")
    public abstract MessageType getType();
}

