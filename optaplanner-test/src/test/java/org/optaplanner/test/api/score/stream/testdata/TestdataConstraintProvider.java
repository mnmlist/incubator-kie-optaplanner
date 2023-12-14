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

package com.sankuai.optaplanner.test.api.score.stream.testdata;

import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.api.score.stream.Joiners;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.planningid.TestdataStringPlanningIdEntity;

public class TestdataConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                penalizeEveryEntity(constraintFactory),
                rewardEveryEntity(constraintFactory),
                differentStringEntityHaveDifferentValues(constraintFactory),
        };
    }

    public Constraint penalizeEveryEntity(ConstraintFactory constraintFactory) {
        return constraintFactory.from(TestdataEntity.class)
                .penalize("Penalize every entity", SimpleScore.ONE);
    }

    public Constraint rewardEveryEntity(ConstraintFactory constraintFactory) {
        return constraintFactory.from(TestdataEntity.class)
                .reward("Reward every entity", SimpleScore.ONE);
    }

    public Constraint differentStringEntityHaveDifferentValues(ConstraintFactory constraintFactory) {
        return constraintFactory
                .fromUniquePair(TestdataStringPlanningIdEntity.class, Joiners.equal(TestdataStringPlanningIdEntity::getValue))
                .penalize("Different String Entity Have Different Values", SimpleScore.ONE);
    }

}
