/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.pas.app;

import com.sankuai.optaplanner.core.config.solver.EnvironmentMode;
import com.sankuai.optaplanner.examples.common.app.SolverPerformanceTest;
import com.sankuai.optaplanner.examples.pas.domain.PatientAdmissionSchedule;

import java.util.stream.Stream;

public class PatientAdmissionSchedulePerformanceTest extends SolverPerformanceTest<PatientAdmissionSchedule> {

    private static final String UNSOLVED_DATA_FILE = "data/pas/unsolved/testdata01.xml";

    @Override
    protected PatientAdmissionScheduleApp createCommonApp() {
        return new PatientAdmissionScheduleApp();
    }

    @Override
    protected Stream<TestData> testData() {
        return Stream.of(
                testData(UNSOLVED_DATA_FILE, "0hard/0medium/-7458soft", EnvironmentMode.REPRODUCIBLE),
                // TODO Adding overconstrained functionality reduced Solver efficiency, so this ran too long (over 1 minute):
                //                testData(UNSOLVED_DATA_FILE, "0hard/0medium/-7172soft", EnvironmentMode.REPRODUCIBLE),
                testData(UNSOLVED_DATA_FILE, "0hard/0medium/-7408soft", EnvironmentMode.FAST_ASSERT)
        // TODO Adding overconstrained functionality reduced Solver efficiency, so this ran too long (over 1 minute):
        //                testData(UNSOLVED_DATA_FILE, "0hard/0medium/-7192soft", EnvironmentMode.FAST_ASSERT)
        );
    }
}
