package com.sankuai.optaplanner.examples.coachshuttlegathering.domain.solver;

import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.Bus;
import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.Coach;

public class CoachPassengerCountTotalUpdatingVariableListener extends BusPassengerCountTotalUpdatingVariableListener {

    @Override
    protected boolean isCorrectBusInstance(Bus bus) {
        return bus instanceof Coach;
    }
}
