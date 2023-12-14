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

package com.sankuai.optaplanner.examples.tsp.app;

import java.util.HashSet;
import java.util.Set;

import com.sankuai.optaplanner.examples.common.app.CommonApp;
import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import com.sankuai.optaplanner.examples.tsp.domain.TspSolution;
import com.sankuai.optaplanner.examples.tsp.persistence.SvgTspLineAndCircleExporter;
import com.sankuai.optaplanner.examples.tsp.persistence.SvgTspPathExporter;
import com.sankuai.optaplanner.examples.tsp.persistence.TspExporter;
import com.sankuai.optaplanner.examples.tsp.persistence.TspImageStipplerImporter;
import com.sankuai.optaplanner.examples.tsp.persistence.TspImporter;
import com.sankuai.optaplanner.examples.tsp.persistence.TspXmlSolutionFileIO;
import com.sankuai.optaplanner.examples.tsp.swingui.TspPanel;
import com.sankuai.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

public class TspApp extends CommonApp<TspSolution> {

    public static final String SOLVER_CONFIG = "org/optaplanner/examples/tsp/solver/tspSolverConfig.xml";

    public static final String DATA_DIR_NAME = "tsp";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new TspApp().init();
    }

    public TspApp() {
        super("Traveling salesman",
                "Official competition name: TSP - Traveling salesman problem\n\n" +
                        "Determine the order in which to visit all cities.\n\n" +
                        "Find the shortest route to visit all cities.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                TspPanel.LOGO_PATH);
    }

    @Override
    protected TspPanel createSolutionPanel() {
        return new TspPanel();
    }

    @Override
    public SolutionFileIO<TspSolution> createSolutionFileIO() {
        return new TspXmlSolutionFileIO();
    }

    @Override
    protected AbstractSolutionImporter[] createSolutionImporters() {
        return new AbstractSolutionImporter[] {
                new TspImporter(),
                new TspImageStipplerImporter()
        };
    }

    @Override
    protected Set<AbstractSolutionExporter> createSolutionExporters() {
        Set<AbstractSolutionExporter> exporters = new HashSet<>();
        exporters.add(new TspExporter());
        exporters.add(new SvgTspPathExporter());
        exporters.add(new SvgTspLineAndCircleExporter());

        return exporters;
    }

}
