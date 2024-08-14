package uz.result.azizashahzadayevnabot.bot;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ChatIdStorage {

    private static final Set<Long> groupChatId=new HashSet<>();

    public static void addGroupChatId(Long chatId) {
        groupChatId.add(chatId);
    }

    public static Set<Long> getGroupChatIds() {
        return groupChatId;
    }

    public static boolean isExistChatId(Long chatId){
        return groupChatId.contains(chatId);
    }

    public static void removeGroupChatId(Long chatId){
        groupChatId.remove(chatId);
    }

}
