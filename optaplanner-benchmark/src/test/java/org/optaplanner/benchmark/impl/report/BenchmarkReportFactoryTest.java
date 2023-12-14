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

package com.sankuai.optaplanner.benchmark.impl.report;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import com.sankuai.optaplanner.benchmark.config.ranking.SolverRankingType;
import com.sankuai.optaplanner.benchmark.config.report.BenchmarkReportConfig;
import com.sankuai.optaplanner.benchmark.impl.ranking.TotalRankSolverRankingWeightFactory;
import com.sankuai.optaplanner.benchmark.impl.ranking.TotalScoreSolverRankingComparator;
import com.sankuai.optaplanner.benchmark.impl.result.PlannerBenchmarkResult;

class BenchmarkReportFactoryTest {

    @Test
    void buildWithSolverRankingTypeAndSolverRankingComparatorClass() {
        BenchmarkReportConfig config = new BenchmarkReportConfig();
        config.setSolverRankingType(SolverRankingType.TOTAL_RANKING);
        config.setSolverRankingComparatorClass(TotalScoreSolverRankingComparator.class);

        PlannerBenchmarkResult result = mock(PlannerBenchmarkResult.class);
        BenchmarkReportFactory reportFactory = new BenchmarkReportFactory(config);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> reportFactory.buildBenchmarkReport(result))
                .withMessageContaining("solverRankingType").withMessageContaining("solverRankingComparatorClass");
    }

    @Test
    void buildWithSolverRankingTypeAndSolverRankingWeightFactoryClass() {
        BenchmarkReportConfig config = new BenchmarkReportConfig();
        config.setSolverRankingType(SolverRankingType.TOTAL_RANKING);
        config.setSolverRankingWeightFactoryClass(TotalRankSolverRankingWeightFactory.class);

        PlannerBenchmarkResult result = mock(PlannerBenchmarkResult.class);
        BenchmarkReportFactory reportFactory = new BenchmarkReportFactory(config);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> reportFactory.buildBenchmarkReport(result))
                .withMessageContaining("solverRankingType").withMessageContaining("solverRankingWeightFactoryClass");
    }

    @Test
    void buildWithSolverRankingComparatorClassAndSolverRankingWeightFactoryClass() {
        BenchmarkReportConfig config = new BenchmarkReportConfig();
        config.setSolverRankingComparatorClass(TotalScoreSolverRankingComparator.class);
        config.setSolverRankingWeightFactoryClass(TotalRankSolverRankingWeightFactory.class);

        PlannerBenchmarkResult result = mock(PlannerBenchmarkResult.class);
        BenchmarkReportFactory reportFactory = new BenchmarkReportFactory(config);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> reportFactory.buildBenchmarkReport(result))
                .withMessageContaining("solverRankingComparatorClass").withMessageContaining("solverRankingWeightFactoryClass");
    }
}
