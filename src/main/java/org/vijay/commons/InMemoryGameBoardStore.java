package org.vijay.commons;

import org.springframework.stereotype.Component;
import org.vijay.domains.GameBoard;

import java.util.HashMap;
import java.util.Map;

/**
 * A Simple HashMap store to keep the game board, this can be replaced with proper db later.
 */
@Component
public class InMemoryGameBoardStore {
    private Map<Integer, GameBoard> store;

    public Map<Integer, GameBoard> getStore() {
        if (store == null) {
            store = new HashMap<>();
        }
        return store;
    }
}
