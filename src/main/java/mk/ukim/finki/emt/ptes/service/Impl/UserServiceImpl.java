package mk.ukim.finki.emt.ptes.service.Impl;

import mk.ukim.finki.emt.ptes.model.HistoryLog;
import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.enums.PetType;
import mk.ukim.finki.emt.ptes.model.exceptions.PetsNotFound;
import mk.ukim.finki.emt.ptes.model.exceptions.UsersNotFound;
import mk.ukim.finki.emt.ptes.repository.HistoryLogRepository;
import mk.ukim.finki.emt.ptes.repository.PetRepository;
import mk.ukim.finki.emt.ptes.repository.UserRepository;
import mk.ukim.finki.emt.ptes.service.FactoryGenerator;
import mk.ukim.finki.emt.ptes.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FactoryGenerator factoryGenerator;
    private final PetRepository petRepository;
    private final HistoryLogRepository historyLogRepository;

    public UserServiceImpl(UserRepository userRepository, FactoryGenerator factoryGenerator, PetRepository petRepository, HistoryLogRepository historyLogRepository) {
        this.userRepository = userRepository;
        this.factoryGenerator = factoryGenerator;
        this.petRepository = petRepository;
        this.historyLogRepository = historyLogRepository;
    }

    @Override
    public User createFactory() {
        User user = new User();
        user.setName(factoryGenerator.generateFirstName());
        user.setSurname(factoryGenerator.generateLastName());
        user.setEmail(factoryGenerator.generateEmail());
        Random random = new Random();
        user.setBudget(random.nextInt(100));
        user.setPets(new ArrayList<>());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> createUsers(int number) {
        if (number <= 0 || number > 20) {
            number = 20;
        }
        List<User> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            users.add(createFactory());
        }
        return users;
    }

    @Override
    public List<User> buy() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UsersNotFound();
        }
        List<Pet> pets = petRepository.findAll();
        if (pets.isEmpty()) {
            throw new PetsNotFound();
        }

//        int purchases=0;
        Set<User> uniqueUsers = new HashSet<>();

        for (User user : users) {
            for (Pet pet : pets) {
                if (user.getBudget() <= pet.getPrice() || pet.getOwner() != null) {
                    continue;
                } else {
                    int userBudget = user.getBudget();
                    int petPrice = pet.getPrice();

                    user.setBudget(userBudget - petPrice);
                    pet.setOwner(user);
                    user.getPets().add(pet);

                    if (pet.getPetType() == PetType.CAT) {
                        System.out.println("meow cat " + pet.getName() + " has owner " + user.getName());
                    } else {
                        System.out.println("woof, dog " + pet.getName() + " has owner " + user.getName());
                    }
                    uniqueUsers.add(user);

                    userRepository.save(user);
                    petRepository.save(pet);
                }
            }
        }
        addHistoryLog(users.size(), uniqueUsers.size());
        return uniqueUsers.stream().toList();
    }

    @Override
    public void addHistoryLog(int allUsers, int uniqueUsers) {
        HistoryLog historyLog = new HistoryLog();
        historyLog.setDate(Date.from(Instant.now()));
        historyLog.setNumberOfUsersSuccess(uniqueUsers);
        historyLog.setNumberOfUsersFailed(allUsers - uniqueUsers);

        historyLogRepository.save(historyLog);
    }
}
