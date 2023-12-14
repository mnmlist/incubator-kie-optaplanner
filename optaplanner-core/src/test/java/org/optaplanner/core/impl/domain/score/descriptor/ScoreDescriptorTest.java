/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sankuai.optaplanner.core.impl.domain.score.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.score.buildin.simple.SimpleScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.definition.ScoreDefinition;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;

class ScoreDescriptorTest {

    @Test
    void scoreDefinition() {
        SolutionDescriptor<TestdataSolution> solutionDescriptor = TestdataSolution.buildSolutionDescriptor();
        ScoreDefinition<?> scoreDefinition = solutionDescriptor.getScoreDefinition();
        assertThat(scoreDefinition).isInstanceOf(SimpleScoreDefinition.class);
        assertThat(scoreDefinition.getScoreClass()).isEqualTo(SimpleScore.class);
    }

    @Test
    void scoreAccess() {
        SolutionDescriptor<TestdataSolution> solutionDescriptor = TestdataSolution.buildSolutionDescriptor();
        TestdataSolution solution = new TestdataSolution();

        assertThat((SimpleScore) solutionDescriptor.getScore(solution)).isNull();

        SimpleScore score = SimpleScore.of(-2);
        solutionDescriptor.setScore(solution, score);
        assertThat((SimpleScore) solutionDescriptor.getScore(solution)).isSameAs(score);
    }
}
