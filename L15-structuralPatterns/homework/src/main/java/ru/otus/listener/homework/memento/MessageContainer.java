package ru.otus.listener.homework.memento;

import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;

public class MessageContainer {

    private final Map<Long, Message> messages = new HashMap<>();

    public void putMessage(Message message) {
        messages.put(message.getId(), message.toBuilder().build());
    }

    public Message getMessageById(long id) {
        return messages.get(id);
    }
}
