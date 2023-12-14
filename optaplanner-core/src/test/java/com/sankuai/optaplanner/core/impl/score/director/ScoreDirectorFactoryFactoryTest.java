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

package com.sankuai.optaplanner.core.impl.score.director;

import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import com.sankuai.optaplanner.core.api.score.calculator.IncrementalScoreCalculator;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintStreamImplType;
import com.sankuai.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import com.sankuai.optaplanner.core.config.solver.EnvironmentMode;
import com.sankuai.optaplanner.core.impl.score.director.easy.EasyScoreDirector;
import com.sankuai.optaplanner.core.impl.score.director.incremental.IncrementalScoreDirector;
import com.sankuai.optaplanner.core.impl.score.director.stream.AbstractConstraintStreamScoreDirectorFactory;
import com.sankuai.optaplanner.core.impl.score.director.stream.BavetConstraintStreamScoreDirectorFactory;
import com.sankuai.optaplanner.core.impl.score.director.stream.DroolsConstraintStreamScoreDirectorFactory;
import com.sankuai.optaplanner.core.impl.score.stream.drools.DroolsConstraintSessionFactory;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ScoreDirectorFactoryFactoryTest {

    @Test
    void easyScoreCalculatorWithCustomProperties() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig();
        config.setEasyScoreCalculatorClass(TestCustomPropertiesEasyScoreCalculator.class);
        HashMap<String, String> customProperties = new HashMap<>();
        customProperties.put("stringProperty", "string 1");
        customProperties.put("intProperty", "7");
        config.setEasyScoreCalculatorCustomProperties(customProperties);

        EasyScoreDirector<TestdataSolution, ?> scoreDirector =
                (EasyScoreDirector<TestdataSolution, ?>) buildTestdataScoreDirectoryFactory(config).buildScoreDirector();
        TestCustomPropertiesEasyScoreCalculator scoreCalculator =
                (TestCustomPropertiesEasyScoreCalculator) scoreDirector
                        .getEasyScoreCalculator();
        assertThat(scoreCalculator.getStringProperty()).isEqualTo("string 1");
        assertThat(scoreCalculator.getIntProperty()).isEqualTo(7);
    }

    @Test
    void incrementalScoreCalculatorWithCustomProperties() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig();
        config.setIncrementalScoreCalculatorClass(
                TestCustomPropertiesIncrementalScoreCalculator.class);
        HashMap<String, String> customProperties = new HashMap<>();
        customProperties.put("stringProperty", "string 1");
        customProperties.put("intProperty", "7");
        config.setIncrementalScoreCalculatorCustomProperties(customProperties);

        ScoreDirectorFactory<TestdataSolution> scoreDirectorFactory = buildTestdataScoreDirectoryFactory(config);
        IncrementalScoreDirector<TestdataSolution, ?> scoreDirector =
                (IncrementalScoreDirector<TestdataSolution, ?>) scoreDirectorFactory.buildScoreDirector();
        TestCustomPropertiesIncrementalScoreCalculator scoreCalculator =
                (TestCustomPropertiesIncrementalScoreCalculator) scoreDirector
                        .getIncrementalScoreCalculator();
        assertThat(scoreCalculator.getStringProperty()).isEqualTo("string 1");
        assertThat(scoreCalculator.getIntProperty()).isEqualTo(7);
    }

    @Test
    void buildWithAssertionScoreDirectorFactory() {
        ScoreDirectorFactoryConfig assertionScoreDirectorConfig = new ScoreDirectorFactoryConfig()
                .withIncrementalScoreCalculatorClass(TestCustomPropertiesIncrementalScoreCalculator.class);
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withConstraintProviderClass(TestdataConstraintProvider.class)
                .withAssertionScoreDirectorFactory(assertionScoreDirectorConfig);

        AbstractScoreDirectorFactory<TestdataSolution, ?> scoreDirectorFactory =
                (AbstractScoreDirectorFactory<TestdataSolution, ?>) buildTestdataScoreDirectoryFactory(config,
                        EnvironmentMode.FAST_ASSERT);

        ScoreDirectorFactory<TestdataSolution> assertionScoreDirectorFactory =
                scoreDirectorFactory.getAssertionScoreDirectorFactory();
        IncrementalScoreDirector<TestdataSolution, ?> assertionScoreDirector =
                (IncrementalScoreDirector<TestdataSolution, ?>) assertionScoreDirectorFactory.buildScoreDirector();
        IncrementalScoreCalculator<TestdataSolution, ?> assertionScoreCalculator =
                assertionScoreDirector.getIncrementalScoreCalculator();

        assertThat(assertionScoreCalculator)
                .isNotNull()
                .isExactlyInstanceOf(TestCustomPropertiesIncrementalScoreCalculator.class);
    }

    @Test
    void multipleScoreCalculations_throwsException() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withConstraintProviderClass(TestdataConstraintProvider.class)
                .withEasyScoreCalculatorClass(TestCustomPropertiesEasyScoreCalculator.class)
                .withIncrementalScoreCalculatorClass(TestCustomPropertiesIncrementalScoreCalculator.class)
                .withScoreDrls(getClass().getPackage().getName().replace('.', '/') + "/dummySimpleScoreDroolsConstraints.drl");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> buildTestdataScoreDirectoryFactory(config))
                .withMessageContaining("scoreDirectorFactory")
                .withMessageContaining("together");
    }

    @Test
    void constraintStreamsDroolsWithAlphaNetworkCompilationEnabled() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withConstraintProviderClass(TestdataConstraintProvider.class)
                .withDroolsAlphaNetworkCompilationEnabled(true);
        ScoreDirectorFactoryFactory<TestdataSolution, SimpleScore> factoryFactory = new ScoreDirectorFactoryFactory<>(config);
        AbstractConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore> uncastScoreDirectorFactory =
                factoryFactory.buildConstraintStreamScoreDirectorFactory(TestdataSolution.buildSolutionDescriptor());
        assertThat(uncastScoreDirectorFactory).isInstanceOf(DroolsConstraintStreamScoreDirectorFactory.class);
        DroolsConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore> scoreDirectorFactory =
                (DroolsConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore>) uncastScoreDirectorFactory;
        DroolsConstraintSessionFactory<TestdataSolution, SimpleScore> constraintSessionFactory =
                scoreDirectorFactory.getConstraintSessionFactory();
        assertThat(constraintSessionFactory.isDroolsAlphaNetworkCompilationEnabled()).isTrue();
    }

    @Test
    void constraintStreamsDroolsWithAlphaNetworkCompilationDisabled() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withConstraintProviderClass(TestdataConstraintProvider.class)
                .withDroolsAlphaNetworkCompilationEnabled(false);
        ScoreDirectorFactoryFactory<TestdataSolution, SimpleScore> factoryFactory = new ScoreDirectorFactoryFactory<>(config);
        AbstractConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore> uncastScoreDirectorFactory =
                factoryFactory.buildConstraintStreamScoreDirectorFactory(TestdataSolution.buildSolutionDescriptor());
        assertThat(uncastScoreDirectorFactory).isInstanceOf(DroolsConstraintStreamScoreDirectorFactory.class);
        DroolsConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore> scoreDirectorFactory =
                (DroolsConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore>) uncastScoreDirectorFactory;
        DroolsConstraintSessionFactory<TestdataSolution, SimpleScore> constraintSessionFactory =
                scoreDirectorFactory.getConstraintSessionFactory();
        assertThat(constraintSessionFactory.isDroolsAlphaNetworkCompilationEnabled()).isFalse();
    }

    @Test
    void constraintStreamsBavet() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withConstraintProviderClass(TestdataConstraintProvider.class)
                .withConstraintStreamImplType(ConstraintStreamImplType.BAVET);
        ScoreDirectorFactoryFactory<TestdataSolution, SimpleScore> factoryFactory = new ScoreDirectorFactoryFactory<>(config);
        AbstractConstraintStreamScoreDirectorFactory<TestdataSolution, SimpleScore> scoreDirectorFactory =
                factoryFactory.buildConstraintStreamScoreDirectorFactory(TestdataSolution.buildSolutionDescriptor());
        assertThat(scoreDirectorFactory).isInstanceOf(BavetConstraintStreamScoreDirectorFactory.class);
    }

    @Test
    void invalidDrlResource_throwsException() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withScoreDrls("com/sankuai/optaplanner/core/impl/score/director/invalidDroolsConstraints.drl");
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> buildTestdataScoreDirectoryFactory(config))
                .withMessageContaining("scoreDrl")
                .withRootCauseInstanceOf(RuntimeException.class);
    }

    @Test
    void nonExistingDrlResource_throwsException() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withScoreDrls("nonExisting.drl");
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> buildTestdataScoreDirectoryFactory(config))
                .withMessageContaining("scoreDrl");
    }

    @Test
    void nonExistingDrlFile_throwsException() {
        ScoreDirectorFactoryConfig config = new ScoreDirectorFactoryConfig()
                .withScoreDrlFiles(new File("nonExisting.drl"));
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> buildTestdataScoreDirectoryFactory(config))
                .withMessageContaining("scoreDrl");
    }

    private <Score_ extends Score<Score_>> ScoreDirectorFactory<TestdataSolution> buildTestdataScoreDirectoryFactory(
            ScoreDirectorFactoryConfig config, EnvironmentMode environmentMode) {
        return new ScoreDirectorFactoryFactory<TestdataSolution, Score_>(config)
                .buildScoreDirectorFactory(getClass().getClassLoader(), environmentMode,
                        TestdataSolution.buildSolutionDescriptor());
    }

    private ScoreDirectorFactory<TestdataSolution> buildTestdataScoreDirectoryFactory(ScoreDirectorFactoryConfig config) {
        return buildTestdataScoreDirectoryFactory(config, EnvironmentMode.REPRODUCIBLE);
    }

    public static class TestCustomPropertiesEasyScoreCalculator
            implements EasyScoreCalculator<TestdataSolution, SimpleScore> {

        private String stringProperty;
        private int intProperty;

        public String getStringProperty() {
            return stringProperty;
        }

        @SuppressWarnings("unused")
        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        @SuppressWarnings("unused")
        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public SimpleScore calculateScore(TestdataSolution testdataSolution) {
            return SimpleScore.ZERO;
        }
    }

    public static class TestCustomPropertiesIncrementalScoreCalculator
            implements IncrementalScoreCalculator<TestdataSolution, SimpleScore> {

        private String stringProperty;
        private int intProperty;

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        @Override
        public void resetWorkingSolution(TestdataSolution workingSolution) {
        }

        @Override
        public void beforeEntityAdded(Object entity) {
        }

        @Override
        public void afterEntityAdded(Object entity) {
        }

        @Override
        public void beforeVariableChanged(Object entity, String variableName) {
        }

        @Override
        public void afterVariableChanged(Object entity, String variableName) {
        }

        @Override
        public void beforeEntityRemoved(Object entity) {
        }

        @Override
        public void afterEntityRemoved(Object entity) {
        }

        @Override
        public SimpleScore calculateScore() {
            return SimpleScore.ZERO;
        }
    }

    public static class TestdataConstraintProvider implements ConstraintProvider {
        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
            return new Constraint[0];
        }
    }
}
