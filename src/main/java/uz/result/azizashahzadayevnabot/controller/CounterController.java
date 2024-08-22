package uz.result.azizashahzadayevnabot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.result.azizashahzadayevnabot.model.ApiResponse;
import uz.result.azizashahzadayevnabot.model.Button;
import uz.result.azizashahzadayevnabot.service.CounterService;

@RestController
@RequestMapping("/api/counter")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService service;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addCallNum(
            @RequestParam(value = "button")Button button
            ) {
        return service.addCallNumber(button);
    }

}
