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

package com.sankuai.optaplanner.examples.nqueens.optional.score;

import static com.sankuai.optaplanner.core.api.score.stream.Joiners.equal;

import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.examples.nqueens.domain.Queen;

public class NQueensConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[] {
                horizontalConflict(factory),
                ascendingDiagonalConflict(factory),
                descendingDiagonalConflict(factory),
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    protected Constraint horizontalConflict(ConstraintFactory factory) {
        return factory
                .fromUniquePair(Queen.class, equal(Queen::getRowIndex))
                .penalize("Horizontal conflict", SimpleScore.ONE);
        // fromUniquePair() is syntactic sugar for from().join(..., lessThan(getId())
        //        return factory.from(Queen.class)
        //                .join(Queen.class,
        //                        equal(Queen::getRowIndex),
        //                        lessThan(Queen::getId))
        //                .penalize("Horizontal conflict", SimpleScore.ONE);
    }

    protected Constraint ascendingDiagonalConflict(ConstraintFactory factory) {
        return factory.fromUniquePair(Queen.class, equal(Queen::getAscendingDiagonalIndex))
                .penalize("Ascending diagonal conflict", SimpleScore.ONE);
    }

    protected Constraint descendingDiagonalConflict(ConstraintFactory factory) {
        return factory.fromUniquePair(Queen.class, equal(Queen::getDescendingDiagonalIndex))
                .penalize("Descending diagonal conflict", SimpleScore.ONE);
    }

}
