package uz.result.azizashahzadayevnabot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.result.azizashahzadayevnabot.model.Application;
import uz.result.azizashahzadayevnabot.model.Counter;

@Component
@RequiredArgsConstructor
public class ApplyNotifierBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Value("${group.chatId}")
    private String groupChatId;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void handleSendApplicationMessage(Application application) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        sendMessage.setParseMode("Markdown");
        sendMessage.setText(
                "*Новый комментарий*\n\n" +
                        "\uD83D\uDC64 *ФИО*: " + application.getFullName() + "\n" +
                        "\uD83D\uDCDE *Номер телефона*: " + application.getPhoneNum() + "\n" +
                        "\uD83D\uDCAC *Комментарий*: " + application.getComment() + "\n"
        );
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCounter(Counter counter) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(groupChatId);
        StringBuilder textBuilder = new StringBuilder();

        if (counter == null) {
            textBuilder.append("<b>Еженедельный отчет 📋</b>\n\n")
                    .append("<b>1. Поступившие заявки:</b> 0\n")
                    .append("<b>2. Количество звонков:</b> 0\n");
        } else {
            if (counter.getCountCall() != null) {
                if (counter.getCountApplication() != null) {
                    textBuilder.append("<b>Еженедельный отчет 📋</b>\n\n")
                            .append(String.format("<b>1. Поступившие заявки:</b> %d\n", counter.getCountApplication()))
                            .append(String.format("<b>2. Количество звонков:</b> %d\n", counter.getCountCall()));
                } else {
                    textBuilder.append("<b>Еженедельный отчет 📋</b>\n\n")
                            .append("<b>1. Поступившие заявки:</b> 0\n")
                            .append(String.format("<b>2. Количество звонков:</b> %d\n", counter.getCountCall()));
                }
            }
        }

        String text = textBuilder.toString();

        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


// Agar ma'lumotlar bazasi ham qo'shilsa bu narsa ish beradi.
//    Faqat bu narsa uchun avtorizatsiya qilish kerak chunki
//    botning vazifasi saytdagi commentlarni telegram guruhga jo'natish
//    agarda reklama bo'lsa shu usulni qo'llash qo'l keladi
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            if (!update.getMessage().isUserMessage()) {
//                Long chatId = update.getMessage().getChatId();
//                System.out.println(chatId);
//                if (ChatIdStorage.isExistChatId(chatId))
//                    ChatIdStorage.removeGroupChatId(chatId);
//                else
//                    ChatIdStorage.addGroupChatId(chatId);
//                System.out.println(ChatIdStorage.getGroupChatIds());
//            }
//        }
//
//    }
//
//    public void handleSendApplicationMessage(Application application) {
//        for (Long groupChatId : ChatIdStorage.getGroupChatIds()) {
//            System.out.println(groupChatId);
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(groupChatId);
//            sendMessage.setParseMode("Markdown");
//            sendMessage.setText(
//                    "*Новый комментарий*\n\n" +
//                            "\uD83D\uDC64 *ФИО*: " + application.getFullName() + "\n" +
//                            "\uD83D\uDCDE *Номер телефона*: " + application.getPhoneNum() + "\n" +
//                            "\uD83D\uDCAC *Комментарий*: " + application.getComment() + "\n"
//            );
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
