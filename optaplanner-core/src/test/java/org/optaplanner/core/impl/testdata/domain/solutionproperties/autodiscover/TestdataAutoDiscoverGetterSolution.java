/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.core.impl.testdata.domain.solutionproperties.autodiscover;

import java.util.List;

import com.sankuai.optaplanner.core.api.domain.autodiscover.AutoDiscoverMemberType;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;
import com.sankuai.optaplanner.core.impl.testdata.domain.constraintconfiguration.TestdataConstraintConfiguration;

@PlanningSolution(autoDiscoverMemberType = AutoDiscoverMemberType.GETTER)
public class TestdataAutoDiscoverGetterSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataAutoDiscoverGetterSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataAutoDiscoverGetterSolution.class, TestdataEntity.class);
    }

    private TestdataConstraintConfiguration constraintConfiguration;
    private TestdataObject singleProblemFactField;
    private List<TestdataValue> problemFactListField;

    private List<TestdataEntity> entityListField;
    private TestdataEntity otherEntityField;

    private SimpleScore score;

    public TestdataAutoDiscoverGetterSolution() {
    }

    public TestdataAutoDiscoverGetterSolution(String code) {
        super(code);
    }

    public TestdataAutoDiscoverGetterSolution(String code, TestdataObject singleProblemFact,
            List<TestdataValue> problemFactList, List<TestdataEntity> entityList,
            TestdataEntity otherEntity) {
        super(code);
        this.singleProblemFactField = singleProblemFact;
        this.problemFactListField = problemFactList;
        this.entityListField = entityList;
        this.otherEntityField = otherEntity;
    }

    public TestdataConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }

    public void setConstraintConfiguration(TestdataConstraintConfiguration constraintConfiguration) {
        this.constraintConfiguration = constraintConfiguration;
    }

    public TestdataObject getSingleProblemFact() {
        return singleProblemFactField;
    }

    @ValueRangeProvider(id = "valueRange")
    public List<TestdataValue> getProblemFactList() {
        return problemFactListField;
    }

    public List<TestdataEntity> getEntityList() {
        return entityListField;
    }

    public TestdataEntity getOtherEntity() {
        return otherEntityField;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

}
