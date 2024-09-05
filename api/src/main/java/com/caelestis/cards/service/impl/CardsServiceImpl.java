package com.caelestis.cards.service.impl;

import com.caelestis.cards.constants.CardsConstants;
import com.caelestis.cards.entity.Cards;
import com.caelestis.cards.exception.CardAlreadyExistsException;
import com.caelestis.cards.repository.CardsRepository;
import com.caelestis.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    CardsRepository cardsRepository;

    @Override
    public void createCard(String numberMobile) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(numberMobile);
        if (optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber: " + numberMobile);
        }
        cardsRepository.save(createNewCards(numberMobile));
    }

    private Cards createNewCards(String mobileNumber) {
        Cards entity = new Cards();
        entity.setMobileNumber(mobileNumber);
        entity.setCardNumber(createCardNumber());
        entity.setCardType(CardsConstants.CREDIT_CARD);
        entity.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        entity.setAmountUsed(0);
        entity.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("Cards Microservice");
        return entity;
    }

    private String createCardNumber(){
        return Long.toString(100000000000L + new Random().nextInt(900000000));
    }
}
