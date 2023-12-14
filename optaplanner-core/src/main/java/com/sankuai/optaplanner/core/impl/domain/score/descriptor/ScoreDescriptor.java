/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sankuai.optaplanner.core.impl.domain.score.descriptor;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.score.AbstractBendableScore;
import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.api.score.buildin.bendable.BendableScore;
import com.sankuai.optaplanner.core.api.score.buildin.bendablebigdecimal.BendableBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.bendablelong.BendableLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import com.sankuai.optaplanner.core.config.util.ConfigUtils;
import com.sankuai.optaplanner.core.impl.domain.common.accessor.MemberAccessor;
import com.sankuai.optaplanner.core.impl.domain.common.accessor.MemberAccessorFactory;
import com.sankuai.optaplanner.core.impl.domain.policy.DescriptorPolicy;
import com.sankuai.optaplanner.core.impl.score.buildin.bendable.BendableScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.bendablebigdecimal.BendableBigDecimalScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.bendablelong.BendableLongScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardmediumsoft.HardMediumSoftScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardmediumsoftlong.HardMediumSoftLongScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardsoft.HardSoftScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.hardsoftlong.HardSoftLongScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.simple.SimpleScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.simplebigdecimal.SimpleBigDecimalScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.buildin.simplelong.SimpleLongScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.definition.ScoreDefinition;

import java.lang.reflect.Member;

import static com.sankuai.optaplanner.core.impl.domain.common.accessor.MemberAccessorFactory.MemberAccessorType.FIELD_OR_GETTER_METHOD_WITH_SETTER;

public class ScoreDescriptor {

    // Used to obtain default @PlanningScore attribute values from a score member that was auto-discovered,
    // as if it had an empty @PlanningScore annotation on it.
    @PlanningScore
    private static final Object PLANNING_SCORE = new Object();

    private final MemberAccessor scoreMemberAccessor;
    private final ScoreDefinition<?> scoreDefinition;

    private ScoreDescriptor(MemberAccessor scoreMemberAccessor, ScoreDefinition<?> scoreDefinition) {
        this.scoreMemberAccessor = scoreMemberAccessor;
        this.scoreDefinition = scoreDefinition;
    }

    public static ScoreDescriptor buildScoreDescriptor(
            DescriptorPolicy descriptorPolicy,
            Member member,
            Class<?> solutionClass) {
        MemberAccessor scoreMemberAccessor = buildScoreMemberAccessor(descriptorPolicy, member);
        Class<? extends Score<?>> scoreType = extractScoreType(scoreMemberAccessor, solutionClass);
        PlanningScore annotation = extractPlanningScoreAnnotation(scoreMemberAccessor);
        ScoreDefinition<?> scoreDefinition = buildScoreDefinition(solutionClass, scoreMemberAccessor, scoreType, annotation);
        return new ScoreDescriptor(scoreMemberAccessor, scoreDefinition);
    }

    private static MemberAccessor buildScoreMemberAccessor(DescriptorPolicy descriptorPolicy, Member member) {
        return MemberAccessorFactory.buildMemberAccessor(
                member,
                FIELD_OR_GETTER_METHOD_WITH_SETTER,
                PlanningScore.class,
                descriptorPolicy.getDomainAccessType(),
                descriptorPolicy.getGeneratedMemberAccessorMap());
    }

    private static Class<? extends Score<?>> extractScoreType(MemberAccessor scoreMemberAccessor, Class<?> solutionClass) {
        Class<?> memberType = scoreMemberAccessor.getType();
        if (!Score.class.isAssignableFrom(memberType)) {
            throw new IllegalStateException("The solutionClass (" + solutionClass
                    + ") has a @" + PlanningScore.class.getSimpleName()
                    + " annotated member (" + scoreMemberAccessor + ") that does not return a subtype of Score.");
        }
        if (memberType == Score.class) {
            throw new IllegalStateException("The solutionClass (" + solutionClass
                    + ") has a @" + PlanningScore.class.getSimpleName()
                    + " annotated member (" + scoreMemberAccessor
                    + ") that doesn't return a non-abstract " + Score.class.getSimpleName() + " class.\n"
                    + "Maybe make it return " + HardSoftScore.class.getSimpleName()
                    + " or another specific " + Score.class.getSimpleName() + " implementation.");
        }
        return (Class<? extends Score<?>>) memberType;
    }

    private static PlanningScore extractPlanningScoreAnnotation(MemberAccessor scoreMemberAccessor) {
        PlanningScore annotation = scoreMemberAccessor.getAnnotation(PlanningScore.class);
        if (annotation != null) {
            return annotation;
        }
        // The member was auto-discovered.
        try {
            return ScoreDescriptor.class.getDeclaredField("PLANNING_SCORE").getAnnotation(PlanningScore.class);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Impossible situation: the field (PLANNING_SCORE) must exist.", e);
        }
    }

    private static ScoreDefinition<?> buildScoreDefinition(
            Class<?> solutionClass,
            MemberAccessor scoreMemberAccessor,
            Class<? extends Score<?>> scoreType,
            PlanningScore annotation) {
        Class<? extends ScoreDefinition> scoreDefinitionClass = annotation.scoreDefinitionClass();
        int bendableHardLevelsSize = annotation.bendableHardLevelsSize();
        int bendableSoftLevelsSize = annotation.bendableSoftLevelsSize();
        if (scoreDefinitionClass != PlanningScore.NullScoreDefinition.class) {
            if (bendableHardLevelsSize != PlanningScore.NO_LEVEL_SIZE
                    || bendableSoftLevelsSize != PlanningScore.NO_LEVEL_SIZE) {
                throw new IllegalArgumentException("The solutionClass (" + solutionClass
                        + ") has a @" + PlanningScore.class.getSimpleName()
                        + " annotated member (" + scoreMemberAccessor
                        + ") that has a scoreDefinition (" + scoreDefinitionClass
                        + ") that must not have a bendableHardLevelsSize (" + bendableHardLevelsSize
                        + ") or a bendableSoftLevelsSize (" + bendableSoftLevelsSize + ").");
            }
            return ConfigUtils.newInstance(() -> scoreMemberAccessor + " with @" + PlanningScore.class.getSimpleName(),
                    "scoreDefinitionClass", scoreDefinitionClass);
        }
        if (!AbstractBendableScore.class.isAssignableFrom(scoreType)) {
            if (bendableHardLevelsSize != PlanningScore.NO_LEVEL_SIZE
                    || bendableSoftLevelsSize != PlanningScore.NO_LEVEL_SIZE) {
                throw new IllegalArgumentException("The solutionClass (" + solutionClass
                        + ") has a @" + PlanningScore.class.getSimpleName()
                        + " annotated member (" + scoreMemberAccessor
                        + ") that returns a scoreType (" + scoreType
                        + ") that must not have a bendableHardLevelsSize (" + bendableHardLevelsSize
                        + ") or a bendableSoftLevelsSize (" + bendableSoftLevelsSize + ").");
            }
            if (scoreType.equals(SimpleScore.class)) {
                return new SimpleScoreDefinition();
            } else if (scoreType.equals(SimpleLongScore.class)) {
                return new SimpleLongScoreDefinition();
            } else if (scoreType.equals(SimpleBigDecimalScore.class)) {
                return new SimpleBigDecimalScoreDefinition();
            } else if (scoreType.equals(HardSoftScore.class)) {
                return new HardSoftScoreDefinition();
            } else if (scoreType.equals(HardSoftLongScore.class)) {
                return new HardSoftLongScoreDefinition();
            } else if (scoreType.equals(HardSoftBigDecimalScore.class)) {
                return new HardSoftBigDecimalScoreDefinition();
            } else if (scoreType.equals(HardMediumSoftScore.class)) {
                return new HardMediumSoftScoreDefinition();
            } else if (scoreType.equals(HardMediumSoftLongScore.class)) {
                return new HardMediumSoftLongScoreDefinition();
            } else if (scoreType.equals(HardMediumSoftBigDecimalScore.class)) {
                return new HardMediumSoftBigDecimalScoreDefinition();
            } else {
                throw new IllegalArgumentException("The solutionClass (" + solutionClass
                        + ") has a @" + PlanningScore.class.getSimpleName()
                        + " annotated member (" + scoreMemberAccessor
                        + ") that returns a scoreType (" + scoreType
                        + ") that is not recognized as a default " + Score.class.getSimpleName() + " implementation.\n"
                        + "  If you intend to use a custom implementation,"
                        + " maybe set a scoreDefinition in the @" + PlanningScore.class.getSimpleName()
                        + " annotation.");
            }
        } else {
            if (bendableHardLevelsSize == PlanningScore.NO_LEVEL_SIZE
                    || bendableSoftLevelsSize == PlanningScore.NO_LEVEL_SIZE) {
                throw new IllegalArgumentException("The solutionClass (" + solutionClass
                        + ") has a @" + PlanningScore.class.getSimpleName()
                        + " annotated member (" + scoreMemberAccessor
                        + ") that returns a scoreType (" + scoreType
                        + ") that must have a bendableHardLevelsSize (" + bendableHardLevelsSize
                        + ") and a bendableSoftLevelsSize (" + bendableSoftLevelsSize + ").");
            }
            if (scoreType.equals(BendableScore.class)) {
                return new BendableScoreDefinition(bendableHardLevelsSize, bendableSoftLevelsSize);
            } else if (scoreType.equals(BendableLongScore.class)) {
                return new BendableLongScoreDefinition(bendableHardLevelsSize, bendableSoftLevelsSize);
            } else if (scoreType.equals(BendableBigDecimalScore.class)) {
                return new BendableBigDecimalScoreDefinition(bendableHardLevelsSize, bendableSoftLevelsSize);
            } else {
                throw new IllegalArgumentException("The solutionClass (" + solutionClass
                        + ") has a @" + PlanningScore.class.getSimpleName()
                        + " annotated member (" + scoreMemberAccessor
                        + ") that returns a bendable scoreType (" + scoreType
                        + ") that is not recognized as a default " + Score.class.getSimpleName() + " implementation.\n"
                        + "  If you intend to use a custom implementation,"
                        + " maybe set a scoreDefinition in the annotation.");
            }
        }
    }

    public ScoreDefinition<?> getScoreDefinition() {
        return scoreDefinition;
    }

    public Class<? extends Score<?>> getScoreClass() {
        return scoreDefinition.getScoreClass();
    }

    public Score<?> getScore(Object solution) {
        return (Score<?>) scoreMemberAccessor.executeGetter(solution);
    }

    public void setScore(Object solution, Score<?> score) {
        scoreMemberAccessor.executeSetter(solution, score);
    }

    public void failFastOnDuplicateMember(DescriptorPolicy descriptorPolicy, Member member, Class<?> solutionClass) {
        MemberAccessor memberAccessor = buildScoreMemberAccessor(descriptorPolicy, member);
        // A solution class cannot have more than one score field or bean property (name check), and the @PlanningScore
        // annotation cannot appear on both the score field and its getter (member accessor class check).
        if (!scoreMemberAccessor.getName().equals(memberAccessor.getName())
                || !scoreMemberAccessor.getClass().equals(memberAccessor.getClass())) {
            throw new IllegalStateException("The solutionClass (" + solutionClass
                    + ") has a @" + PlanningScore.class.getSimpleName()
                    + " annotated member (" + memberAccessor
                    + ") that is duplicated by another member (" + scoreMemberAccessor + ").\n"
                    + "Maybe the annotation is defined on both the field and its getter.");
        }
    }
}
