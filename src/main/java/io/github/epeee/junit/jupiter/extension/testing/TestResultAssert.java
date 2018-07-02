package io.github.epeee.junit.jupiter.extension.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TestResultAssert {

    private final Map<TestDescriptor, TestExecutionResult> map;

    public TestResultAssert(Map<TestDescriptor, TestExecutionResult> map) {
        this.map = map;
    }

    public TestResultAssert hasNoAbortedTests() {
        return hasTests(filter(TestExecutionResult.Status.ABORTED), countEqualZero());
    }

    public TestResultAssert hasAbortedTests(int nr) {
        return hasTests(filter(TestExecutionResult.Status.ABORTED), countEqual(nr));
    }

    public TestResultAssert hasAbortedTests() {
        return hasTests(filter(TestExecutionResult.Status.ABORTED), countGreaterZero());
    }

    public TestResultAssert hasNoFailedTests() {
        return hasTests(filter(TestExecutionResult.Status.FAILED), countEqualZero());
    }

    public TestResultAssert hasFailedTests(int nr) {
        return hasTests(filter(TestExecutionResult.Status.FAILED), countEqual(nr));
    }

    public TestResultAssert hasFailedTests() {
        return hasTests(filter(TestExecutionResult.Status.FAILED), countGreaterZero());
    }

    public TestResultAssert hasNoSuccessfulTests() {
        return hasTests(filter(TestExecutionResult.Status.SUCCESSFUL), countEqualZero());
    }

    public TestResultAssert hasSuccessfulTests(int nr) {
        return hasTests(filter(TestExecutionResult.Status.SUCCESSFUL), countEqual(nr));
    }

    public TestResultAssert hasSuccessfulTests() {
        return hasTests(filter(TestExecutionResult.Status.SUCCESSFUL), countGreaterZero());
    }

    public TestResultAssert hasTests(Predicate<TestExecutionResult> filter, Predicate<Stream<TestExecutionResult>> condition) {
        BiFunction<Stream<TestExecutionResult>, Predicate<TestExecutionResult>, Stream<TestExecutionResult>> function = (stream, testExecutionResultPredicate) -> stream.filter(testExecutionResultPredicate);
        Stream<TestExecutionResult> result = function.apply(map.values().stream(), filter);

        BiConsumer<Stream<TestExecutionResult>, Predicate<Stream<TestExecutionResult>>> consumer = (stream, predicate) -> {
            if (!predicate.test(stream)) {
                Assertions.fail("Expectation did not match for " + map);
            }
        };

        consumer.accept(result, condition);
        return this;
    }

    private Predicate<TestExecutionResult> filter(TestExecutionResult.Status status) {
        return result -> result.getStatus() == status;
    }

    private <T> Predicate<Stream<T>> countEqual(int nr) {
        return stream -> stream.count() == nr;
    }

    private <T> Predicate<Stream<T>> countEqualZero() {
        return countEqual(0);
    }

    private <T> Predicate<Stream<T>> countGreater(int nr) {
        return stream -> stream.count() > nr;
    }

    private <T> Predicate<Stream<T>> countGreaterZero() {
        return countGreater(0);
    }
}
