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
package com.sankuai.optaplanner.core.impl.testdata.domain.solutionproperties.invalid;

import java.util.Collection;
import java.util.List;

import com.sankuai.optaplanner.core.api.domain.autodiscover.AutoDiscoverMemberType;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningSolution(autoDiscoverMemberType = AutoDiscoverMemberType.FIELD)
public class TestdataUnknownFactTypeSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataUnknownFactTypeSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataUnknownFactTypeSolution.class,
                TestdataEntity.class);
    }

    private List<TestdataValue> valueList;
    private List<TestdataEntity> entityList;
    private SimpleScore score;
    // this can't work with autodiscovery because it's difficult/impossible to resolve the type of collection elements
    private MyStringCollection facts;

    public TestdataUnknownFactTypeSolution() {
    }

    public TestdataUnknownFactTypeSolution(String code) {
        super(code);
    }

    @ValueRangeProvider(id = "valueRange")
    public List<TestdataValue> getValueList() {
        return valueList;
    }

    public static interface MyStringCollection extends Collection<String> {

    }
}
