package io.github.epeee.junit.jupiter.extension.testing;

import com.google.errorprone.annotations.CheckReturnValue;
import org.assertj.core.api.Condition;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestResultAssert {

    private final Map<TestDescriptor, TestExecutionResult> map;

    public TestResultAssert(Map<TestDescriptor, TestExecutionResult> map) {
        this.map = map;
    }

    public TestResultAssert hasNoAbortedTests() {
        return hasTests(TestExecutionResult.Status.ABORTED, countEqualZero());
    }

    public TestResultAssert hasAbortedTests(int nr) {
        return hasTests(TestExecutionResult.Status.ABORTED, countEqual(nr));
    }

    public TestResultAssert hasAbortedTests() {
        return hasTests(TestExecutionResult.Status.ABORTED, countGreaterZero());
    }

    public TestResultAssert hasNoFailedTests() {
        return hasTests(TestExecutionResult.Status.FAILED, countEqualZero());
    }

    public TestResultAssert hasFailedTests(int nr) {
        return hasTests(TestExecutionResult.Status.FAILED, countEqual(nr));
    }

    public TestResultAssert hasFailedTests() {
        return hasTests(TestExecutionResult.Status.FAILED, countGreaterZero());
    }

    public TestResultAssert hasNoSuccessfulTests() {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countEqualZero());
    }

    public TestResultAssert hasSuccessfulTests(int nr) {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countEqual(nr));
    }

    public TestResultAssert hasSuccessfulTests() {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countGreaterZero());
    }

    @CheckReturnValue
    public TestResultAssert filtering(Predicate<TestDescriptor> predicate) {
        return new TestResultAssert(map.entrySet().stream().filter(a ->
                predicate.test(a.getKey())
        ).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
    }

    public TestResultAssert hasTests(TestExecutionResult.Status status, BiConsumer<Stream<TestExecutionResult>, TestExecutionResult.Status> condition) {
        Predicate<TestExecutionResult> filter = filter(status);

        BiFunction<Stream<TestExecutionResult>, Predicate<TestExecutionResult>, Stream<TestExecutionResult>> function = (stream, testExecutionResultPredicate) -> stream.filter(testExecutionResultPredicate);
        Stream<TestExecutionResult> result = function.apply(map.values().stream(), filter);

        BiConsumer<Stream<TestExecutionResult>, BiConsumer<Stream<TestExecutionResult>, TestExecutionResult.Status>> consumer = (stream, consumer1) -> consumer1.accept(stream, status);

        consumer.accept(result, condition);
        return this;
    }

    private Predicate<TestExecutionResult> filter(TestExecutionResult.Status status) {
        return result -> result.getStatus() == status;
    }

    private <T> BiConsumer<Stream<T>, TestExecutionResult.Status> countEqual(int nr) {
        return (stream, status) -> assertThat(stream).as(getDescription(status)).hasSize(nr);
    }

    private <T> BiConsumer<Stream<T>, TestExecutionResult.Status> countEqualZero() {
        return countEqual(0);
    }

    private <T> BiConsumer<Stream<T>, TestExecutionResult.Status> countGreater(int nr) {
        return (stream, status) -> assertThat(stream).as(getDescription(status)).has(new Condition<>((Predicate<List<? extends T>>) ts -> ts.size() > 0, "count greater " + nr));
    }

    private <T> BiConsumer<Stream<T>, TestExecutionResult.Status> countGreaterZero() {
        return countGreater(0);
    }

    private <T> String getDescription(T t) {
        return "Number of '" + t + "' tests did not match:";
    }
}
