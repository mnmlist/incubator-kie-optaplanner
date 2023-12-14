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

package com.sankuai.optaplanner.examples.examination.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.sankuai.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Topic")
public class Topic extends AbstractPersistable {

    private int duration; // in minutes
    private Set<Student> studentSet;

    // Calculated during initialization, not modified during score calculation.
    private boolean frontLoadLarge;
    private Set<Topic> coincidenceTopicSet = null;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public int getStudentSize() {
        return studentSet.size();
    }

    public boolean isFrontLoadLarge() {
        return frontLoadLarge;
    }

    public void setFrontLoadLarge(boolean frontLoadLarge) {
        this.frontLoadLarge = frontLoadLarge;
    }

    public Set<Topic> getCoincidenceTopicSet() {
        return coincidenceTopicSet;
    }

    public void setCoincidenceTopicSet(Set<Topic> coincidenceTopicSet) {
        this.coincidenceTopicSet = coincidenceTopicSet;
    }

    public boolean hasCoincidenceTopic() {
        return coincidenceTopicSet != null;
    }

    @Override
    public String toString() {
        return id == null ? "no id" : Long.toString(id);
    }

    // ************************************************************************
    // With methods
    // ************************************************************************

    public Topic withId(long id) {
        this.setId(id);
        return this;
    }

    public Topic withDuration(int duration) {
        this.setDuration(duration);
        return this;
    }

    public Topic withStudents(Student... students) {
        this.setStudentSet(Arrays.stream(students).collect(Collectors.toSet()));
        return this;
    }

    public Topic withFrontLoadLarge(boolean frontLoadLarge) {
        this.setFrontLoadLarge(frontLoadLarge);
        return this;
    }

    public Topic withCoincidenceTopicSet(Set<Topic> coincidenceTopicSet) {
        this.setCoincidenceTopicSet(coincidenceTopicSet);
        return this;
    }
}
