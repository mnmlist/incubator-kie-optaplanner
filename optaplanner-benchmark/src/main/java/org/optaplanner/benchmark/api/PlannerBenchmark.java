/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.benchmark.api;

import java.io.File;

/**
 * A planner benchmark that runs a number of single benchmarks.
 * <p>
 * Build by a {@link PlannerBenchmarkFactory}.
 */
public interface PlannerBenchmark {

    /**
     * Run all the single benchmarks and create an overview report.
     *
     * @return never null, the directory in which the benchmark results are stored
     */
    File benchmark();

    /**
     * Run all the single benchmarks, create an overview report
     * and show it in the default browser.
     *
     * @return never null, the directory in which the benchmark results are stored
     */
    File benchmarkAndShowReportInBrowser();

}
