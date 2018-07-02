package io.github.epeee.junit.jupiter.extension.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;

import java.util.Map;

public class TestResultAssert {

    private final Map<TestDescriptor, TestExecutionResult> map;

    public TestResultAssert(Map<TestDescriptor, TestExecutionResult> map) {
        this.map = map;
    }

    public TestResultAssert hasAbortedTests() {
        if (map.values().stream().filter(result -> result.getStatus() == TestExecutionResult.Status.ABORTED).count() == 0) {
            Assertions.fail("Had no aborted tests");
        }
        return this;
    }

    public TestResultAssert hasNoAbortedTests() {
        if (map.values().stream().filter(result -> result.getStatus() == TestExecutionResult.Status.ABORTED).count() > 0) {
            Assertions.fail("Had aborted tests");
        }
        return this;
    }


    public TestResultAssert hasAbortedTests(int nr) {
        if (map.values().stream().filter(result -> result.getStatus() == TestExecutionResult.Status.ABORTED).count() != nr) {
            Assertions.fail("Had wrong count of aborted tests");
        }
        return this;
    }
}
