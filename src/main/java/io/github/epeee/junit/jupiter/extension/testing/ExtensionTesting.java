package io.github.epeee.junit.jupiter.extension.testing;

import static io.github.epeee.junit.jupiter.extension.testing.TestExecutionUtil.runTests;

public class ExtensionTesting {

    public static TestResultAssert assertThatTest(Class<?> clazz) {
        return new TestResultAssert(runTests(clazz));
    }

}