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

package com.sankuai.optaplanner.examples.app;

import com.sankuai.optaplanner.examples.cloudbalancing.persistence.CloudBalancingGenerator;
import com.sankuai.optaplanner.examples.common.app.LoggingMain;
import com.sankuai.optaplanner.examples.curriculumcourse.persistence.CurriculumCourseImporter;
import com.sankuai.optaplanner.examples.examination.persistence.ExaminationImporter;
import com.sankuai.optaplanner.examples.machinereassignment.persistence.MachineReassignmentImporter;
import com.sankuai.optaplanner.examples.nqueens.persistence.NQueensGenerator;
import com.sankuai.optaplanner.examples.nurserostering.persistence.NurseRosteringImporter;
import com.sankuai.optaplanner.examples.pas.persistence.PatientAdmissionScheduleImporter;
import com.sankuai.optaplanner.examples.projectjobscheduling.persistence.ProjectJobSchedulingImporter;
import com.sankuai.optaplanner.examples.travelingtournament.persistence.TravelingTournamentImporter;
import com.sankuai.optaplanner.examples.tsp.persistence.TspImporter;
import com.sankuai.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingImporter;

public class AllExamplesSolutionImporter extends LoggingMain {

    public static void main(String[] args) {
        new AllExamplesSolutionImporter().importAll();
    }

    public void importAll() {
        NQueensGenerator.main(new String[0]);
        CloudBalancingGenerator.main(new String[0]);
        TspImporter.main(new String[0]);
        CurriculumCourseImporter.main(new String[0]);
        MachineReassignmentImporter.main(new String[0]);
        VehicleRoutingImporter.main(new String[0]);
        ProjectJobSchedulingImporter.main(new String[0]);
        PatientAdmissionScheduleImporter.main(new String[0]);
        ExaminationImporter.main(new String[0]);
        NurseRosteringImporter.main(new String[0]);
        TravelingTournamentImporter.main(new String[0]);
    }

}
