/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.nqueens.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sankuai.optaplanner.core.api.solver.Solver;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import com.sankuai.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig;
import com.sankuai.optaplanner.core.config.phase.PhaseConfig;
import com.sankuai.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import com.sankuai.optaplanner.core.config.solver.SolverConfig;
import com.sankuai.optaplanner.core.config.solver.termination.TerminationConfig;
import com.sankuai.optaplanner.examples.common.app.CommonApp;
import com.sankuai.optaplanner.examples.nqueens.domain.NQueens;
import com.sankuai.optaplanner.examples.nqueens.domain.Queen;
import com.sankuai.optaplanner.examples.nqueens.persistence.NQueensXmlSolutionFileIO;
import com.sankuai.optaplanner.examples.nqueens.swingui.NQueensPanel;
import com.sankuai.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

/**
 * For an easy example, look at {@link NQueensHelloWorld} instead.
 */
public class NQueensApp extends CommonApp<NQueens> {

    public static final String SOLVER_CONFIG = "org/optaplanner/examples/nqueens/solver/nqueensSolverConfig.xml";

    public static final String DATA_DIR_NAME = "nqueens";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new NQueensApp().init();
    }

    public NQueensApp() {
        super("N queens",
                "Place queens on a chessboard.\n\n" +
                        "No 2 queens must be able to attack each other.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                NQueensPanel.LOGO_PATH);
    }

    @Override
    protected SolverFactory<NQueens> createSolverFactory() {
        return createSolverFactoryByXml();
    }

    /**
     * Normal way to create a {@link Solver}.
     *
     * @return never null
     */
    protected SolverFactory<NQueens> createSolverFactoryByXml() {
        return SolverFactory.createFromXmlResource(SOLVER_CONFIG);
    }

    /**
     * Unused alternative. A way to create a {@link Solver} without using XML.
     * <p>
     * It is recommended to use {@link #createSolverFactoryByXml()} instead.
     *
     * @return never null
     */
    protected SolverFactory<NQueens> createSolverFactoryByApi() {
        SolverConfig solverConfig = new SolverConfig();

        solverConfig.setSolutionClass(NQueens.class);
        solverConfig.setEntityClassList(Collections.singletonList(Queen.class));

        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = new ScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setScoreDrlList(
                Arrays.asList("org/optaplanner/examples/nqueens/solver/nQueensConstraints.drl"));
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);

        solverConfig.setTerminationConfig(new TerminationConfig().withBestScoreLimit("0"));
        List<PhaseConfig> phaseConfigList = new ArrayList<>();

        ConstructionHeuristicPhaseConfig constructionHeuristicPhaseConfig = new ConstructionHeuristicPhaseConfig();
        constructionHeuristicPhaseConfig.setConstructionHeuristicType(
                ConstructionHeuristicType.FIRST_FIT_DECREASING);
        phaseConfigList.add(constructionHeuristicPhaseConfig);

        LocalSearchPhaseConfig localSearchPhaseConfig = new LocalSearchPhaseConfig();
        ChangeMoveSelectorConfig changeMoveSelectorConfig = new ChangeMoveSelectorConfig();
        changeMoveSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        localSearchPhaseConfig.setMoveSelectorConfig(changeMoveSelectorConfig);
        localSearchPhaseConfig.setAcceptorConfig(new LocalSearchAcceptorConfig().withEntityTabuSize(5));
        phaseConfigList.add(localSearchPhaseConfig);

        solverConfig.setPhaseConfigList(phaseConfigList);
        return SolverFactory.create(solverConfig);
    }

    @Override
    protected NQueensPanel createSolutionPanel() {
        return new NQueensPanel();
    }

    @Override
    public SolutionFileIO<NQueens> createSolutionFileIO() {
        return new NQueensXmlSolutionFileIO();
    }

}
