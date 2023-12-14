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

package com.sankuai.optaplanner.spring.boot.autoconfigure.chained.constraints;

import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.api.score.stream.Joiners;
import com.sankuai.optaplanner.spring.boot.autoconfigure.chained.domain.TestdataChainedSpringAnchor;
import com.sankuai.optaplanner.spring.boot.autoconfigure.chained.domain.TestdataChainedSpringEntity;

public class TestdataChainedSpringConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[] {
                factory.from(TestdataChainedSpringAnchor.class)
                        .ifNotExists(TestdataChainedSpringEntity.class,
                                Joiners.equal((anchor) -> anchor, TestdataChainedSpringEntity::getPrevious))
                        .penalize("Assign at least one entity to each anchor.", SimpleScore.ONE)
        };
    }

}
