package entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "Builder", setterPrefix = "set")
public class User {

    private String fio;

    private String birthDate;

    private String phoneNumber;

    private String email;
}
