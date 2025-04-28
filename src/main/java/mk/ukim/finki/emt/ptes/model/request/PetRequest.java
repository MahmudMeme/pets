package mk.ukim.finki.emt.ptes.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class PetRequest {
    private String name;
    private String description;
    private Date birthDate;
    private int price;
    private int petType;
    private int rating;
}
