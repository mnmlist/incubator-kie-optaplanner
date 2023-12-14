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

package com.sankuai.optaplanner.benchmark.config.report;

import com.sankuai.optaplanner.benchmark.config.ranking.SolverRankingType;
import com.sankuai.optaplanner.benchmark.impl.ranking.TotalRankSolverRankingWeightFactory;
import com.sankuai.optaplanner.benchmark.impl.ranking.TotalScoreSolverRankingComparator;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class BenchmarkReportConfigTest {

    @Test
    public void inheritBenchmarkReportConfig() {
        BenchmarkReportConfig inheritedReportConfig = new BenchmarkReportConfig();
        inheritedReportConfig.setLocale(Locale.CANADA);
        inheritedReportConfig.setSolverRankingType(SolverRankingType.TOTAL_RANKING);
        inheritedReportConfig.setSolverRankingComparatorClass(TotalScoreSolverRankingComparator.class);
        inheritedReportConfig.setSolverRankingWeightFactoryClass(TotalRankSolverRankingWeightFactory.class);

        BenchmarkReportConfig reportConfig = new BenchmarkReportConfig(inheritedReportConfig);

        assertThat(reportConfig.getLocale()).isEqualTo(inheritedReportConfig.getLocale());
        assertThat(reportConfig.getSolverRankingType()).isEqualTo(inheritedReportConfig.getSolverRankingType());
        assertThat(reportConfig.getSolverRankingComparatorClass())
                .isEqualTo(inheritedReportConfig.getSolverRankingComparatorClass());
        assertThat(reportConfig.getSolverRankingWeightFactoryClass())
                .isEqualTo(inheritedReportConfig.getSolverRankingWeightFactoryClass());
    }
}
