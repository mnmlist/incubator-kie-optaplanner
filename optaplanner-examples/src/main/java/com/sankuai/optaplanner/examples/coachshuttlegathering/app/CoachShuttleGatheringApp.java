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

package com.sankuai.optaplanner.examples.coachshuttlegathering.app;

import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.CoachShuttleGatheringSolution;
import com.sankuai.optaplanner.examples.coachshuttlegathering.persistence.CoachShuttleGatheringExporter;
import com.sankuai.optaplanner.examples.coachshuttlegathering.persistence.CoachShuttleGatheringImporter;
import com.sankuai.optaplanner.examples.coachshuttlegathering.persistence.CoachShuttleGatheringXmlSolutionFileIO;
import com.sankuai.optaplanner.examples.coachshuttlegathering.swingui.CoachShuttleGatheringPanel;
import com.sankuai.optaplanner.examples.common.app.CommonApp;
import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import com.sankuai.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

import java.util.HashSet;
import java.util.Set;

public class CoachShuttleGatheringApp extends CommonApp<CoachShuttleGatheringSolution> {

    public static final String SOLVER_CONFIG =
            "com/sankuai/optaplanner/examples/coachshuttlegathering/solver/coachShuttleGatheringSolverConfig.xml";

    public static final String DATA_DIR_NAME = "coachshuttlegathering";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new CoachShuttleGatheringApp().init();
    }

    public CoachShuttleGatheringApp() {
        super("Coach shuttle gathering",
                "Transport passengers to a hub by using coaches and shuttles.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                CoachShuttleGatheringPanel.LOGO_PATH);
    }

    @Override
    protected CoachShuttleGatheringPanel createSolutionPanel() {
        return new CoachShuttleGatheringPanel();
    }

    @Override
    public SolutionFileIO<CoachShuttleGatheringSolution> createSolutionFileIO() {
        return new CoachShuttleGatheringXmlSolutionFileIO();
    }

    @Override
    protected AbstractSolutionImporter[] createSolutionImporters() {
        return new AbstractSolutionImporter[] {
                new CoachShuttleGatheringImporter()
        };
    }

    @Override
    protected Set<AbstractSolutionExporter> createSolutionExporters() {
        Set<AbstractSolutionExporter> exporters = new HashSet<>(1);
        exporters.add(new CoachShuttleGatheringExporter());
        return exporters;
    }

}
