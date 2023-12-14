package com.sankuai.optaplanner.examples.coachshuttlegathering.domain.solver;

import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.Bus;
import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.Shuttle;

public class ShuttlePassengerCountTotalUpdatingVariableListener extends BusPassengerCountTotalUpdatingVariableListener {

    @Override
    protected boolean isCorrectBusInstance(Bus bus) {
        return bus instanceof Shuttle;
    }
}
