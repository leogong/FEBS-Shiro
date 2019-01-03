package cc.mrbird.telegram.service.impl;

import cc.mrbird.core.domain.entity.DomainDO;
import cc.mrbird.core.domain.entity.DomainStatus;
import cc.mrbird.core.domain.service.DomainMgrService;
import cc.mrbird.core.log.SystemErrorLog;
import cc.mrbird.core.util.ShellUtil;
import cc.mrbird.telegram.service.TelegramMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cc.mrbird.telegram.constants.MessageConstants.COUNT;
import static cc.mrbird.telegram.constants.MessageConstants.DOMAIN_CALLBACK;
import static cc.mrbird.telegram.constants.MessageConstants.DONE;
import static cc.mrbird.telegram.constants.MessageConstants.EMPTY;
import static cc.mrbird.telegram.constants.MessageConstants.FAILED;
import static cc.mrbird.telegram.constants.MessageConstants.LOAD;
import static cc.mrbird.telegram.constants.MessageConstants.REBOOT;
import static cc.mrbird.telegram.constants.MessageConstants.START;
import static cc.mrbird.telegram.constants.MessageConstants.START_RESPONSE;

/**
 * @author leo on 30/01/2018.
 */
@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @Value("${net.cn.mi.domain.detail}")
    private String domainDetail;

    private final static Pattern STATUS_DOMAIN_PATTERN = Pattern.compile("/([a-z]+)(_[a-z0-9]+)*$");

    @Autowired
    private DomainMgrService domainMgrService;

    @Override
    public SendMessage buildResponseMessage(String inComeMessage) {
        if (StringUtils.isEmpty(inComeMessage)) {
            return null;
        }
        Matcher matcher = STATUS_DOMAIN_PATTERN.matcher(inComeMessage);
        if (matcher.find() && DomainStatus.of(matcher.group(1)) != null) {
            String message = EMPTY;
            String suffix = null;
            Integer price = 100;
            Integer prefixLength = null;
            String[] split = inComeMessage.split("_");
            if (split.length > 1) {
                suffix = split[1];
            }
            if (split.length > 2) {
                price = Integer.valueOf(split[2]);
            }
            if (split.length > 3) {
                prefixLength = Integer.valueOf(split[3]);
            }
            List<DomainDO> domainDOS = domainMgrService.queryDomainList(1, 3, suffix, DomainStatus.of(matcher.group(1)), price, prefixLength);
            SendMessage sendMessage = new SendMessage();
            if (CollectionUtils.isNotEmpty(domainDOS)) {

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);

                domainDOS.forEach(domainDO -> {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
                    // Set the keyboard to the markup
                    rowInline.add(new InlineKeyboardButton().setText(domainDO.getDomain()).setCallbackData(EMPTY).setUrl(domainDetail + domainDO.getDomain()));
                    rowInline.add(new InlineKeyboardButton().setText(String.valueOf(domainDO.getPrice())).setCallbackData(EMPTY));
                    rowInline2.add(new InlineKeyboardButton().setText(DomainStatus.EXPENSIVE.name())
                        .setCallbackData(DOMAIN_CALLBACK + " " + domainDO.getDomain() + " " + DomainStatus.EXPENSIVE.getCode()));
                    rowInline2.add(new InlineKeyboardButton().setText(DomainStatus.YES.name())
                        .setCallbackData(DOMAIN_CALLBACK + " " + domainDO.getDomain() + " " + DomainStatus.YES.getCode()));
                    rowInline3.add(
                        new InlineKeyboardButton().setText(DomainStatus.NO.name()).setCallbackData(DOMAIN_CALLBACK + " " + domainDO.getDomain() + " " + DomainStatus.NO.getCode()));
                    rowInline3.add(new InlineKeyboardButton().setText(DomainStatus.HOLD.name())
                        .setCallbackData(DOMAIN_CALLBACK + " " + domainDO.getDomain() + " " + DomainStatus.HOLD.getCode()));
                    rowsInline.add(rowInline);
                    rowsInline.add(rowInline2);
                    rowsInline.add(rowInline3);
                });
                message = DONE;
            }
            return sendMessage.setText("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + message);

        }
        if (inComeMessage.equals(START)) {
            return new SendMessage().setText(START_RESPONSE);
        }
        if (inComeMessage.equals(LOAD)) {
            domainMgrService.loadDomain();
            return new SendMessage().setText(DONE);
        }
        if (inComeMessage.equals(REBOOT)) {
            String message = FAILED;
            try {
                ShellUtil.reboot();
                message = DONE;
            } catch (Exception e) {
                SystemErrorLog.error(e, "execute reboot failed.");
            }
            return new SendMessage().setText(message);
        }
        if (inComeMessage.equals(COUNT)) {
            Integer count = domainMgrService.count();
            return new SendMessage().setText(String.valueOf(count));
        }
        return null;
    }

    @Override
    public EditMessageText buildEditMessage(Message inComeMessage, String callbackData) {
        if (inComeMessage == null || StringUtils.isEmpty(callbackData)) {
            return null;
        }
        if (callbackData.startsWith(DOMAIN_CALLBACK)) {
            String[] split = callbackData.split(" ");
            //noinspection AlibabaUndefineMagicConstant
            if (split.length != 3) {
                SystemErrorLog.error("invalid call back. %s", callbackData);
            }
            domainMgrService.updateStatus(split[1], DomainStatus.of(Integer.valueOf(split[2])));
        }
        return null;
    }
}
