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

import com.sankuai.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import com.sankuai.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.partitionedsearch.PartitionedSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.phase.NoChangePhaseConfig;
import com.sankuai.optaplanner.core.config.phase.PhaseConfig;
import com.sankuai.optaplanner.core.config.phase.custom.CustomPhaseConfig;
import com.sankuai.optaplanner.core.impl.constructionheuristic.DefaultConstructionHeuristicPhaseFactory;
import com.sankuai.optaplanner.core.impl.exhaustivesearch.DefaultExhaustiveSearchPhaseFactory;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.localsearch.DefaultLocalSearchPhaseFactory;
import com.sankuai.optaplanner.core.impl.partitionedsearch.DefaultPartitionedSearchPhaseFactory;
import com.sankuai.optaplanner.core.impl.phase.custom.DefaultCustomPhaseFactory;
import com.sankuai.optaplanner.core.impl.solver.recaller.BestSolutionRecaller;
import com.sankuai.optaplanner.core.impl.solver.termination.Termination;

public interface PhaseFactory<Solution_> {

    static <Solution_> PhaseFactory<Solution_> create(PhaseConfig<?> phaseConfig) {
        if (LocalSearchPhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new DefaultLocalSearchPhaseFactory<>((LocalSearchPhaseConfig) phaseConfig);
        } else if (ConstructionHeuristicPhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new DefaultConstructionHeuristicPhaseFactory<>((ConstructionHeuristicPhaseConfig) phaseConfig);
        } else if (PartitionedSearchPhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new DefaultPartitionedSearchPhaseFactory<>((PartitionedSearchPhaseConfig) phaseConfig);
        } else if (CustomPhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new DefaultCustomPhaseFactory<>((CustomPhaseConfig) phaseConfig);
        } else if (ExhaustiveSearchPhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new DefaultExhaustiveSearchPhaseFactory<>((ExhaustiveSearchPhaseConfig) phaseConfig);
        } else if (NoChangePhaseConfig.class.isAssignableFrom(phaseConfig.getClass())) {
            return new NoChangePhaseFactory<>((NoChangePhaseConfig) phaseConfig);
        } else {
            throw new IllegalArgumentException(String.format("Unknown %s type: (%s).",
                    PhaseConfig.class.getSimpleName(), phaseConfig.getClass().getName()));
        }
    }

    Phase<Solution_> buildPhase(int phaseIndex, HeuristicConfigPolicy<Solution_> solverConfigPolicy,
            BestSolutionRecaller<Solution_> bestSolutionRecaller, Termination<Solution_> solverTermination);
}
