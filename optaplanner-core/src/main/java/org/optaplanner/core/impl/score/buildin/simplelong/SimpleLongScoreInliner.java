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

package com.sankuai.optaplanner.core.impl.score.buildin.simplelong;

import java.util.Map;

import com.sankuai.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.impl.score.inliner.JustificationsSupplier;
import com.sankuai.optaplanner.core.impl.score.inliner.ScoreInliner;
import com.sankuai.optaplanner.core.impl.score.inliner.UndoScoreImpacter;
import com.sankuai.optaplanner.core.impl.score.inliner.WeightedScoreImpacter;

public final class SimpleLongScoreInliner extends ScoreInliner<SimpleLongScore> {

    private long score;

    protected SimpleLongScoreInliner(Map<Constraint, SimpleLongScore> constraintToWeightMap,
            boolean constraintMatchEnabled) {
        super(constraintToWeightMap, constraintMatchEnabled, SimpleLongScore.ZERO);
    }

    @Override
    public WeightedScoreImpacter buildWeightedScoreImpacter(Constraint constraint) {
        SimpleLongScore constraintWeight = getConstraintWeight(constraint);
        long simpleConstraintWeight = constraintWeight.getScore();
        return WeightedScoreImpacter.of((long matchWeight, JustificationsSupplier justificationsSupplier) -> {
            long impact = simpleConstraintWeight * matchWeight;
            this.score += impact;
            UndoScoreImpacter undoScoreImpact = () -> this.score -= impact;
            if (!constraintMatchEnabled) {
                return undoScoreImpact;
            }
            Runnable undoConstraintMatch = addConstraintMatch(constraint, constraintWeight, SimpleLongScore.of(impact),
                    justificationsSupplier.get());
            return () -> {
                undoScoreImpact.run();
                undoConstraintMatch.run();
            };
        });
    }

    @Override
    public SimpleLongScore extractScore(int initScore) {
        return SimpleLongScore.ofUninitialized(initScore, score);
    }

    @Override
    public String toString() {
        return SimpleLongScore.class.getSimpleName() + " inliner";
    }

}
