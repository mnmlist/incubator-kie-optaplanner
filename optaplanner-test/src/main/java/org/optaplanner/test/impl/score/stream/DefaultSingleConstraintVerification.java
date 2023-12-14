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

package com.sankuai.optaplanner.test.impl.score.stream;

import java.util.Objects;

import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.impl.score.director.InnerScoreDirector;
import com.sankuai.optaplanner.core.impl.score.director.stream.AbstractConstraintStreamScoreDirectorFactory;
import com.sankuai.optaplanner.test.api.score.stream.SingleConstraintVerification;

public final class DefaultSingleConstraintVerification<Solution_, Score_ extends Score<Score_>>
        implements SingleConstraintVerification<Solution_> {

    private final AbstractConstraintStreamScoreDirectorFactory<Solution_, Score_> scoreDirectorFactory;
    private final SessionBasedAssertionBuilder<Solution_, Score_> sessionBasedAssertionBuilder;

    protected DefaultSingleConstraintVerification(
            AbstractConstraintStreamScoreDirectorFactory<Solution_, Score_> scoreDirectorFactory) {
        this.scoreDirectorFactory = scoreDirectorFactory;
        this.sessionBasedAssertionBuilder = SessionBasedAssertionBuilder.create(scoreDirectorFactory);
    }

    @Override
    public final DefaultSingleConstraintAssertion<Solution_, Score_> given(Object... facts) {
        return sessionBasedAssertionBuilder.singleConstraintGiven(facts);
    }

    @Override
    public final DefaultSingleConstraintAssertion<Solution_, Score_> givenSolution(Solution_ solution) {
        try (InnerScoreDirector<Solution_, Score_> scoreDirector = scoreDirectorFactory.buildScoreDirector(true, true)) {
            scoreDirector.setWorkingSolution(Objects.requireNonNull(solution));
            return new DefaultSingleConstraintAssertion<>(scoreDirectorFactory, scoreDirector.calculateScore(),
                    scoreDirector.getConstraintMatchTotalMap(), scoreDirector.getIndictmentMap());
        }
    }

}
