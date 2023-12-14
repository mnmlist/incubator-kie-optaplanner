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

package com.sankuai.optaplanner.core.api.score;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.Collections;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintStreamImplType;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import com.sankuai.optaplanner.core.config.solver.SolverConfig;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataConstraintProvider;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;

class ScoreManagerTest {

    @Test
    public void updateScore() {
        SolverFactory<TestdataSolution> solverFactory =
                SolverFactory.createFromXmlResource("org/optaplanner/core/api/solver/testdataSolverConfig.xml");
        ScoreManager<TestdataSolution, ?> scoreManager = ScoreManager.create(solverFactory);
        assertThat(scoreManager).isNotNull();
        TestdataSolution solution = TestdataSolution.generateSolution();
        assertThat(solution.getScore()).isNull();
        scoreManager.updateScore(solution);
        assertThat(solution.getScore()).isNotNull();
    }

    @Test
    public void explainScore() {
        SolverFactory<TestdataSolution> solverFactory =
                SolverFactory.createFromXmlResource("org/optaplanner/core/api/solver/testdataSolverConfig.xml");
        ScoreManager<TestdataSolution, ?> scoreManager = ScoreManager.create(solverFactory);
        assertThat(scoreManager).isNotNull();
        TestdataSolution solution = TestdataSolution.generateSolution();
        ScoreExplanation<TestdataSolution, ?> scoreExplanation = scoreManager.explainScore(solution);
        assertThat(scoreExplanation).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(scoreExplanation.getScore()).isNotNull();
            softly.assertThat(scoreExplanation.getSummary()).isNotBlank();
            softly.assertThat(scoreExplanation.getConstraintMatchTotalMap()).isNotEmpty();
            softly.assertThat(scoreExplanation.getIndictmentMap()).isNotEmpty();
        });
    }

    @Test
    public void indictmentsPresentOnFreshExplanationDrools() {
        indictmentsPresentOnFreshExplanations(ConstraintStreamImplType.DROOLS);
    }

    @Test
    public void indictmentsPresentOnFreshExplanationBavet() {
        indictmentsPresentOnFreshExplanations(ConstraintStreamImplType.BAVET);
    }

    private void indictmentsPresentOnFreshExplanations(ConstraintStreamImplType constraintStreamImplType) {
        // Create the environment.
        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = new ScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setConstraintProviderClass(TestdataConstraintProvider.class);
        scoreDirectorFactoryConfig.setConstraintStreamImplType(constraintStreamImplType);
        SolverConfig solverConfig = new SolverConfig();
        solverConfig.setSolutionClass(TestdataSolution.class);
        solverConfig.setEntityClassList(Collections.singletonList(TestdataEntity.class));
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);
        SolverFactory<TestdataSolution> solverFactory = SolverFactory.create(solverConfig);
        ScoreManager<TestdataSolution, SimpleScore> scoreManager = ScoreManager.create(solverFactory);

        // Prepare the solution.
        int entityCount = 3;
        TestdataSolution solution = TestdataSolution.generateSolution(2, entityCount);
        ScoreExplanation<TestdataSolution, SimpleScore> scoreExplanation = scoreManager.explainScore(solution);

        // Check for expected results.
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(scoreExplanation.getScore())
                    .isEqualTo(SimpleScore.of(-entityCount));
            softly.assertThat(scoreExplanation.getConstraintMatchTotalMap())
                    .isNotEmpty();
            softly.assertThat(scoreExplanation.getIndictmentMap())
                    .isNotEmpty();
        });
    }

}
