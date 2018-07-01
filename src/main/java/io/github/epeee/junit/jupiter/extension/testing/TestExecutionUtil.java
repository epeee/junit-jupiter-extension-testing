package io.github.epeee.junit.jupiter.extension.testing;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.LauncherDiscoveryRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

class TestExecutionUtil {

    static Map<TestDescriptor, TestExecutionResult> runTests(Class<?> clazz) {
        return runTests(selectClass(clazz));
    }

    private static Map<TestDescriptor, TestExecutionResult> runTests(DiscoverySelector... selectors) {
        return runTests(request().selectors(selectors).build());
    }

    private static Map<TestDescriptor, TestExecutionResult> runTests(LauncherDiscoveryRequest request) {
        JupiterTestEngine engine = new JupiterTestEngine();
        TestDescriptor testDescriptor = engine.discover(request, UniqueId.forEngine(engine.getId()));

        final Map<TestDescriptor, TestExecutionResult> map = new HashMap<>();
        EngineExecutionListener listener = new EngineExecutionListener() {
            @Override
            public void dynamicTestRegistered(TestDescriptor testDescriptor) {

            }

            @Override
            public void executionSkipped(TestDescriptor testDescriptor, String reason) {

            }

            @Override
            public void executionStarted(TestDescriptor testDescriptor) {

            }

            @Override
            public void executionFinished(TestDescriptor testDescriptor, TestExecutionResult testExecutionResult) {
                if (testDescriptor.isTest()) {
                    map.put(testDescriptor, testExecutionResult);
                }
            }

            @Override
            public void reportingEntryPublished(TestDescriptor testDescriptor, ReportEntry entry) {

            }
        };
        engine.execute(new ExecutionRequest(testDescriptor, listener, request.getConfigurationParameters()));
        return map;
    }
}
