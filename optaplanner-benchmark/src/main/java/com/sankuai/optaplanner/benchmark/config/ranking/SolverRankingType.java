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

package com.sankuai.optaplanner.benchmark.config.ranking;

import com.sankuai.optaplanner.benchmark.impl.ranking.TotalRankSolverRankingWeightFactory;
import com.sankuai.optaplanner.benchmark.impl.ranking.TotalScoreSolverRankingComparator;
import com.sankuai.optaplanner.benchmark.impl.ranking.WorstScoreSolverRankingComparator;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum SolverRankingType {
    /**
     * Maximize the overall score, so minimize the overall cost if all {@link PlanningSolution}s would be executed.
     *
     * @see TotalScoreSolverRankingComparator
     */
    TOTAL_SCORE,
    /**
     * Minimize the worst case scenario.
     *
     * @see WorstScoreSolverRankingComparator
     */
    WORST_SCORE,
    /**
     * Maximize the overall ranking.
     *
     * @see TotalRankSolverRankingWeightFactory
     */
    TOTAL_RANKING;

}
