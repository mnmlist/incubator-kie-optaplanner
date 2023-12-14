/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sankuai.optaplanner.examples.cloudbalancing.app;

import com.sankuai.optaplanner.core.api.solver.ProblemFactChange;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudComputer;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudProcess;
import com.sankuai.optaplanner.examples.cloudbalancing.persistence.CloudBalancingGenerator;
import com.sankuai.optaplanner.examples.cloudbalancing.swingui.realtime.AddComputerProblemFactChange;
import com.sankuai.optaplanner.examples.cloudbalancing.swingui.realtime.AddProcessProblemFactChange;
import com.sankuai.optaplanner.examples.cloudbalancing.swingui.realtime.DeleteComputerProblemFactChange;
import com.sankuai.optaplanner.examples.cloudbalancing.swingui.realtime.DeleteProcessProblemFactChange;
import com.sankuai.optaplanner.examples.common.app.RealTimePlanningTurtleTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloudBalancingRealTimePlanningTurtleTest extends RealTimePlanningTurtleTest<CloudBalance> {

    private CloudBalancingGenerator generator = new CloudBalancingGenerator(true);

    private List<CloudComputer> existingComputerList;
    private List<CloudProcess> existingProcessList;

    @Override
    protected String createSolverConfigResource() {
        return CloudBalancingApp.SOLVER_CONFIG;
    }

    @Override
    protected CloudBalance readProblem() {
        CloudBalance cloudBalance = generator.createCloudBalance(1200, 4800);
        existingComputerList = new ArrayList<>(cloudBalance.getComputerList());
        existingProcessList = new ArrayList<>(cloudBalance.getProcessList());
        return cloudBalance;
    }

    @Override
    protected ProblemFactChange<CloudBalance> nextProblemFactChange(Random random) {
        boolean capacityTooLow = existingComputerList.size() <= 20
                || existingComputerList.size() < existingProcessList.size() / 4;
        boolean capacityTooHigh = existingComputerList.size() > existingProcessList.size() / 2;
        if (random.nextBoolean()) {
            if (capacityTooLow || (!capacityTooHigh && random.nextBoolean())) {
                CloudComputer computer = generator.generateComputerWithoutId();
                existingComputerList.add(computer);
                return new AddComputerProblemFactChange(
                        computer);
            } else {
                return new DeleteComputerProblemFactChange(
                        existingComputerList.remove(random.nextInt(existingComputerList.size())));
            }
        } else {
            if (capacityTooHigh || (!capacityTooLow && random.nextBoolean())) {
                CloudProcess process = generator.generateProcessWithoutId();
                existingProcessList.add(process);
                return new AddProcessProblemFactChange(
                        process);
            } else {
                return new DeleteProcessProblemFactChange(
                        existingProcessList.remove(random.nextInt(existingProcessList.size())));
            }
        }
    }

}
