/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.cloudbalancing.app;

import java.time.Duration;

import com.sankuai.optaplanner.core.api.solver.Solver;
import com.sankuai.optaplanner.core.api.solver.SolverFactory;
import com.sankuai.optaplanner.core.config.solver.SolverConfig;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudComputer;
import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudProcess;
import com.sankuai.optaplanner.examples.cloudbalancing.optional.benchmark.CloudBalancingBenchmarkHelloWorld;
import com.sankuai.optaplanner.examples.cloudbalancing.optional.score.CloudBalancingConstraintProvider;
import com.sankuai.optaplanner.examples.cloudbalancing.persistence.CloudBalancingGenerator;

/**
 * To benchmark this solver config, run {@link CloudBalancingBenchmarkHelloWorld} instead.
 */
public class CloudBalancingHelloWorld {

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory<CloudBalance> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(CloudBalance.class)
                .withEntityClasses(CloudProcess.class)
                .withConstraintProviderClass(CloudBalancingConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofMinutes(2)));
        Solver<CloudBalance> solver = solverFactory.buildSolver();

        // Load a problem with 400 computers and 1200 processes
        CloudBalance unsolvedCloudBalance = new CloudBalancingGenerator().createCloudBalance(400, 1200);

        // Solve the problem
        CloudBalance solvedCloudBalance = solver.solve(unsolvedCloudBalance);

        // Display the result
        System.out.println("\nSolved cloudBalance with 400 computers and 1200 processes:\n"
                + toDisplayString(solvedCloudBalance));
    }

    public static String toDisplayString(CloudBalance cloudBalance) {
        StringBuilder displayString = new StringBuilder();
        for (CloudProcess process : cloudBalance.getProcessList()) {
            CloudComputer computer = process.getComputer();
            displayString.append("  ").append(process.getLabel()).append(" -> ")
                    .append(computer == null ? null : computer.getLabel()).append("\n");
        }
        return displayString.toString();
    }

}
