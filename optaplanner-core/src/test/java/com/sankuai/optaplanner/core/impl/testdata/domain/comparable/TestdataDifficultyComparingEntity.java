package com.sankuai.optaplanner.core.impl.testdata.domain.comparable;

import com.sankuai.optaplanner.core.api.domain.entity.PlanningEntity;
import com.sankuai.optaplanner.core.api.domain.variable.PlanningVariable;
import com.sankuai.optaplanner.core.impl.domain.entity.descriptor.EntityDescriptor;
import com.sankuai.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataObject;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningEntity(difficultyComparatorClass = TestdataCodeComparator.class)
public class TestdataDifficultyComparingEntity extends TestdataObject {

    public static EntityDescriptor<TestdataDifficultyComparingSolution> buildEntityDescriptor() {
        return TestdataDifficultyComparingSolution.buildSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataDifficultyComparingEntity.class);
    }

    public static GenuineVariableDescriptor<TestdataDifficultyComparingSolution> buildVariableDescriptorForValue() {
        return buildEntityDescriptor().getGenuineVariableDescriptor("value");
    }

    private TestdataValue value;

    public TestdataDifficultyComparingEntity() {
    }

    public TestdataDifficultyComparingEntity(String code) {
        super(code);
    }

    public TestdataDifficultyComparingEntity(String code, TestdataValue value) {
        this(code);
        this.value = value;
    }

    @PlanningVariable(valueRangeProviderRefs = { "valueRange" }, strengthComparatorClass = TestdataCodeComparator.class)
    public TestdataValue getValue() {
        return value;
    }

    public void setValue(TestdataValue value) {
        this.value = value;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************
}
