/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.core.impl.testdata.domain.shadow.inverserelation;

import com.sankuai.optaplanner.core.api.domain.entity.PlanningEntity;
import com.sankuai.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;

import java.util.ArrayList;
import java.util.Collection;

@PlanningEntity
public class TestdataInverseRelationValue extends TestdataObject {

    private Collection<TestdataInverseRelationEntity> entities = new ArrayList<>();

    public TestdataInverseRelationValue() {
    }

    public TestdataInverseRelationValue(String code) {
        super(code);
    }

    @InverseRelationShadowVariable(sourceVariableName = "value")
    public Collection<TestdataInverseRelationEntity> getEntities() {
        return entities;
    }

    public void setEntities(Collection<TestdataInverseRelationEntity> entities) {
        this.entities = entities;
    }

}
