package cc.mrbird.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author leo on 30/01/2018.
 */
public interface TelegramMessageService {
    SendMessage buildResponseMessage(String message);

    EditMessageText buildEditMessage(Message inComeMessage, String callbackData);
}
