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

package com.sankuai.optaplanner.examples.cloudbalancing.app;

import com.sankuai.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import com.sankuai.optaplanner.examples.cloudbalancing.optional.score.CloudBalancingMapBasedEasyScoreCalculator;
import com.sankuai.optaplanner.examples.common.app.CommonApp;
import com.sankuai.optaplanner.examples.common.app.UnsolvedDirSolveAllTurtleTest;

public class CloudBalancingSolveAllTurtleTest extends UnsolvedDirSolveAllTurtleTest<CloudBalance> {

    @Override
    protected CommonApp<CloudBalance> createCommonApp() {
        return new CloudBalancingApp();
    }

    @Override
    protected Class<? extends EasyScoreCalculator> overwritingEasyScoreCalculatorClass() {
        return CloudBalancingMapBasedEasyScoreCalculator.class;
    }
}
