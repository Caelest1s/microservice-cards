package com.caelestis.cards.controller;

import com.caelestis.cards.constants.CardsConstants;
import com.caelestis.cards.dto.CardsDto;
import com.caelestis.cards.dto.ResponseDto;
import com.caelestis.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CardsController {

    ICardsService iCardsService;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber){
        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobileNumber){
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }
}
