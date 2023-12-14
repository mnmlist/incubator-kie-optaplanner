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

package com.sankuai.optaplanner.test.impl.score.buildin.hardmediumsoftlong;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintStream;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.test.api.score.stream.ConstraintVerifier;
import com.sankuai.optaplanner.test.impl.score.AbstractScoreVerifier;

/**
 * To assert the constraints of a {@link SolverFactory}
 * that uses a {@link HardMediumSoftLongScore}.
 * If you're using {@link ConstraintStream}s, use {@link ConstraintVerifier} instead.
 *
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class HardMediumSoftLongScoreVerifier<Solution_> extends AbstractScoreVerifier<Solution_> {

    /**
     * @param solverFactory never null, the {@link SolverFactory} of which you want to test the constraints.
     */
    public HardMediumSoftLongScoreVerifier(SolverFactory<Solution_> solverFactory) {
        super(solverFactory, HardMediumSoftLongScore.class);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertHardWeight(String constraintName, long expectedWeight, Solution_ solution) {
        assertHardWeight(null, constraintName, expectedWeight, solution);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintPackage sometimes null.
     *        When null, {@code constraintName} for the {@code scoreLevel} must be unique.
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertHardWeight(String constraintPackage, String constraintName, long expectedWeight, Solution_ solution) {
        assertWeight(constraintPackage, constraintName, 0, Long.valueOf(expectedWeight), solution);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertMediumWeight(String constraintName, long expectedWeight, Solution_ solution) {
        assertMediumWeight(null, constraintName, expectedWeight, solution);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintPackage sometimes null.
     *        When null, {@code constraintName} for the {@code scoreLevel} must be unique.
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertMediumWeight(String constraintPackage, String constraintName, long expectedWeight, Solution_ solution) {
        assertWeight(constraintPackage, constraintName, 1, Long.valueOf(expectedWeight), solution);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertSoftWeight(String constraintName, long expectedWeight, Solution_ solution) {
        assertSoftWeight(null, constraintName, expectedWeight, solution);
    }

    /**
     * Assert that the constraint of {@link PlanningSolution}
     * has the expected weight for that score level.
     *
     * @param constraintPackage sometimes null.
     *        When null, {@code constraintName} for the {@code scoreLevel} must be unique.
     * @param constraintName never null, the name of the constraint
     * @param expectedWeight the total weight for all matches of that 1 constraint
     * @param solution never null, the actual {@link PlanningSolution}
     */
    public void assertSoftWeight(String constraintPackage, String constraintName, long expectedWeight, Solution_ solution) {
        assertWeight(constraintPackage, constraintName, 2, Long.valueOf(expectedWeight), solution);
    }

}
