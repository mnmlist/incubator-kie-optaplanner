package com.sankuai.optaplanner.core.impl.score.stream;

import com.sankuai.optaplanner.core.api.domain.entity.PlanningEntity;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.api.solver.Solver;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import com.sankuai.optaplanner.core.config.solver.SolverConfig;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedAnchor;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedSolution;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FactChangePropagationTest {

    private static final String VALUE_CODE = "v1";
    private Solver<TestdataShadowingChainedSolution> solver = buildSolver();

    /**
     * Tests if Drools try to modify a {@link PlanningEntity} before its shadow variables are updated.
     */
    @Test
    void delayedFactChangePropagation() {
        TestdataShadowingChainedEntity entity = new TestdataShadowingChainedEntity("e1");
        TestdataShadowingChainedAnchor value = new TestdataShadowingChainedAnchor(VALUE_CODE);
        TestdataShadowingChainedSolution inputProblem = new TestdataShadowingChainedSolution();
        inputProblem.setChainedAnchorList(Arrays.asList(value));
        inputProblem.setChainedEntityList(Arrays.asList(entity));

        TestdataShadowingChainedSolution solution = solver.solve(inputProblem);
        TestdataShadowingChainedEntity solvedEntity = solution.getChainedEntityList().get(0);
        assertThat(solvedEntity.getChainedObject()).isNotNull();
        assertThat(solvedEntity.getAnchor().getCode()).isEqualTo(VALUE_CODE);
        assertThat(solution.getScore().isFeasible()).isTrue();
    }

    private Solver<TestdataShadowingChainedSolution> buildSolver() {
        SolverConfig solverConfig = new SolverConfig()
                .withEntityClasses(TestdataShadowingChainedEntity.class)
                .withSolutionClass(TestdataShadowingChainedSolution.class)
                .withConstraintProviderClass(ChainedEntityConstraintProvider.class)
                .withPhases(new ConstructionHeuristicPhaseConfig());

        SolverFactory<TestdataShadowingChainedSolution> solverFactory = SolverFactory.create(solverConfig);
        return solverFactory.buildSolver();
    }

    public static class ChainedEntityConstraintProvider implements ConstraintProvider {

        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
            return new Constraint[] {
                    anchorCannotBeNull(constraintFactory)
            };
        }

        private Constraint anchorCannotBeNull(ConstraintFactory constraintFactory) {
            return constraintFactory.from(TestdataShadowingChainedEntity.class)
                    /*
                     * The getCode() is here just to trigger NPE if the filter's predicate has been called before
                     * the AnchorVariableListener has updated the anchor.
                     */
                    .filter(testdataShadowingChainedEntity -> "v1".equals(testdataShadowingChainedEntity.getAnchor().getCode()))
                    .penalize("anchorCannotBeNull", SimpleScore.ONE);
        }
    }
}
