package mk.ukim.finki.emt.ptes.service.Impl;

import com.github.javafaker.Faker;
import mk.ukim.finki.emt.ptes.service.FactoryGenerator;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class FactoryGeneratorImpl implements FactoryGenerator {
    private final static Faker faker = new Faker();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public String generatePetName(int type) {
        if (type == 0) {
            return faker.cat().name();
        }
        return faker.dog().name();
    }

    @Override
    public String generatePetDescription() {
        return faker.lorem().sentence(12);
    }

    @Override
    public String generateFirstName() {
        return faker.name().firstName();
    }

    @Override
    public String generateLastName() {
        return faker.name().lastName();
    }

    @Override
    public LocalDate generateBirthDate() {
        Date date = faker.date().birthday(5,23);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public String generateEmail() {
        return faker.internet().emailAddress();
    }
}
