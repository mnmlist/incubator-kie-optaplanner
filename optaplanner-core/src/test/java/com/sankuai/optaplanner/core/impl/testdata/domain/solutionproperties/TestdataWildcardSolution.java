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

package com.sankuai.optaplanner.core.impl.testdata.domain.solutionproperties;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

import java.util.List;

@PlanningSolution
public class TestdataWildcardSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataWildcardSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataWildcardSolution.class, TestdataEntity.class);
    }

    private List<? extends TestdataValue> extendsValueList;
    private List<? super TestdataValue> supersValueList;
    private List<? extends TestdataEntity> extendsEntityList;

    private SimpleScore score;

    public TestdataWildcardSolution() {
    }

    public TestdataWildcardSolution(String code) {
        super(code);
    }

    @ValueRangeProvider(id = "valueRange")
    @ProblemFactCollectionProperty
    public List<? extends TestdataValue> getExtendsValueList() {
        return extendsValueList;
    }

    public void setExtendsValueList(List<? extends TestdataValue> extendsValueList) {
        this.extendsValueList = extendsValueList;
    }

    @ProblemFactCollectionProperty
    public List<? super TestdataValue> getSupersValueList() {
        return supersValueList;
    }

    public void setSupersValueList(List<? super TestdataValue> supersValueList) {
        this.supersValueList = supersValueList;
    }

    @PlanningEntityCollectionProperty
    public List<? extends TestdataEntity> getExtendsEntityList() {
        return extendsEntityList;
    }

    public void setExtendsEntityList(List<? extends TestdataEntity> extendsEntityList) {
        this.extendsEntityList = extendsEntityList;
    }

    @PlanningScore
    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

}
