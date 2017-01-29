package com.dfl.grevesapp.datamodels;

import lombok.Data;

/**
 * Created by Diogo Loureiro on 29/01/2017.
 * <p>
 * Cards to show in the recyclerView
 */

@Data
public class Card {

    private Object card;
    private CardType cardType;

    public Card(Object card, CardType cardType) {
        this.card = card;
        this.cardType = cardType;
    }

    /**
     * Card type
     */
    public enum CardType {
        LISBON_SUBWAY, STRIKE
    }
}
