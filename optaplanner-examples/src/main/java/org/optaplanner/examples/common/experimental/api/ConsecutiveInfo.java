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

package com.sankuai.optaplanner.examples.common.experimental.api;

/**
 * Contains info regarding the consecutive sequences and breaks
 * in a collection of points.
 *
 * @param <ValueType_> The type of value in the sequence
 * @param <DifferenceType_> The type of difference between values in the sequence
 */
public interface ConsecutiveInfo<ValueType_, DifferenceType_ extends Comparable<DifferenceType_>> {
    /**
     * @return never null, an iterable that iterates through the sequences contained in
     *         the collection in ascending order
     */
    Iterable<Sequence<ValueType_, DifferenceType_>> getConsecutiveSequences();

    /**
     * @return never null, an iterable that iterates through the breaks contained in
     *         the collection in ascending order
     */
    Iterable<Break<ValueType_, DifferenceType_>> getBreaks();
}
