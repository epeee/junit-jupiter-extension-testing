package io.github.epeee.junit.jupiter.extension.testing;

import com.google.errorprone.annotations.CheckReturnValue;

import static io.github.epeee.junit.jupiter.extension.testing.TestExecutionUtil.runTests;

/**
 * Entry point for assertion methods.
 */
public class ExtensionTesting {

    /**
     * Create assertion for test results produced by executing the given class.
     *
     * @param clazz the test class to execute.
     * @return the created assertion object.
     */
    @CheckReturnValue
    public static TestResultAssert assertThatTest(Class<?> clazz) {
        return new TestResultAssert(runTests(clazz));
    }

}
