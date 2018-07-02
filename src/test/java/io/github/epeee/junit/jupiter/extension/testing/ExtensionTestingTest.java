package io.github.epeee.junit.jupiter.extension.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.epeee.junit.jupiter.extension.testing.ExtensionTesting.assertThatTest;

class ExtensionTestingTest {

    @Test
    void test() {
        assertThatTest(SampleClasses.HasNoAbortedTests.class).hasNoAbortedTests();
    }

    @Test
    void testHasAbortedTest() {
        assertThatTest(SampleClasses.HasAbortedTests.class).hasAbortedTests();
        Assertions.assertThatThrownBy(() -> assertThatTest(SampleClasses.HasAbortedTests.class).hasNoAbortedTests()).hasMessageContaining("Had aborted tests");
        assertThatTest(SampleClasses.HasAbortedTests.class).hasAbortedTests(1);
    }
}
