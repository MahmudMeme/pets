package mk.ukim.finki.emt.ptes.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    private String name;
    private String surname;
    private String email;
    private int budget;
}
