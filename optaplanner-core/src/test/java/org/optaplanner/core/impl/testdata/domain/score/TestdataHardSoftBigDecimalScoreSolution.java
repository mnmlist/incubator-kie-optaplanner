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

package com.sankuai.optaplanner.core.impl.testdata.domain.score;

import java.util.ArrayList;
import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningSolution
public class TestdataHardSoftBigDecimalScoreSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataHardSoftBigDecimalScoreSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataHardSoftBigDecimalScoreSolution.class, TestdataEntity.class);
    }

    public static TestdataHardSoftBigDecimalScoreSolution generateSolution() {
        return generateSolution(5, 7);
    }

    public static TestdataHardSoftBigDecimalScoreSolution generateSolution(int valueListSize, int entityListSize) {
        TestdataHardSoftBigDecimalScoreSolution solution = new TestdataHardSoftBigDecimalScoreSolution("Generated Solution 0");
        List<TestdataValue> valueList = new ArrayList<>(valueListSize);
        for (int i = 0; i < valueListSize; i++) {
            TestdataValue value = new TestdataValue("Generated Value " + i);
            valueList.add(value);
        }
        solution.setValueList(valueList);
        List<TestdataEntity> entityList = new ArrayList<>(entityListSize);
        for (int i = 0; i < entityListSize; i++) {
            TestdataValue value = valueList.get(i % valueListSize);
            TestdataEntity entity = new TestdataEntity("Generated Entity " + i, value);
            entityList.add(entity);
        }
        solution.setEntityList(entityList);
        return solution;
    }

    private List<TestdataValue> valueList;
    private List<TestdataEntity> entityList;

    HardSoftBigDecimalScore score;

    public TestdataHardSoftBigDecimalScoreSolution() {
    }

    public TestdataHardSoftBigDecimalScoreSolution(String code) {
        super(code);
    }

    @ValueRangeProvider(id = "valueRange")
    @ProblemFactCollectionProperty
    public List<TestdataValue> getValueList() {
        return valueList;
    }

    public void setValueList(List<TestdataValue> valueList) {
        this.valueList = valueList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<TestdataEntity> entityList) {
        this.entityList = entityList;
    }

    @PlanningScore
    HardSoftBigDecimalScore getScore() {
        return score;
    }

    public void setScore(HardSoftBigDecimalScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
