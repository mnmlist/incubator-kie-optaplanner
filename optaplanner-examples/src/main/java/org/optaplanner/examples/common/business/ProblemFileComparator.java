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

package com.sankuai.optaplanner.examples.common.business;

import java.io.File;
import java.util.Comparator;

public class ProblemFileComparator implements Comparator<File> {

    private static final AlphaNumericStringComparator ALPHA_NUMERIC_STRING_COMPARATOR = new AlphaNumericStringComparator();
    private static final Comparator<File> COMPARATOR = Comparator.comparing(File::getParent, ALPHA_NUMERIC_STRING_COMPARATOR)
            .thenComparing(File::isDirectory)
            .thenComparing(f -> !f.getName().toLowerCase().startsWith("demo"))
            .thenComparing(f -> f.getName().toLowerCase(), ALPHA_NUMERIC_STRING_COMPARATOR)
            .thenComparing(File::getName);

    @Override
    public int compare(File a, File b) {
        return COMPARATOR.compare(a, b);
    }
}
