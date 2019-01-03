package cc.mrbird.telegram.entity;

import cc.mrbird.core.log.SystemErrorLog;
import cc.mrbird.telegram.service.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

/**
 * @author leo on 28/01/2018.
 */
public class MyBotListener extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramToken;

    @Value("${telegram.bot.user.name}")
    private String botUserName;

    @Value("${telegram.user.name}")
    private String userName;

    @Value("${telegram.user.chat.id}")
    private Long chatId;

    final private TelegramMessageService telegramMessageService;


    public MyBotListener(String telegramToken, String botUserName, String userName, Long chatId, DefaultBotOptions botOptions,
                            TelegramMessageService telegramMessageService) {
        super(botOptions);
        this.telegramToken = telegramToken;
        this.botUserName = botUserName;
        this.userName = userName;
        this.chatId = chatId;
        this.telegramMessageService = telegramMessageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        Message message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            if (!this.userName.equals(message.getChat().getUserName())) {
                SystemErrorLog.error("invalid message user:%s", message.getChat().getUserName());
                return;
            }
            String text = message.getText();
            SendMessage sendMessage = telegramMessageService.buildResponseMessage(text);
            if (sendMessage != null) {
                sendMessage.setChatId(message.getChatId());
                execute(sendMessage);
            }
        } else if (update.hasCallbackQuery()) {
            if (!this.userName.equals(update.getCallbackQuery().getFrom().getUserName())) {
                SystemErrorLog.error("invalid callback user:%s", update.getCallbackQuery().getFrom().getUserName());
                return;
            }
            EditMessageText editMessageText = telegramMessageService.buildEditMessage(update.getCallbackQuery().getMessage(),
                    update.getCallbackQuery().getData());
            if (editMessageText != null) {
                editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
                editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                execute(editMessageText);
            }
        }
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        if (method == null) {
            return null;
        }
        try {
            return super.execute(method);
        } catch (TelegramApiException e) {
            SystemErrorLog.error(e, "execute failed. method:%s", method);
        }
        return null;
    }

    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(message);
        execute(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }


}
