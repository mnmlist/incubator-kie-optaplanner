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

package com.sankuai.optaplanner.core.impl.testdata.domain.planningid;

import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;

@PlanningSolution
public class TestdataStringPlanningIdSolution extends TestdataSolution {
    private List<String> stringValueList;
    private List<TestdataStringPlanningIdEntity> stringEntityList;

    @ValueRangeProvider(id = "stringValueRange")
    @ProblemFactCollectionProperty
    public List<String> getStringValueList() {
        return stringValueList;
    }

    public void setStringValueList(List<String> stringValueList) {
        this.stringValueList = stringValueList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataStringPlanningIdEntity> getStringEntityList() {
        return stringEntityList;
    }

    public void setStringEntityList(List<TestdataStringPlanningIdEntity> stringEntityList) {
        this.stringEntityList = stringEntityList;
    }
}
