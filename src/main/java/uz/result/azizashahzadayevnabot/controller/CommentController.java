package uz.result.azizashahzadayevnabot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.result.azizashahzadayevnabot.model.Application;
import uz.result.azizashahzadayevnabot.service.ApplicationService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public void create(
            @RequestBody Application application
    ) {
        applicationService.save(application);
    }

}
