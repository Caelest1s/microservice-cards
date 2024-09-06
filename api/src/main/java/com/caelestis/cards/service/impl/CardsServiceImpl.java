package com.caelestis.cards.service.impl;

import com.caelestis.cards.constants.CardsConstants;
import com.caelestis.cards.dto.CardsDto;
import com.caelestis.cards.entity.Cards;
import com.caelestis.cards.exception.CardAlreadyExistsException;
import com.caelestis.cards.exception.ResourceNotFoundException;
import com.caelestis.cards.mapper.CardsMapper;
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

    @Override
    public CardsDto fetchCard(String numberMobile) {
        Cards cards = cardsRepository.findByMobileNumber(numberMobile).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", numberMobile));

        return CardsMapper.mapToCardsDto(cards, new CardsDto());
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

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String numberMobile) {
        Cards cards = cardsRepository.findByMobileNumber(numberMobile).orElseThrow(
                () -> new ResourceNotFoundException("Card", "numberMobile", numberMobile));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
