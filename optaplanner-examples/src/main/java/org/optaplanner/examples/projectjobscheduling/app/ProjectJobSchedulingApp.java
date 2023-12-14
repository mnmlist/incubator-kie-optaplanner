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

package com.sankuai.optaplanner.examples.projectjobscheduling.app;

import com.sankuai.optaplanner.examples.common.app.CommonApp;
import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import com.sankuai.optaplanner.examples.projectjobscheduling.domain.Schedule;
import com.sankuai.optaplanner.examples.projectjobscheduling.persistence.ProjectJobSchedulingImporter;
import com.sankuai.optaplanner.examples.projectjobscheduling.persistence.ProjectJobSchedulingXmlSolutionFileIO;
import com.sankuai.optaplanner.examples.projectjobscheduling.swingui.ProjectJobSchedulingPanel;
import com.sankuai.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

public class ProjectJobSchedulingApp extends CommonApp<Schedule> {

    public static final String SOLVER_CONFIG =
            "org/optaplanner/examples/projectjobscheduling/solver/projectJobSchedulingSolverConfig.xml";

    public static final String DATA_DIR_NAME = "projectjobscheduling";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new ProjectJobSchedulingApp().init();
    }

    public ProjectJobSchedulingApp() {
        super("Project job scheduling",
                "Official competition name:" +
                        " multi-mode resource-constrained multi-project scheduling problem (MRCMPSP)\n\n" +
                        "Schedule all jobs in time and execution mode.\n\n" +
                        "Minimize project delays.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                ProjectJobSchedulingPanel.LOGO_PATH);
    }

    @Override
    protected ProjectJobSchedulingPanel createSolutionPanel() {
        return new ProjectJobSchedulingPanel();
    }

    @Override
    public SolutionFileIO<Schedule> createSolutionFileIO() {
        return new ProjectJobSchedulingXmlSolutionFileIO();
    }

    @Override
    protected AbstractSolutionImporter[] createSolutionImporters() {
        return new AbstractSolutionImporter[] {
                new ProjectJobSchedulingImporter()
        };
    }

}
