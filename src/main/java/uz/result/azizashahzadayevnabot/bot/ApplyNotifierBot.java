package uz.result.azizashahzadayevnabot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.result.azizashahzadayevnabot.model.Application;
import uz.result.azizashahzadayevnabot.model.Button;
import uz.result.azizashahzadayevnabot.model.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void sendCounter(List<Counter> counters, Long totalApplications) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        StringBuilder textBuilder = new StringBuilder();

        textBuilder.append("<b>Еженедельный отчет 📋</b>\n\n");

        if ((counters == null || counters.isEmpty() && totalApplications==0)) {
            textBuilder.append("<b>Поступившие заявки:</b> 0\n")
                    .append("<b>Общее количество звонков:</b> 0\n");
        } else {
            Map<Button, Long> buttonCountMap = new HashMap<>();
            long totalCalls = 0;

            for (Counter counter : counters) {
                Button button = counter.getSection();
                long countCall = counter.getCountCall() != null ? counter.getCountCall() : 0;

                buttonCountMap.put(button, buttonCountMap.getOrDefault(button, 0L) + countCall);
                totalCalls += countCall;
            }

            for (Map.Entry<Button, Long> entry : buttonCountMap.entrySet()) {
                textBuilder.append(String.format("<b>%s:</b> %d\n", getButtonDisplayName(entry.getKey()), entry.getValue()));
            }

            textBuilder.append(String.format("\n<b>Общее количество заявок:</b> %d\n", totalApplications));
            textBuilder.append(String.format("<b>Общее количество звонков:</b> %d\n", totalCalls));
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


    private String getButtonDisplayName(Button button) {
        return switch (button) {
            case MAKE_AN_APPOINTMENT -> "Запись на прием";
            case SIGN_UP -> "Регистрация";
            case TELEGRAM -> "Telegram";
            case CALL -> "Звонок";
            case TELEGRAM_FOOTER -> "Telegram (Footer)";
            case INSTAGRAM_FOOTER -> "Instagram (Footer)";
            default -> button.name();
        };
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
