package io.github.epeee.junit.jupiter.extension.testing;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.epeee.junit.jupiter.extension.testing.ExtensionTesting.assertThatTest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExtensionTestingTest {

    @Test
    void test() {
        assertThatTest(SampleClasses.MyJupiterTest.class).hasSuccessfulTests(1).hasFailedTests(1).hasAbortedTests(1);
    }

    @Test
    void testFiltering() {
        assertThatTest(AbortedTests.class).filtering(
                p -> p.getDisplayName().equalsIgnoreCase("testHasNoAbortedTestsHadNoAbortedTests()")
        ).hasSuccessfulTests(1).hasAbortedTests(0).hasFailedTests(0);
    }

    // Aborted Tests
    @Nested
    class AbortedTests {

        @Test
        void testHasNoAbortedTestsHadNoAbortedTests() {
            assertThatTest(SampleClasses.HasNoAbortedTests.class).hasNoAbortedTests();
        }

        @Test
        void testHasNoAbortedTestsHadAbortedTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoAbortedTests.class).hasAbortedTests()).hasMessageContaining("Number of 'ABORTED' tests did not match");
        }

        @Test
        void testHasAbortedTestHadAbortedTests() {
            assertThatTest(SampleClasses.HasAbortedTests.class).hasAbortedTests();
        }

        @Test
        void testHasAbortedTestHadNoAbortedTest() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasAbortedTests.class).hasNoAbortedTests()).hasMessageContaining("Number of 'ABORTED' tests did not match");
        }

        @Test
        void testHasAbortedTestsNrHadAbortedTests() {
            assertThatTest(SampleClasses.HasAbortedTests.class).hasAbortedTests(1);
        }

        @Test
        void testHasAbortedTestNrHadDifferentNrOfAbortedTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoAbortedTests.class).hasAbortedTests(2)).hasMessageContaining("Number of 'ABORTED' tests did not match");
        }

        @Test
        void testHasAbortedTestsInBeforeAll() {
            assertThatTest(SampleClasses.AbortedInBeforeAll.class).hasAbortedTests(2);
        }

        @Test
        void testHasAbortedTestsInBeforeEach() {
            assertThatTest(SampleClasses.AbortedInBeforeEach.class).hasAbortedTests(2);
        }
    }

    // Failed
    @Nested
    class FailedTests {

        @Test
        void testHasNoFailedTestsHadNoFailedTests() {
            assertThatTest(SampleClasses.HasNoFailedTests.class).hasNoFailedTests();
        }

        @Test
        void testHasNoFailedTestsHadFailedTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoFailedTests.class).hasFailedTests()).hasMessageContaining("Number of 'FAILED' tests did not match");
        }

        @Test
        void testHasFailedTestHadFailedTests() {
            assertThatTest(SampleClasses.HasFailedTests.class).hasFailedTests();
        }

        @Test
        void testHasFailedTestHadNoFailedTest() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasFailedTests.class).hasNoFailedTests()).hasMessageContaining("Number of 'FAILED' tests did not match");
        }

        @Test
        void testHasFailedTestsNrHadFailedTests() {
            assertThatTest(SampleClasses.HasFailedTests.class).hasFailedTests(1);
        }

        @Test
        void testHasFailedTestNrHadDifferentNrOfFailedTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoFailedTests.class).hasFailedTests(2)).hasMessageContaining("Number of 'FAILED' tests did not match");
        }

        @Test
        void testHasFailedTestsInBeforeAll() {
            assertThatTest(SampleClasses.FailedInBeforeAll.class).hasAbortedTests(2);
        }

        @Test
        void testHasFailedTestsInBeforeEach() {
            assertThatTest(SampleClasses.FailedInBeforeEach.class).hasFailedTests(2);
        }
    }

    // Successful
    @Nested
    class SuccessfulTests {

        @Test
        void testHasNoSuccessfulTestsHadNoSuccessfulTests() {
            assertThatTest(SampleClasses.HasNoSuccessfulTests.class).hasNoSuccessfulTests();
        }

        @Test
        void testHasNoSuccessfulTestsHadSuccessfulTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoSuccessfulTests.class).hasSuccessfulTests()).hasMessageContaining("Number of 'SUCCESSFUL' tests did not match");
        }

        @Test
        void testHasSuccessfulTestHadSuccessfulTests() {
            assertThatTest(SampleClasses.HasSuccessfulTests.class).hasSuccessfulTests();
        }

        @Test
        void testHasSuccessfulTestHadNoSuccessfulTest() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasSuccessfulTests.class).hasNoSuccessfulTests()).hasMessageContaining("Number of 'SUCCESSFUL' tests did not match");
        }

        @Test
        void testHasSuccessfulTestsNrHadSuccessfulTests() {
            assertThatTest(SampleClasses.HasSuccessfulTests.class).hasSuccessfulTests(1);
        }

        @Test
        void testHasSuccessfulTestNrHadDifferentNrOfSuccessfulTests() {
            assertThatThrownBy(() -> assertThatTest(SampleClasses.HasNoSuccessfulTests.class).hasSuccessfulTests(2)).hasMessageContaining("Number of 'SUCCESSFUL' tests did not match");
        }
    }
}
