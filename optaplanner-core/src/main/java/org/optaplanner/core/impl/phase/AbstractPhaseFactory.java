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

package com.sankuai.optaplanner.core.impl.phase;

import com.sankuai.optaplanner.core.config.phase.PhaseConfig;
import com.sankuai.optaplanner.core.config.solver.termination.TerminationConfig;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.solver.termination.PhaseToSolverTerminationBridge;
import com.sankuai.optaplanner.core.impl.solver.termination.Termination;
import com.sankuai.optaplanner.core.impl.solver.termination.TerminationFactory;

public abstract class AbstractPhaseFactory<Solution_, PhaseConfig_ extends PhaseConfig<PhaseConfig_>>
        implements PhaseFactory<Solution_> {

    protected final PhaseConfig_ phaseConfig;

    public AbstractPhaseFactory(PhaseConfig_ phaseConfig) {
        this.phaseConfig = phaseConfig;
    }

    protected Termination<Solution_> buildPhaseTermination(HeuristicConfigPolicy<Solution_> configPolicy,
            Termination<Solution_> solverTermination) {
        TerminationConfig terminationConfig_ = phaseConfig.getTerminationConfig() == null ? new TerminationConfig()
                : phaseConfig.getTerminationConfig();
        // In case of childThread PART_THREAD, the solverTermination is actually the parent phase's phaseTermination
        // with the bridge removed, so it's ok to add it again
        Termination<Solution_> phaseTermination = new PhaseToSolverTerminationBridge<>(solverTermination);
        return TerminationFactory.<Solution_> create(terminationConfig_)
                .buildTermination(configPolicy, phaseTermination);
    }
}
