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

package com.sankuai.optaplanner.core.impl.phase.scope;

import java.util.Random;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.score.director.InnerScoreDirector;

/**
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public abstract class AbstractMoveScope<Solution_> {

    protected final int moveIndex;
    protected final Move<Solution_> move;

    protected Score<?> score = null;

    public AbstractMoveScope(int moveIndex, Move<Solution_> move) {
        this.moveIndex = moveIndex;
        this.move = move;
    }

    public abstract AbstractStepScope<Solution_> getStepScope();

    public int getMoveIndex() {
        return moveIndex;
    }

    public Move<Solution_> getMove() {
        return move;
    }

    public <Score_ extends Score<Score_>> Score_ getScore() {
        return (Score_) score;
    }

    public <Score_ extends Score<Score_>> void setScore(Score_ score) {
        this.score = score;
    }

    // ************************************************************************
    // Calculated methods
    // ************************************************************************

    public int getStepIndex() {
        return getStepScope().getStepIndex();
    }

    public <Score_ extends Score<Score_>> InnerScoreDirector<Solution_, Score_> getScoreDirector() {
        return getStepScope().getScoreDirector();
    }

    public Solution_ getWorkingSolution() {
        return getStepScope().getWorkingSolution();
    }

    public Random getWorkingRandom() {
        return getStepScope().getWorkingRandom();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + getStepScope().getStepIndex() + "/" + moveIndex + ")";
    }

}
