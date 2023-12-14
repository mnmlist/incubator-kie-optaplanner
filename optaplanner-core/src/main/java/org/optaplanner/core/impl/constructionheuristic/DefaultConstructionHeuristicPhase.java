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

package com.sankuai.optaplanner.core.impl.constructionheuristic;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.impl.constructionheuristic.decider.ConstructionHeuristicDecider;
import com.sankuai.optaplanner.core.impl.constructionheuristic.placer.EntityPlacer;
import com.sankuai.optaplanner.core.impl.constructionheuristic.placer.Placement;
import com.sankuai.optaplanner.core.impl.constructionheuristic.scope.ConstructionHeuristicPhaseScope;
import com.sankuai.optaplanner.core.impl.constructionheuristic.scope.ConstructionHeuristicStepScope;
import com.sankuai.optaplanner.core.impl.heuristic.move.AbstractMove;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.phase.AbstractPhase;
import com.sankuai.optaplanner.core.impl.solver.recaller.BestSolutionRecaller;
import com.sankuai.optaplanner.core.impl.solver.scope.SolverScope;
import com.sankuai.optaplanner.core.impl.solver.termination.Termination;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link ConstructionHeuristicPhase}.
 *
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class DefaultConstructionHeuristicPhase<Solution_> extends AbstractPhase<Solution_>
        implements ConstructionHeuristicPhase<Solution_> {

    protected EntityPlacer<Solution_> entityPlacer;
    protected ConstructionHeuristicDecider<Solution_> decider;

    // TODO make this configurable or make it constant
    protected final boolean skipBestSolutionCloningInSteps = true;

    public DefaultConstructionHeuristicPhase(int phaseIndex, String logIndentation,
            BestSolutionRecaller<Solution_> bestSolutionRecaller, Termination<Solution_> termination) {
        super(phaseIndex, logIndentation, bestSolutionRecaller, termination);
    }

    public void setEntityPlacer(EntityPlacer<Solution_> entityPlacer) {
        this.entityPlacer = entityPlacer;
    }

    public void setDecider(ConstructionHeuristicDecider<Solution_> decider) {
        this.decider = decider;
    }

    @Override
    public String getPhaseTypeString() {
        return "Construction Heuristics";
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void solve(SolverScope<Solution_> solverScope) {
        ConstructionHeuristicPhaseScope<Solution_> phaseScope = new ConstructionHeuristicPhaseScope<>(solverScope);
        phaseStarted(phaseScope);

        for (Placement<Solution_> placement : entityPlacer) {
            ConstructionHeuristicStepScope<Solution_> stepScope = new ConstructionHeuristicStepScope<>(phaseScope);
            stepStarted(stepScope);
            decider.decideNextStep(stepScope, placement);
            if (stepScope.getStep() == null) {
                if (termination.isPhaseTerminated(phaseScope)) {
                    logger.trace("{}    Step index ({}), time spent ({}) terminated without picking a nextStep.",
                            logIndentation,
                            stepScope.getStepIndex(),
                            stepScope.getPhaseScope().calculateSolverTimeMillisSpentUpToNow());
                } else if (stepScope.getSelectedMoveCount() == 0L) {
                    logger.warn("{}    No doable selected move at step index ({}), time spent ({})."
                            + " Terminating phase early.",
                            logIndentation,
                            stepScope.getStepIndex(),
                            stepScope.getPhaseScope().calculateSolverTimeMillisSpentUpToNow());
                } else {
                    throw new IllegalStateException("The step index (" + stepScope.getStepIndex()
                            + ") has selected move count (" + stepScope.getSelectedMoveCount()
                            + ") but failed to pick a nextStep (" + stepScope.getStep() + ").");
                }
                // Although stepStarted has been called, stepEnded is not called for this step
                break;
            }
            doStep(stepScope);
            stepEnded(stepScope);
            phaseScope.setLastCompletedStepScope(stepScope);
            if (termination.isPhaseTerminated(phaseScope)) {
                break;
            }
        }
        phaseEnded(phaseScope);
    }

    private void doStep(ConstructionHeuristicStepScope<Solution_> stepScope) {
        Move<Solution_> step = stepScope.getStep();
        Move<Solution_> undoStep = step.doMove(stepScope.getScoreDirector());
        stepScope.setUndoStep(undoStep);
        predictWorkingStepScore(stepScope, step);
        if (!skipBestSolutionCloningInSteps) {
            // Causes a planning clone, which is expensive
            // For example, on cloud balancing 1200c-4800p this reduces performance by 18%
            bestSolutionRecaller.processWorkingSolutionDuringStep(stepScope);
        } else {
            stepScope.setBestScoreImproved(true);
        }
    }

    @Override
    public void solvingStarted(SolverScope<Solution_> solverScope) {
        super.solvingStarted(solverScope);
        entityPlacer.solvingStarted(solverScope);
        decider.solvingStarted(solverScope);
    }

    public void phaseStarted(ConstructionHeuristicPhaseScope<Solution_> phaseScope) {
        super.phaseStarted(phaseScope);
        entityPlacer.phaseStarted(phaseScope);
        decider.phaseStarted(phaseScope);
        moveCountMap = new HashMap<>();
    }

    public void stepStarted(ConstructionHeuristicStepScope<Solution_> stepScope) {
        super.stepStarted(stepScope);
        entityPlacer.stepStarted(stepScope);
        decider.stepStarted(stepScope);
    }

    private static final String STEP_END_LOG_FORMAT = "{}CH step:{}, score:{},"
        + " move:{}, picked:{}";

    public void stepEnded(ConstructionHeuristicStepScope<Solution_> stepScope) {
        super.stepEnded(stepScope);
        entityPlacer.stepEnded(stepScope);
        decider.stepEnded(stepScope);

        if (stepScope.getBestScoreImproved()) {
            AbstractMove move = (AbstractMove)stepScope.getStep();
            String moveName = move.getMoveName();
            logger.info(STEP_END_LOG_FORMAT,
                logIndentation,
                stepScope.getStepIndex(),
                stepScope.getScore(),
                stepScope.getSelectedMoveCount(),
                move.getMoveName());
            moveCountMap.put(moveName, moveCountMap.getOrDefault(moveName, 0) + 1);
        }
//        if (logger.isDebugEnabled()) {
//            long timeMillisSpent = stepScope.getPhaseScope().calculateSolverTimeMillisSpentUpToNow();
//            logger.debug("{}    CH step ({}), time spent ({}), score ({}), selected move count ({}),"
//                    + " picked move ({}).",
//                    logIndentation,
//                    stepScope.getStepIndex(), timeMillisSpent,
//                    stepScope.getScore(),
//                    stepScope.getSelectedMoveCount(),
//                    stepScope.getStepString());
//        }
    }

    public void phaseEnded(ConstructionHeuristicPhaseScope<Solution_> phaseScope) {
        super.phaseEnded(phaseScope);
        if (skipBestSolutionCloningInSteps) {
            bestSolutionRecaller.updateBestSolution(phaseScope.getSolverScope());
        }
        entityPlacer.phaseEnded(phaseScope);
        decider.phaseEnded(phaseScope);
        phaseScope.endingNow();
        logger.info("{}Construction Heuristic phase ({}) ended: time spent ({}), best score ({}),"
                + " score calculation speed ({}/sec), step total ({}).",
                logIndentation,
                phaseIndex,
                phaseScope.calculateSolverTimeMillisSpentUpToNow(),
                phaseScope.getBestScore(),
                phaseScope.getPhaseScoreCalculationSpeed(),
                phaseScope.getNextStepIndex());
        logger.info("Phase statistics, CH index:{}, 各种Move的有效性统计如下:", phaseIndex);
        Integer totalWinMoveCount = moveCountMap.values().stream().reduce(Integer::sum).orElse(0);
        for (Map.Entry<String, Integer> entry : moveCountMap.entrySet()) {
            logger.info("Move statistics: moveType:{}, winMoveCount:{}, winPercent:{}", entry.getKey(), entry.getValue(), entry.getValue() * 1.0 / totalWinMoveCount.doubleValue());
        }
        logger.info("Phase statistics, CH index:{}, 有效Step总计:{}, 有效Step占比:{}", phaseIndex, totalWinMoveCount, totalWinMoveCount * 1.0 / phaseScope.getNextStepIndex());
    }

    @Override
    public void solvingEnded(SolverScope<Solution_> solverScope) {
        super.solvingEnded(solverScope);
        entityPlacer.solvingEnded(solverScope);
        decider.solvingEnded(solverScope);
    }

}
