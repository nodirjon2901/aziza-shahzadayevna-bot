package uz.result.azizashahzadayevnabot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.result.azizashahzadayevnabot.bot.ApplyNotifierBot;
import uz.result.azizashahzadayevnabot.model.Application;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final ApplyNotifierBot bot;

    @PostMapping("/create")
    public void create(
            @RequestBody Application application
    ) {
        bot.handleSendApplicationMessage(application);
    }

}
