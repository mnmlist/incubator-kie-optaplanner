/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.core.impl.testdata.domain.constraintconfiguration.extended;

import com.sankuai.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;

@PlanningSolution
public class TestdataExtendedConstraintConfigurationSolution extends TestdataSolution {

    public static SolutionDescriptor<TestdataExtendedConstraintConfigurationSolution> buildExtendedSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataExtendedConstraintConfigurationSolution.class,
                TestdataEntity.class);
    }

    private TestdataExtendedConstraintConfiguration constraintConfiguration;

    public TestdataExtendedConstraintConfigurationSolution() {
    }

    public TestdataExtendedConstraintConfigurationSolution(String code) {
        super(code);
    }

    @ConstraintConfigurationProvider
    public TestdataExtendedConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }

    public void setConstraintConfiguration(TestdataExtendedConstraintConfiguration constraintConfiguration) {
        this.constraintConfiguration = constraintConfiguration;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
