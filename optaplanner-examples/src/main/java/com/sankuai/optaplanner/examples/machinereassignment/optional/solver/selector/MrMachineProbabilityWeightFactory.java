/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.machinereassignment.optional.solver.selector;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionProbabilityWeightFactory;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MachineReassignment;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrMachine;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrProcess;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrProcessAssignment;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrResource;

public class MrMachineProbabilityWeightFactory
        implements SelectionProbabilityWeightFactory<MachineReassignment, MrProcessAssignment> {

    @Override
    public double createProbabilityWeight(ScoreDirector<MachineReassignment> scoreDirector,
            MrProcessAssignment processAssignment) {
        MachineReassignment machineReassignment = scoreDirector.getWorkingSolution();
        MrMachine machine = processAssignment.getMachine();
        // TODO reuse usage calculated by of the ScoreCalculator which is a delta
        long[] usage = new long[machineReassignment.getResourceList().size()];
        for (MrProcessAssignment someProcessAssignment : machineReassignment.getProcessAssignmentList()) {
            if (someProcessAssignment.getMachine() == machine) {
                MrProcess process = someProcessAssignment.getProcess();
                for (MrResource resource : machineReassignment.getResourceList()) {
                    usage[resource.getIndex()] += process.getUsage(resource);
                }
            }
        }
        double sum = 0.0;
        for (MrResource resource : machineReassignment.getResourceList()) {
            double available = (machine.getMachineCapacity(resource).getSafetyCapacity() - usage[resource.getIndex()]);
            sum += (available * available);
        }
        return sum + 1.0;
    }

}
