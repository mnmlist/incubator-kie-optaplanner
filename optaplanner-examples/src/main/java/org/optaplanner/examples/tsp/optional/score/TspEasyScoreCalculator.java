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

package com.sankuai.optaplanner.examples.tsp.optional.score;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sankuai.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import com.sankuai.optaplanner.core.api.score.calculator.EasyScoreCalculator;
import com.sankuai.optaplanner.examples.tsp.domain.Domicile;
import com.sankuai.optaplanner.examples.tsp.domain.Standstill;
import com.sankuai.optaplanner.examples.tsp.domain.TspSolution;
import com.sankuai.optaplanner.examples.tsp.domain.Visit;

public class TspEasyScoreCalculator implements EasyScoreCalculator<TspSolution, SimpleLongScore> {

    @Override
    public SimpleLongScore calculateScore(TspSolution tspSolution) {
        List<Visit> visitList = tspSolution.getVisitList();
        Set<Visit> tailVisitSet = new HashSet<>(visitList);
        long score = 0L;
        for (Visit visit : visitList) {
            Standstill previousStandstill = visit.getPreviousStandstill();
            if (previousStandstill != null) {
                score -= visit.getDistanceFromPreviousStandstill();
                if (previousStandstill instanceof Visit) {
                    tailVisitSet.remove(previousStandstill);
                }
            }
        }
        Domicile domicile = tspSolution.getDomicile();
        for (Visit tailVisit : tailVisitSet) {
            if (tailVisit.getPreviousStandstill() != null) {
                score -= tailVisit.getDistanceTo(domicile);
            }
        }
        return SimpleLongScore.of(score);
    }

}
