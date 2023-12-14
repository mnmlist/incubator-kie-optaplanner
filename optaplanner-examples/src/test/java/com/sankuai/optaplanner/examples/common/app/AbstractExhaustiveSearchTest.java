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

package com.sankuai.optaplanner.examples.common.app;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchType;
import com.sankuai.optaplanner.core.config.solver.SolverConfig;
import com.sankuai.optaplanner.core.config.solver.termination.TerminationConfig;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public abstract class AbstractExhaustiveSearchTest<Solution_>
        extends AbstractPhaseTest<Solution_, ExhaustiveSearchType> {

    @Override
    protected Stream<ExhaustiveSearchType> solverFactoryParams() {
        return Stream.of(ExhaustiveSearchType.values());
    }

    @Override
    protected SolverFactory<Solution_> buildSolverFactory(
            CommonApp<Solution_> commonApp,
            ExhaustiveSearchType exhaustiveSearchType) {
        String solverConfigResource = commonApp.getSolverConfigResource();
        SolverConfig solverConfig = SolverConfig.createFromXmlResource(solverConfigResource);
        solverConfig.setTerminationConfig(new TerminationConfig());
        ExhaustiveSearchPhaseConfig exhaustiveSearchPhaseConfig = new ExhaustiveSearchPhaseConfig();
        exhaustiveSearchPhaseConfig.setExhaustiveSearchType(exhaustiveSearchType);
        solverConfig.setPhaseConfigList(Arrays.asList(exhaustiveSearchPhaseConfig));
        return SolverFactory.create(solverConfig);
    }
}
