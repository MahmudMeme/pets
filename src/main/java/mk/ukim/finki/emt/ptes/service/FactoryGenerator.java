package mk.ukim.finki.emt.ptes.service;

import java.time.LocalDate;

public interface FactoryGenerator {
    String generatePetName(int type);
    String generatePetDescription();
    LocalDate generateBirthDate();
    String generateFirstName();
    String generateLastName();
    String generateEmail();
}
