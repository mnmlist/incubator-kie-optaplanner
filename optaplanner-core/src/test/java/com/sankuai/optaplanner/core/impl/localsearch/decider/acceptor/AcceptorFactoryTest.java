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

package com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor;

import com.sankuai.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import com.sankuai.optaplanner.core.config.localsearch.decider.acceptor.AcceptorType;
import com.sankuai.optaplanner.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig;
import com.sankuai.optaplanner.core.config.localsearch.decider.acceptor.stepcountinghillclimbing.StepCountingHillClimbingType;
import com.sankuai.optaplanner.core.config.solver.EnvironmentMode;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.greatdeluge.GreatDelugeAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.hillclimbing.HillClimbingAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.lateacceptance.LateAcceptanceAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.simulatedannealing.SimulatedAnnealingAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.stepcountinghillclimbing.StepCountingHillClimbingAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.tabu.EntityTabuAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.tabu.MoveTabuAcceptor;
import com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.tabu.ValueTabuAcceptor;
import com.sankuai.optaplanner.core.impl.score.buildin.hardsoft.HardSoftScoreDefinition;
import com.sankuai.optaplanner.core.impl.score.definition.ScoreDefinition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AcceptorFactoryTest {

    @Test
    <Solution_> void buildCompositeAcceptor() {
        LocalSearchAcceptorConfig localSearchAcceptorConfig = new LocalSearchAcceptorConfig()
                .withAcceptorTypeList(Arrays.asList(AcceptorType.values()))
                .withEntityTabuSize(1)
                .withFadingEntityTabuSize(1)
                .withMoveTabuSize(1)
                .withFadingMoveTabuSize(1)
                .withUndoMoveTabuSize(1)
                .withValueTabuSize(1)
                .withFadingValueTabuSize(1)
                .withLateAcceptanceSize(10)
                .withSimulatedAnnealingStartingTemperature("-10hard/-10soft")
                .withStepCountingHillClimbingSize(1)
                .withStepCountingHillClimbingType(StepCountingHillClimbingType.IMPROVING_STEP);

        HeuristicConfigPolicy<Solution_> heuristicConfigPolicy = mock(HeuristicConfigPolicy.class);
        ScoreDefinition<HardSoftScore> scoreDefinition = new HardSoftScoreDefinition();
        when(heuristicConfigPolicy.getEnvironmentMode()).thenReturn(EnvironmentMode.NON_INTRUSIVE_FULL_ASSERT);
        when(heuristicConfigPolicy.getScoreDefinition()).thenReturn(scoreDefinition);

        AcceptorFactory<Solution_> acceptorFactory = AcceptorFactory.create(localSearchAcceptorConfig);
        Acceptor<Solution_> acceptor = acceptorFactory.buildAcceptor(heuristicConfigPolicy);
        assertThat(acceptor).isExactlyInstanceOf(CompositeAcceptor.class);
        CompositeAcceptor<Solution_> compositeAcceptor = (CompositeAcceptor<Solution_>) acceptor;
        assertThat(compositeAcceptor.acceptorList).hasSize(AcceptorType.values().length);
        assertAcceptorTypeAtPosition(compositeAcceptor, 0, HillClimbingAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 1, StepCountingHillClimbingAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 2, EntityTabuAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 3, ValueTabuAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 4, MoveTabuAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 5, MoveTabuAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 6, SimulatedAnnealingAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 7, LateAcceptanceAcceptor.class);
        assertAcceptorTypeAtPosition(compositeAcceptor, 8, GreatDelugeAcceptor.class);
    }

    private <Solution_, Acceptor_ extends Acceptor<Solution_>> void assertAcceptorTypeAtPosition(
            CompositeAcceptor<Solution_> compositeAcceptor, int position, Class<Acceptor_> expectedAcceptorType) {
        assertThat(compositeAcceptor.acceptorList.get(position)).isExactlyInstanceOf(expectedAcceptorType);
    }

    @Test
    <Solution_> void noAcceptorConfigured_throwsException() {
        AcceptorFactory<Solution_> acceptorFactory = AcceptorFactory.create(new LocalSearchAcceptorConfig());
        assertThatIllegalArgumentException().isThrownBy(() -> acceptorFactory.buildAcceptor(mock(HeuristicConfigPolicy.class)))
                .withMessageContaining("The acceptor does not specify any acceptorType");
    }
}
