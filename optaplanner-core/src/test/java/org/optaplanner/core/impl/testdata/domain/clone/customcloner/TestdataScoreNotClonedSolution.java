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
package com.sankuai.optaplanner.core.impl.testdata.domain.clone.customcloner;

import java.util.Collections;
import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.cloner.SolutionCloner;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningSolution(solutionCloner = TestdataScoreNotClonedSolution.class)
public class TestdataScoreNotClonedSolution implements SolutionCloner<TestdataScoreNotClonedSolution> {

    @PlanningScore
    private SimpleScore score;
    @PlanningEntityProperty
    private TestdataEntity entity = new TestdataEntity("A");

    @ValueRangeProvider(id = "valueRange")
    @ProblemFactCollectionProperty
    public List<TestdataValue> valueRange() {
        return Collections.singletonList(new TestdataValue("1"));
    }

    @Override
    public TestdataScoreNotClonedSolution cloneSolution(TestdataScoreNotClonedSolution original) {
        TestdataScoreNotClonedSolution clone = new TestdataScoreNotClonedSolution();
        clone.entity.setValue(original.entity.getValue());
        return clone;
    }

}
