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

package com.sankuai.optaplanner.spring.boot.autoconfigure.normal.domain;

import java.util.List;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;

@PlanningSolution
public class TestdataSpringSolution {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "valueRange")
    private List<String> valueList;
    @PlanningEntityCollectionProperty
    private List<TestdataSpringEntity> entityList;

    @PlanningScore
    private SimpleScore score;

    // ************************************************************************
    // Getters/setters
    // ************************************************************************

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public List<TestdataSpringEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<TestdataSpringEntity> entityList) {
        this.entityList = entityList;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }
}
