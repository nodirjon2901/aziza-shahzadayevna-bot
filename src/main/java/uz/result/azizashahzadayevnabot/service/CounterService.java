package uz.result.azizashahzadayevnabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.result.azizashahzadayevnabot.bot.ApplyNotifierBot;
import uz.result.azizashahzadayevnabot.model.ApiResponse;
import uz.result.azizashahzadayevnabot.model.Counter;
import uz.result.azizashahzadayevnabot.repository.ApplicationRepository;
import uz.result.azizashahzadayevnabot.repository.CounterRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;

    private final ApplicationRepository applicationRepository;

    private final ApplyNotifierBot bot;

    private Counter currentCounter;

    public ResponseEntity<ApiResponse<?>> addCallNumber() {
        if (currentCounter == null) {
            currentCounter = Counter.builder()
                    .countCall(1L)
                    .createdDate(LocalDateTime.now())
                    .build();
            counterRepository.save(currentCounter);
        } else {
            currentCounter.setCountCall(currentCounter.getCountCall() + 1);
            counterRepository.save(currentCounter);
        }

        ApiResponse<?> response = new ApiResponse<>();
        response.setMessage("Success. Number of calls per week: " + currentCounter.getCountCall());
        return ResponseEntity.ok(response);
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void checkAndSendCounter() throws Exception {
        Long applicationCount = applicationRepository.countApplicationInOneWeek(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        if (currentCounter == null){
            Counter counter = Counter.builder()
                    .countApplication(applicationCount)
                    .countCall(0L)
                    .build();
            currentCounter = counterRepository.save(counter);
        } else {
            if (applicationCount != null) {
                currentCounter.setCountApplication(applicationCount);
            }
            counterRepository.save(currentCounter);
        }

        counterRepository.update(currentCounter.getId(), applicationCount);
        bot.sendCounter(currentCounter);
        currentCounter = null;
    }


}
