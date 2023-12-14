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

package com.sankuai.optaplanner.core.impl.testdata.domain.multientity;

import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningSolution
public class TestdataMultiEntitySolution extends TestdataObject {

    public static SolutionDescriptor<TestdataMultiEntitySolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataMultiEntitySolution.class,
                TestdataLeadEntity.class, TestdataHerdEntity.class);
    }

    private List<TestdataValue> valueList;
    private List<TestdataLeadEntity> leadEntityList;
    private List<TestdataHerdEntity> herdEntityList;

    private SimpleScore score;

    public TestdataMultiEntitySolution() {
    }

    public TestdataMultiEntitySolution(String code) {
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
    @ValueRangeProvider(id = "leadEntityRange")
    public List<TestdataLeadEntity> getLeadEntityList() {
        return leadEntityList;
    }

    public void setLeadEntityList(List<TestdataLeadEntity> leadEntityList) {
        this.leadEntityList = leadEntityList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataHerdEntity> getHerdEntityList() {
        return herdEntityList;
    }

    public void setHerdEntityList(List<TestdataHerdEntity> herdEntityList) {
        this.herdEntityList = herdEntityList;
    }

    @PlanningScore
    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
