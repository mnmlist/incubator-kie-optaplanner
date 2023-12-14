/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.projectjobscheduling.app;

import java.util.stream.Stream;

import com.sankuai.optaplanner.core.config.solver.EnvironmentMode;
import com.sankuai.optaplanner.examples.common.app.SolverPerformanceTest;
import com.sankuai.optaplanner.examples.projectjobscheduling.domain.Schedule;

public class ProjectJobSchedulingPerformanceTest extends SolverPerformanceTest<Schedule> {

    private static final String UNSOLVED_DATA_FILE = "data/projectjobscheduling/unsolved/A-4.xml";

    @Override
    protected ProjectJobSchedulingApp createCommonApp() {
        return new ProjectJobSchedulingApp();
    }

    @Override
    protected Stream<TestData> testData() {
        return Stream.of(
                testData(UNSOLVED_DATA_FILE, "[0]hard/[-152/-69]soft", EnvironmentMode.REPRODUCIBLE),
                testData(UNSOLVED_DATA_FILE, "[0]hard/[-193/-92]soft", EnvironmentMode.FAST_ASSERT));
    }
}
