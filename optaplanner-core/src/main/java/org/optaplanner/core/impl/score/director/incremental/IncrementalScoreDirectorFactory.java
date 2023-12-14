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

package com.sankuai.optaplanner.core.impl.score.director.incremental;

import java.util.function.Supplier;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.api.score.calculator.IncrementalScoreCalculator;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.score.director.AbstractScoreDirectorFactory;
import com.sankuai.optaplanner.core.impl.score.director.ScoreDirectorFactory;

/**
 * Incremental implementation of {@link ScoreDirectorFactory}.
 *
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 * @param <Score_> the score type to go with the solution
 * @see IncrementalScoreDirector
 * @see ScoreDirectorFactory
 */
public class IncrementalScoreDirectorFactory<Solution_, Score_ extends Score<Score_>>
        extends AbstractScoreDirectorFactory<Solution_, Score_> {

    private final Supplier<IncrementalScoreCalculator<Solution_, Score_>> incrementalScoreCalculatorSupplier;

    public IncrementalScoreDirectorFactory(SolutionDescriptor<Solution_> solutionDescriptor,
            Supplier<IncrementalScoreCalculator<Solution_, Score_>> incrementalScoreCalculatorSupplier) {
        super(solutionDescriptor);
        this.incrementalScoreCalculatorSupplier = incrementalScoreCalculatorSupplier;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @Override
    public IncrementalScoreDirector<Solution_, Score_> buildScoreDirector(
            boolean lookUpEnabled, boolean constraintMatchEnabledPreference) {
        return new IncrementalScoreDirector<>(this,
                lookUpEnabled, constraintMatchEnabledPreference, incrementalScoreCalculatorSupplier.get());
    }

}
