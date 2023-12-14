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

package com.sankuai.optaplanner.core.config.solver.testutil.calculator;

import java.util.HashSet;
import java.util.Set;

import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

public class AbstractTestdataDifferentValuesCalculator implements EasyScoreCalculator<TestdataSolution, SimpleScore> {

    private boolean isCorrupted;
    private int numOfCalls;

    AbstractTestdataDifferentValuesCalculator(boolean isCorrupted) {
        this.isCorrupted = isCorrupted;
    }

    @Override
    public SimpleScore calculateScore(TestdataSolution solution) {
        int score = 0;
        Set<TestdataValue> alreadyUsedValues = new HashSet<>();

        for (TestdataEntity entity : solution.getEntityList()) {
            if (entity.getValue() != null) {
                TestdataValue value = entity.getValue();
                if (alreadyUsedValues.contains(value)) {
                    score -= 1;
                } else {
                    alreadyUsedValues.add(value);
                }
            }
        }
        if (isCorrupted) {
            numOfCalls += 1;
            return SimpleScore.of(score - numOfCalls);
        } else {
            return SimpleScore.of(score);
        }
    }
}
