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

    /**
     * Verifies that the test execution does not contain aborted tests.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result has aborted tests.
     */
    public TestResultAssert hasNoAbortedTests() {
        return hasTests(TestExecutionResult.Status.ABORTED, countEqualZero());
    }

    /**
     * Verifies that the test execution has a certain number of aborted tests.
     *
     * @param nr the number of aborted tests to assert on.
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have the given number of aborted tests.
     */
    public TestResultAssert hasAbortedTests(int nr) {
        return hasTests(TestExecutionResult.Status.ABORTED, countEqual(nr));
    }

    /**
     * Verifies that the test execution contains at least one aborted test.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have aborted tests.
     */
    public TestResultAssert hasAbortedTests() {
        return hasTests(TestExecutionResult.Status.ABORTED, countGreaterZero());
    }

    /**
     * Verifies that the test execution does not contain failed tests.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result has failed tests.
     */
    public TestResultAssert hasNoFailedTests() {
        return hasTests(TestExecutionResult.Status.FAILED, countEqualZero());
    }

    /**
     * Verifies that the test execution has a certain number of failed tests.
     *
     * @param nr the number of failed tests to assert on.
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have the given number of failed tests.
     */
    public TestResultAssert hasFailedTests(int nr) {
        return hasTests(TestExecutionResult.Status.FAILED, countEqual(nr));
    }

    /**
     * Verifies that the test execution contains at least one failed test.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have failed tests.
     */
    public TestResultAssert hasFailedTests() {
        return hasTests(TestExecutionResult.Status.FAILED, countGreaterZero());
    }

    /**
     * Verifies that the test execution does not contain successful tests.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result has successful tests.
     */
    public TestResultAssert hasNoSuccessfulTests() {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countEqualZero());
    }

    /**
     * Verifies that the test execution has a certain number of successful tests.
     *
     * @param nr the number of successful tests to assert on.
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have the given number of successful tests.
     */
    public TestResultAssert hasSuccessfulTests(int nr) {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countEqual(nr));
    }

    /**
     * Verifies that the test execution contains at least one successful test.
     *
     * @return this assertion object.
     * @throws AssertionError if the actual test result does not have successful tests.
     */
    public TestResultAssert hasSuccessfulTests() {
        return hasTests(TestExecutionResult.Status.SUCCESSFUL, countGreaterZero());
    }

    /**
     * Reduces the test results to assert on based on the given {@link Predicate}.
     *
     * @param predicate the {@link Predicate} used for filtering.
     * @return this assertion object.
     */
    @CheckReturnValue
    public TestResultAssert filtering(Predicate<TestDescriptor> predicate) {
        return new TestResultAssert(map.entrySet().stream().filter(a ->
                predicate.test(a.getKey())
        ).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
    }

    /**
     * Verifies that the test execution contains tests.
     *
     * @param status the {@link TestExecutionResult.Status} used for filtering.
     * @param condition the {@link BiConsumer} which is used to check the actual test result.
     * @return this assertion object.
     */
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
