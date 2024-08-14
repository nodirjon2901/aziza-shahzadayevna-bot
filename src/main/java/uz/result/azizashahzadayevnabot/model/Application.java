package uz.result.azizashahzadayevnabot.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {

    String fullName;

    String phoneNum;

    String comment;

}
