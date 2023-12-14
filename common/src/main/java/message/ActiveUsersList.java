package ru.cft.focus.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActiveUsersList extends Message {
    @JsonProperty
    private List<String> userList;

    @JsonCreator
    public ActiveUsersList(List<String> userList) {
        super(System.currentTimeMillis());
        this.userList = userList;
    }

    @Override
    public MessageType getType() {
        return MessageType.LIST_USERS;
    }
}
