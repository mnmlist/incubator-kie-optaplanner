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

package com.sankuai.optaplanner.examples.coachshuttlegathering.domain;

import com.sankuai.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningScore;
import com.sankuai.optaplanner.core.api.domain.solution.PlanningSolution;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import com.sankuai.optaplanner.core.api.domain.solution.ProblemFactProperty;
import com.sankuai.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import com.sankuai.optaplanner.examples.coachshuttlegathering.domain.location.RoadLocation;
import com.sankuai.optaplanner.examples.common.domain.AbstractPersistable;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardsoftlong.HardSoftLongScoreXStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PlanningSolution
@XStreamAlias("CsgCoachShuttleGatheringSolution")
public class CoachShuttleGatheringSolution extends AbstractPersistable {

    protected String name;
    protected List<RoadLocation> locationList;
    protected List<Coach> coachList;
    protected List<Shuttle> shuttleList;
    protected List<BusStop> stopList;
    protected BusHub hub;

    @XStreamConverter(HardSoftLongScoreXStreamConverter.class)
    protected HardSoftLongScore score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ProblemFactCollectionProperty
    public List<RoadLocation> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<RoadLocation> locationList) {
        this.locationList = locationList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "coachRange")
    public List<Coach> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<Coach> coachList) {
        this.coachList = coachList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "shuttleRange")
    public List<Shuttle> getShuttleList() {
        return shuttleList;
    }

    public void setShuttleList(List<Shuttle> shuttleList) {
        this.shuttleList = shuttleList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "stopRange")
    public List<BusStop> getStopList() {
        return stopList;
    }

    public void setStopList(List<BusStop> stopList) {
        this.stopList = stopList;
    }

    @ProblemFactProperty
    public BusHub getHub() {
        return hub;
    }

    public void setHub(BusHub hub) {
        this.hub = hub;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @ValueRangeProvider(id = "hubRange")
    public List<BusHub> getHubRange() {
        return Collections.singletonList(hub);
    }

    public List<Bus> getBusList() {
        List<Bus> busList = new ArrayList<>(coachList.size() + shuttleList.size());
        busList.addAll(coachList);
        busList.addAll(shuttleList);
        return busList;
    }

}
