package risk.common.entities.orders;

import risk.common.entities.Turn;
import risk.common.entities.orders.exceptions.OrderException;

import java.io.Serializable;

public abstract class Order implements Serializable {
    protected Turn turn;

    public Order(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    public abstract void check() throws OrderException;
}
