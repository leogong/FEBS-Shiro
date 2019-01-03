package cc.mrbird.telegram.config;

import cc.mrbird.telegram.entity.MyBotListener;
import cc.mrbird.telegram.service.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author leo on 21/01/2018.
 */
@Configuration
@Profile("prod")
@SuppressWarnings("Duplicates")
public class ProdTelegramSpringConfig {

    static {
        ApiContextInitializer.init();
    }

    @Value("${telegram.bot.token}")
    private String telegramToken;

    @Value("${telegram.bot.user.name}")
    private String botUserName;

    @Value("${telegram.user.name}")
    private String userName;

    @Value("${telegram.user.chat.id}")
    private Long chatId;

    private final TelegramMessageService telegramMessageService;

    @Autowired
    public ProdTelegramSpringConfig(TelegramMessageService telegramMessageService) {
        this.telegramMessageService = telegramMessageService;
    }


    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiRequestException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        MyBotListener bot = myBotListener();
        telegramBotsApi.registerBot(bot);
        bot.sendMessage("startup");
        return telegramBotsApi;
    }

    @Bean
    public MyBotListener myBotListener() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        return new MyBotListener(telegramToken, botUserName, userName, chatId, botOptions, telegramMessageService);
    }

}
