package com.caelestis.cards.service;

import com.caelestis.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String numberMobile);

    CardsDto fetchCard(String numberMobile);
}
