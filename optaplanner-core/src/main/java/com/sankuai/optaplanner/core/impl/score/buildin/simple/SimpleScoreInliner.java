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

package com.sankuai.optaplanner.core.impl.score.buildin.simple;

import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.impl.score.inliner.JustificationsSupplier;
import com.sankuai.optaplanner.core.impl.score.inliner.ScoreInliner;
import com.sankuai.optaplanner.core.impl.score.inliner.UndoScoreImpacter;
import com.sankuai.optaplanner.core.impl.score.inliner.WeightedScoreImpacter;

import java.util.Map;

public final class SimpleScoreInliner extends ScoreInliner<SimpleScore> {

    private int score;

    protected SimpleScoreInliner(Map<Constraint, SimpleScore> constraintIdToWeightMap, boolean constraintMatchEnabled) {
        super(constraintIdToWeightMap, constraintMatchEnabled, SimpleScore.ZERO);
    }

    @Override
    public WeightedScoreImpacter buildWeightedScoreImpacter(Constraint constraint) {
        SimpleScore constraintWeight = getConstraintWeight(constraint);
        int simpleConstraintWeight = constraintWeight.getScore();
        return WeightedScoreImpacter.of((int matchWeight, JustificationsSupplier justificationsSupplier) -> {
            int impact = simpleConstraintWeight * matchWeight;
            this.score += impact;
            UndoScoreImpacter undoScoreImpact = () -> this.score -= impact;
            if (!constraintMatchEnabled) {
                return undoScoreImpact;
            }
            Runnable undoConstraintMatch = addConstraintMatch(constraint, constraintWeight, SimpleScore.of(impact),
                    justificationsSupplier.get());
            return () -> {
                undoScoreImpact.run();
                undoConstraintMatch.run();
            };
        });
    }

    @Override
    public SimpleScore extractScore(int initScore) {
        return SimpleScore.ofUninitialized(initScore, score);
    }

    @Override
    public String toString() {
        return SimpleScore.class.getSimpleName() + " inliner";
    }

}
