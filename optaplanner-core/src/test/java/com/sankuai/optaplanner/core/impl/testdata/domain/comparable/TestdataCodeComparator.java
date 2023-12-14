package com.sankuai.optaplanner.core.impl.testdata.domain.comparable;

import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;

import java.util.Comparator;

public class TestdataCodeComparator implements Comparator<TestdataObject> {

    private static final Comparator<TestdataObject> COMPARATOR = Comparator.comparing(TestdataObject::getCode);

    public int compare(TestdataObject a, TestdataObject b) {
        return COMPARATOR.compare(a, b);
    }
}
