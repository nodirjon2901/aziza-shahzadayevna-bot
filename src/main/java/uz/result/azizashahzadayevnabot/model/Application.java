package uz.result.azizashahzadayevnabot.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {

    String fullName;

    String phoneNum;

    String comment;

    @CreationTimestamp
    LocalDateTime createdDate;

}
