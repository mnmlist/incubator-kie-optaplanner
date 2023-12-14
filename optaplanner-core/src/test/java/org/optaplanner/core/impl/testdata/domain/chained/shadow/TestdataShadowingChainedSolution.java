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

package com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow;

import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;

@PlanningSolution
public class TestdataShadowingChainedSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataShadowingChainedSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataShadowingChainedSolution.class,
                TestdataShadowingChainedObject.class, TestdataShadowingChainedEntity.class);
    }

    private List<TestdataShadowingChainedAnchor> chainedAnchorList;
    private List<TestdataShadowingChainedEntity> chainedEntityList;

    private SimpleScore score;

    public TestdataShadowingChainedSolution() {
    }

    public TestdataShadowingChainedSolution(String code) {
        super(code);
    }

    @ValueRangeProvider(id = "chainedAnchorRange")
    @ProblemFactCollectionProperty
    public List<TestdataShadowingChainedAnchor> getChainedAnchorList() {
        return chainedAnchorList;
    }

    public void setChainedAnchorList(List<TestdataShadowingChainedAnchor> chainedAnchorList) {
        this.chainedAnchorList = chainedAnchorList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "chainedEntityRange")
    public List<TestdataShadowingChainedEntity> getChainedEntityList() {
        return chainedEntityList;
    }

    public void setChainedEntityList(List<TestdataShadowingChainedEntity> chainedEntityList) {
        this.chainedEntityList = chainedEntityList;
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
