package io.github.epeee.junit.jupiter.extension.testing;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;

import static org.assertj.core.api.Assertions.assertThat;

class TestExecutionUtilTest {

    private static final Condition<TestExecutionResult> TEST_ABORTED = new Condition<TestExecutionResult>("test aborted") {

        @Override
        public boolean matches(TestExecutionResult value) {
            return value.getStatus() == TestExecutionResult.Status.ABORTED;
        }
    };

    @Test
    void runTestsSize() {
        assertThat(TestExecutionUtil.runTests(SampleClasses.MyJupiterTest.class)).isNotEmpty().hasSize(3);
    }

    @Test
    void runTestsContentAborted() {
        assertThat(TestExecutionUtil.runTests(SampleClasses.HasAbortedTests.class)).hasValueSatisfying(TEST_ABORTED);
        assertThat(TestExecutionUtil.runTests(SampleClasses.MyJupiterTest.class)).hasValueSatisfying(TEST_ABORTED);
    }
}
