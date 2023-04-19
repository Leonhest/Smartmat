package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.FridgeMemberMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FridgeService implements IFridgeService{

    private final FridgeRepository fridgeRepository;
    private final FridgeMemberRepository fridgeMemberRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(FridgeService.class);


    @PostConstruct
    public void init() {
        fridgeRepository.dropTrigger();
        fridgeRepository.createTrigger();
    }

    @Override
    public void initializeFridge(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Fridge savedFridge = fridgeRepository.save(new Fridge("Home Fridge"));
        //TODO: add ability to change fridge name

        fridgeMemberRepository.save(FridgeMemberMapper.toFridgeMember(user, savedFridge, true));
    }

}