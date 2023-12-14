package com.sankuai.optaplanner.examples.investment.optional.score;

import com.sankuai.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import com.sankuai.optaplanner.core.api.score.stream.Constraint;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintCollectors;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintFactory;
import com.sankuai.optaplanner.core.api.score.stream.ConstraintProvider;
import com.sankuai.optaplanner.core.api.score.stream.Joiners;
import com.sankuai.optaplanner.examples.investment.domain.AssetClassAllocation;
import com.sankuai.optaplanner.examples.investment.domain.InvestmentParametrization;
import com.sankuai.optaplanner.examples.investment.domain.Region;
import com.sankuai.optaplanner.examples.investment.domain.Sector;

import java.util.function.Function;

public class InvestmentConstraintProvider implements ConstraintProvider {

    private static final String CONSTRAINT_PACKAGE = "com.sankuai.optaplanner.examples.investment.solver";

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                assetsDeviationGreaterThanMaximumPenalty(constraintFactory),
                regionQuantityGreaterThanMaximumPenalty(constraintFactory),
                sectorQuantityGreaterThanMaximumPenalty(constraintFactory),
                assetExpectedReturnReward(constraintFactory)
        };
    }

    private Constraint assetsDeviationGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.from(AssetClassAllocation.class)
                .join(AssetClassAllocation.class)
                .groupBy(ConstraintCollectors.sumLong(AssetClassAllocation::calculateSquaredStandardDeviationFemtosFromTo))
                .join(InvestmentParametrization.class)
                .filter((deviation,
                        parametrization) -> deviation > parametrization.calculateSquaredStandardDeviationFemtosMaximum())
                .penalizeLong(CONSTRAINT_PACKAGE, "Standard deviation maximum", HardSoftLongScore.ONE_HARD, (deviation,
                        parametrization) -> deviation - parametrization.calculateSquaredStandardDeviationFemtosMaximum());
    }

    private Constraint regionQuantityGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.from(Region.class)
                .join(AssetClassAllocation.class, Joiners.equal(Function.identity(), AssetClassAllocation::getRegion))
                .groupBy((region, asset) -> region, ConstraintCollectors.sumLong((region, asset) -> asset.getQuantityMillis()))
                .filter((region, totalQuantity) -> totalQuantity > region.getQuantityMillisMaximum())
                .penalizeLong(CONSTRAINT_PACKAGE, "Region quantity maximum", HardSoftLongScore.ONE_HARD,
                        (region, totalQuantity) -> totalQuantity - region.getQuantityMillisMaximum());
    }

    private Constraint sectorQuantityGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.from(Sector.class)
                .join(AssetClassAllocation.class, Joiners.equal(Function.identity(), AssetClassAllocation::getSector))
                .groupBy((sector, asset) -> sector, ConstraintCollectors.sumLong((sector, asset) -> asset.getQuantityMillis()))
                .filter((sector, totalQuantity) -> totalQuantity > sector.getQuantityMillisMaximum())
                .penalizeLong(CONSTRAINT_PACKAGE, "Sector quantity maximum", HardSoftLongScore.ONE_HARD,
                        (sector, totalQuantity) -> totalQuantity - sector.getQuantityMillisMaximum());
    }

    private Constraint assetExpectedReturnReward(ConstraintFactory cf) {
        return cf.from(AssetClassAllocation.class)
                .rewardLong(CONSTRAINT_PACKAGE, "Maximize expected return", HardSoftLongScore.ONE_SOFT,
                        AssetClassAllocation::getQuantifiedExpectedReturnMicros);
    }
}
