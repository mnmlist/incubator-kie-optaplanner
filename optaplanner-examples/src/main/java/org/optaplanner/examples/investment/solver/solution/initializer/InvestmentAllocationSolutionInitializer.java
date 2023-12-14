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

package com.sankuai.optaplanner.examples.investment.solver.solution.initializer;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.impl.phase.custom.CustomPhaseCommand;
import com.sankuai.optaplanner.examples.investment.domain.AssetClassAllocation;
import com.sankuai.optaplanner.examples.investment.domain.InvestmentSolution;
import com.sankuai.optaplanner.examples.investment.domain.util.InvestmentNumericUtil;

public class InvestmentAllocationSolutionInitializer implements CustomPhaseCommand<InvestmentSolution> {

    @Override
    public void changeWorkingSolution(ScoreDirector<InvestmentSolution> scoreDirector) {
        InvestmentSolution solution = scoreDirector.getWorkingSolution();
        distributeQuantityEvenly(scoreDirector, solution);
    }

    private void distributeQuantityEvenly(ScoreDirector<InvestmentSolution> scoreDirector, InvestmentSolution solution) {
        long budget = InvestmentNumericUtil.MAXIMUM_QUANTITY_MILLIS;
        int size = solution.getAssetClassAllocationList().size();
        long budgetPerAllocation = budget / size;
        long remainder = budget % size;
        for (AssetClassAllocation allocation : solution.getAssetClassAllocationList()) {
            long quantityMillis = budgetPerAllocation;
            if (remainder > 0L) {
                remainder--;
                quantityMillis++;
            }
            scoreDirector.beforeVariableChanged(allocation, "quantityMillis");
            allocation.setQuantityMillis(quantityMillis);
            scoreDirector.afterVariableChanged(allocation, "quantityMillis");
            scoreDirector.triggerVariableListeners();
        }
    }

}
