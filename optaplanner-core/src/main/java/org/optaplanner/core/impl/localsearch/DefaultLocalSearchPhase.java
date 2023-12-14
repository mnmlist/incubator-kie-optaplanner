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

package com.sankuai.optaplanner.core.impl.localsearch;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.impl.heuristic.move.AbstractMove;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.localsearch.decider.LocalSearchDecider;
import com.sankuai.optaplanner.core.impl.localsearch.event.LocalSearchPhaseLifecycleListener;
import com.sankuai.optaplanner.core.impl.localsearch.scope.LocalSearchPhaseScope;
import com.sankuai.optaplanner.core.impl.localsearch.scope.LocalSearchStepScope;
import com.sankuai.optaplanner.core.impl.phase.AbstractPhase;
import com.sankuai.optaplanner.core.impl.solver.recaller.BestSolutionRecaller;
import com.sankuai.optaplanner.core.impl.solver.scope.SolverScope;
import com.sankuai.optaplanner.core.impl.solver.termination.Termination;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link LocalSearchPhase}.
 *
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class DefaultLocalSearchPhase<Solution_> extends AbstractPhase<Solution_> implements LocalSearchPhase<Solution_>,
        LocalSearchPhaseLifecycleListener<Solution_> {

    protected LocalSearchDecider<Solution_> decider;

    public DefaultLocalSearchPhase(int phaseIndex, String logIndentation,
            BestSolutionRecaller<Solution_> bestSolutionRecaller, Termination<Solution_> termination) {
        super(phaseIndex, logIndentation, bestSolutionRecaller, termination);
    }

    public LocalSearchDecider<Solution_> getDecider() {
        return decider;
    }

    public void setDecider(LocalSearchDecider<Solution_> decider) {
        this.decider = decider;
    }

    @Override
    public String getPhaseTypeString() {
        return "Local Search";
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void solve(SolverScope<Solution_> solverScope) {
        LocalSearchPhaseScope<Solution_> phaseScope = new LocalSearchPhaseScope<>(solverScope);
        phaseStarted(phaseScope);

        while (!termination.isPhaseTerminated(phaseScope)) {
            LocalSearchStepScope<Solution_> stepScope = new LocalSearchStepScope<>(phaseScope);
            stepScope.setTimeGradient(termination.calculatePhaseTimeGradient(phaseScope));
            stepStarted(stepScope);
            decider.decideNextStep(stepScope);
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
                            + ") has accepted/selected move count (" + stepScope.getAcceptedMoveCount() + "/"
                            + stepScope.getSelectedMoveCount()
                            + ") but failed to pick a nextStep (" + stepScope.getStep() + ").");
                }
                // Although stepStarted has been called, stepEnded is not called for this step
                break;
            }
            doStep(stepScope);
            stepEnded(stepScope);
            phaseScope.setLastCompletedStepScope(stepScope);
        }
        phaseEnded(phaseScope);
    }

    protected void doStep(LocalSearchStepScope<Solution_> stepScope) {
        Move<Solution_> step = stepScope.getStep();
        Move<Solution_> undoStep = step.doMove(stepScope.getScoreDirector());
        stepScope.setUndoStep(undoStep);
        predictWorkingStepScore(stepScope, step);
        bestSolutionRecaller.processWorkingSolutionDuringStep(stepScope);
    }

    @Override
    public void solvingStarted(SolverScope<Solution_> solverScope) {
        super.solvingStarted(solverScope);
        decider.solvingStarted(solverScope);
    }

    @Override
    public void phaseStarted(LocalSearchPhaseScope<Solution_> phaseScope) {
        super.phaseStarted(phaseScope);
        decider.phaseStarted(phaseScope);
        // TODO maybe this restriction should be lifted to allow LocalSearch to initialize a solution too?
        assertWorkingSolutionInitialized(phaseScope);
        moveCountMap = new HashMap<>();
    }

    @Override
    public void stepStarted(LocalSearchStepScope<Solution_> stepScope) {
        super.stepStarted(stepScope);
        decider.stepStarted(stepScope);
    }

    private static final String STEP_END_LOG_FORMAT = "{}LS step:{}, score:{},"
        + " move:{}, picked:{}";

    @Override
    public void stepEnded(LocalSearchStepScope<Solution_> stepScope) {
        super.stepEnded(stepScope);
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

//        LocalSearchPhaseScope<Solution_> phaseScope = stepScope.getPhaseScope();

//        if (logger.isDebugEnabled()) {
//            logger.info("{}LS step ({}), time spent ({}), score ({}), {} best score ({})," +
//                    " accepted/selected move count ({}/{}), picked move ({}).",
//                    logIndentation,
//                    stepScope.getStepIndex(),
//                    phaseScope.calculateSolverTimeMillisSpentUpToNow(),
//                    stepScope.getScore(),
//                    (stepScope.getBestScoreImproved() ? "new" : "   "), phaseScope.getBestScore(),
//                    stepScope.getAcceptedMoveCount(),
//                    stepScope.getSelectedMoveCount(),
//                    stepScope.getStepString());
//        }
    }

    @Override
    public void phaseEnded(LocalSearchPhaseScope<Solution_> phaseScope) {
        super.phaseEnded(phaseScope);
        decider.phaseEnded(phaseScope);
        phaseScope.endingNow();
        logger.info("{}Local Search phase ({}) ended: time spent ({}), best score ({}),"
                + " score calculation speed ({}/sec), step total ({}).",
                logIndentation,
                phaseIndex,
                phaseScope.calculateSolverTimeMillisSpentUpToNow(),
                phaseScope.getBestScore(),
                phaseScope.getPhaseScoreCalculationSpeed(),
                phaseScope.getNextStepIndex());
        logger.info("Phase statistics, LS index:{}, 各种Move的有效性统计如下:", phaseIndex);
        Integer totalWinMoveCount = moveCountMap.values().stream().reduce(Integer::sum).orElse(0);
        for (Map.Entry<String, Integer> entry : moveCountMap.entrySet()) {
            logger.info("Move statistics: moveType:{}, winMoveCount:{}, winPercent:{}", entry.getKey(), entry.getValue(), entry.getValue() * 1.0 / totalWinMoveCount.doubleValue());
        }
        logger.info("Phase statistics, LS index:{}, 有效Step总计:{}, 有效Step占比:{}", phaseIndex, totalWinMoveCount, totalWinMoveCount * 1.0 / phaseScope.getNextStepIndex());
    }

    @Override
    public void solvingEnded(SolverScope<Solution_> solverScope) {
        super.solvingEnded(solverScope);
        decider.solvingEnded(solverScope);
    }

}
