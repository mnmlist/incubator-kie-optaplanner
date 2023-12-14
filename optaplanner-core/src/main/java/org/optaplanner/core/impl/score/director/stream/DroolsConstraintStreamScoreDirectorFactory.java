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

package com.sankuai.optaplanner.core.impl.score.director.stream;

import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.score.stream.drools.DroolsConstraintFactory;
import com.sankuai.optaplanner.core.impl.score.stream.drools.DroolsConstraintSessionFactory;

public final class DroolsConstraintStreamScoreDirectorFactory<Solution_, Score_ extends Score<Score_>>
        extends AbstractConstraintStreamScoreDirectorFactory<Solution_, Score_> {

    private final DroolsConstraintSessionFactory<Solution_, Score_> constraintSessionFactory;
    private final Constraint[] constraints;

    public DroolsConstraintStreamScoreDirectorFactory(SolutionDescriptor<Solution_> solutionDescriptor,
            ConstraintProvider constraintProvider, boolean droolsAlphaNetworkCompilationEnabled) {
        super(solutionDescriptor);
        DroolsConstraintFactory<Solution_> constraintFactory =
                new DroolsConstraintFactory<>(solutionDescriptor, droolsAlphaNetworkCompilationEnabled);
        constraints = buildConstraints(constraintProvider, constraintFactory);
        this.constraintSessionFactory =
                (DroolsConstraintSessionFactory<Solution_, Score_>) constraintFactory.buildSessionFactory(constraints);
    }

    @Override
    public DroolsConstraintStreamScoreDirector<Solution_, Score_> buildScoreDirector(boolean lookUpEnabled,
            boolean constraintMatchEnabledPreference) {
        return new DroolsConstraintStreamScoreDirector<>(this, lookUpEnabled, constraintMatchEnabledPreference);
    }

    public DroolsConstraintSessionFactory.SessionDescriptor<Score_>
            newConstraintStreamingSession(boolean constraintMatchEnabled, Solution_ workingSolution) {
        return constraintSessionFactory.buildSession(constraintMatchEnabled, workingSolution);
    }

    public DroolsConstraintSessionFactory<Solution_, Score_> getConstraintSessionFactory() {
        return constraintSessionFactory;
    }

    @Override
    public Constraint[] getConstraints() {
        return constraints;
    }
}
