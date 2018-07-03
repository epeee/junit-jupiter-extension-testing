package io.github.epeee.junit.jupiter.extension.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SampleClasses {

    @Tag("sample")
    static class MyJupiterTest {

        @Test
        void test() {
            Assertions.assertThat(true).isTrue();
        }

        @Test
        void failingTest() {
            throw new IllegalStateException();
        }

        @Test
        void skippedTest() {
            Assumptions.assumeTrue(false);
        }
    }

    @Tag("sample")
    static class HasAbortedTests {

        @Test
        void skippedTest() {
            Assumptions.assumeTrue(false);
        }
    }

    @Tag("sample")
    static class HasNoAbortedTests {

        @Test
        void failingTest() {
            throw new IllegalStateException();
        }
    }

    @Tag("sample")
    static class HasFailedTests {

        @Test
        void failingTest() {
            throw new IllegalStateException();
        }
    }

    @Tag("sample")
    static class HasNoFailedTests {

        @Test
        void successfulTest() {
            Assertions.assertThat(true).isTrue();
        }
    }

    @Tag("sample")
    static class HasSuccessfulTests {

        @Test
        void successfulTest() {
            Assertions.assertThat(true).isTrue();
        }
    }

    @Tag("sample")
    static class HasNoSuccessfulTests {

        @Test
        void failingTest() {
            throw new IllegalStateException();
        }
    }
}
