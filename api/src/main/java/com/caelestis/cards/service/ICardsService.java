package com.caelestis.cards.service;

import com.caelestis.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String numberMobile);

    CardsDto fetchCard(String numberMobile);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String numberMobile);
}
