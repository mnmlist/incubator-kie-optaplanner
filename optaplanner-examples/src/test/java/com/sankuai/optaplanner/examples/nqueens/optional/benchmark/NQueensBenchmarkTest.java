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

package com.sankuai.optaplanner.examples.nqueens.optional.benchmark;

import com.sankuai.optaplanner.benchmark.api.PlannerBenchmark;
import com.sankuai.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import com.sankuai.optaplanner.benchmark.config.PlannerBenchmarkConfig;
import com.sankuai.optaplanner.benchmark.config.ProblemBenchmarksConfig;
import com.sankuai.optaplanner.benchmark.config.statistic.ProblemStatisticType;
import com.sankuai.optaplanner.benchmark.config.statistic.SingleStatisticType;
import com.sankuai.optaplanner.benchmark.impl.DefaultPlannerBenchmark;
import com.sankuai.optaplanner.examples.common.app.PlannerBenchmarkTest;
import com.sankuai.optaplanner.examples.nqueens.app.NQueensApp;
import com.sankuai.optaplanner.examples.nqueens.domain.NQueens;
import com.sankuai.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.util.Arrays;

public class NQueensBenchmarkTest extends PlannerBenchmarkTest {

    public NQueensBenchmarkTest() {
        super(NQueensApp.SOLVER_CONFIG);
    }

    // ************************************************************************
    // Tests
    // ************************************************************************

    @Timeout(600)
    public void benchmark64queens() {
        NQueens problem = new XStreamSolutionFileIO<NQueens>(NQueens.class)
                .read(new File("data/nqueens/unsolved/64queens.xml"));
        PlannerBenchmarkConfig benchmarkConfig = buildPlannerBenchmarkConfig();
        addAllStatistics(benchmarkConfig);
        benchmarkConfig.setParallelBenchmarkCount("AUTO");
        PlannerBenchmark benchmark = PlannerBenchmarkFactory.create(benchmarkConfig).buildPlannerBenchmark(problem);
        benchmark.benchmark();
    }

    @Test
    @Timeout(600)
    public void benchmark64queensSingleThread() {
        NQueens problem = new XStreamSolutionFileIO<NQueens>(NQueens.class)
                .read(new File("data/nqueens/unsolved/64queens.xml"));
        PlannerBenchmarkConfig benchmarkConfig = buildPlannerBenchmarkConfig();
        addAllStatistics(benchmarkConfig);
        benchmarkConfig.setParallelBenchmarkCount("1");
        PlannerBenchmark benchmark = PlannerBenchmarkFactory.create(benchmarkConfig).buildPlannerBenchmark(problem);
        benchmark.benchmark();
    }

    protected void addAllStatistics(PlannerBenchmarkConfig benchmarkConfig) {
        ProblemBenchmarksConfig problemBenchmarksConfig = new ProblemBenchmarksConfig();
        problemBenchmarksConfig.setSingleStatisticTypeList(Arrays.asList(SingleStatisticType.values()));
        problemBenchmarksConfig.setProblemStatisticTypeList(Arrays.asList(ProblemStatisticType.values()));
        benchmarkConfig.getInheritedSolverBenchmarkConfig().setProblemBenchmarksConfig(problemBenchmarksConfig);
    }

    @Test
    public void benchmarkDirectoryNameDuplication() {
        NQueens problem = new XStreamSolutionFileIO<NQueens>(NQueens.class)
                .read(new File("data/nqueens/unsolved/4queens.xml"));
        PlannerBenchmarkConfig benchmarkConfig = buildPlannerBenchmarkConfig();
        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.create(benchmarkConfig);
        DefaultPlannerBenchmark plannerBenchmark = (DefaultPlannerBenchmark) benchmarkFactory.buildPlannerBenchmark(problem);
        plannerBenchmark.benchmarkingStarted();
        plannerBenchmark.getPlannerBenchmarkResult().initBenchmarkReportDirectory(benchmarkConfig.getBenchmarkDirectory());
        plannerBenchmark.getPlannerBenchmarkResult().initBenchmarkReportDirectory(benchmarkConfig.getBenchmarkDirectory());
    }

}
