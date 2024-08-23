package uz.result.azizashahzadayevnabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.result.azizashahzadayevnabot.bot.ApplyNotifierBot;
import uz.result.azizashahzadayevnabot.model.ApiResponse;
import uz.result.azizashahzadayevnabot.model.Button;
import uz.result.azizashahzadayevnabot.model.Counter;
import uz.result.azizashahzadayevnabot.repository.ApplicationRepository;
import uz.result.azizashahzadayevnabot.repository.CounterRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;

    private final ApplicationRepository applicationRepository;

    private final ApplyNotifierBot bot;

    private Map<Button, Long> currentCounterMap = new HashMap<>();

    public ResponseEntity<ApiResponse<?>> addCallNumber(Button button) {
        currentCounterMap.put(button, currentCounterMap.getOrDefault(button, 0L) + 1);

        ApiResponse<?> response = new ApiResponse<>();
        response.setMessage("Success. Number of calls for " + button.name() + ": " + currentCounterMap.get(button));
        return ResponseEntity.ok(response);
    }

//    @Scheduled(cron = "0 * * * * *")// every minute
    @Scheduled(cron = "0 0 0 * * SUN")
    public void checkAndSendCounter() throws Exception {
        Long applicationCount = applicationRepository.countApplicationInOneWeek(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        List<Counter> counterList=new ArrayList<>();
        for (Map.Entry<Button, Long> entry : currentCounterMap.entrySet()) {
            Button button = entry.getKey();
            Long countCall = entry.getValue();

            Counter counter = Counter.builder()
                    .section(button)
                    .countCall(countCall)
                    .build();
            counterList.add(counterRepository.save(counter));
        }
        bot.sendCounter(counterList,applicationCount);
        currentCounterMap.clear();
    }

}
