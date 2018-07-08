package io.github.epeee.junit.jupiter.extension.testing;

import com.google.errorprone.annotations.CheckReturnValue;

import static io.github.epeee.junit.jupiter.extension.testing.TestExecutionUtil.runTests;

public class ExtensionTesting {

    @CheckReturnValue
    public static TestResultAssert assertThatTest(Class<?> clazz) {
        return new TestResultAssert(runTests(clazz));
    }

}
