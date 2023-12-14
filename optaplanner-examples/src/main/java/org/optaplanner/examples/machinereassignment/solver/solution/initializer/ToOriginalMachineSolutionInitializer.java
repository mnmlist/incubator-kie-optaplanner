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

package com.sankuai.optaplanner.examples.machinereassignment.solver.solution.initializer;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.impl.phase.custom.CustomPhaseCommand;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MachineReassignment;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrMachine;
import com.sankuai.optaplanner.examples.machinereassignment.domain.MrProcessAssignment;

public class ToOriginalMachineSolutionInitializer implements CustomPhaseCommand<MachineReassignment> {

    @Override
    public void changeWorkingSolution(ScoreDirector<MachineReassignment> scoreDirector) {
        MachineReassignment machineReassignment = scoreDirector.getWorkingSolution();
        initializeProcessAssignmentList(scoreDirector, machineReassignment);
    }

    private void initializeProcessAssignmentList(ScoreDirector<MachineReassignment> scoreDirector,
            MachineReassignment machineReassignment) {
        for (MrProcessAssignment processAssignment : machineReassignment.getProcessAssignmentList()) {
            MrMachine originalMachine = processAssignment.getOriginalMachine();
            MrMachine machine = originalMachine == null ? machineReassignment.getMachineList().get(0) : originalMachine;
            scoreDirector.beforeVariableChanged(processAssignment, "machine");
            processAssignment.setMachine(machine);
            scoreDirector.afterVariableChanged(processAssignment, "machine");
            scoreDirector.triggerVariableListeners();
        }
    }

}
