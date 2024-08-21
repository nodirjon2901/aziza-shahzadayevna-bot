package uz.result.azizashahzadayevnabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.result.azizashahzadayevnabot.bot.ApplyNotifierBot;
import uz.result.azizashahzadayevnabot.model.Application;
import uz.result.azizashahzadayevnabot.repository.ApplicationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ApplyNotifierBot bot;

    public void save(Application application){
        application.setCreatedDate(LocalDateTime.now());
        Application save = applicationRepository.save(application);
        bot.handleSendApplicationMessage(save);
    }

}
