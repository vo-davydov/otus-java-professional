package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.listener.homework.memento.MessageContainer;
import ru.otus.model.Message;

import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final MessageContainer messageContainer;

    public HistoryListener() {
        this.messageContainer = new MessageContainer();
    }

    @Override
    public void onUpdated(Message msg) {
        messageContainer.putMessage(msg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageContainer.getMessageById(id));
    }
}
