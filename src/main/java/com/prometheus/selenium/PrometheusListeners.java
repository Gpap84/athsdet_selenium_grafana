package com.prometheus.selenium;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class PrometheusListeners implements ITestListener {

    List<String> labelKeys = Arrays.asList("author");
    List<String> labelValues = Arrays.asList("gpapadakis");

    PushGateway client = new PushGateway("localhost:9091");
    CollectorRegistry registry = CollectorRegistry.defaultRegistry;

    String jobName = "selenium";
    String metric = "TestPassed";
    String help = "metric_help";

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Gauge counter = Gauge.build()
                .name(metric)
                .help(help)
                .labelNames(labelKeys.toArray(new String[0]))
                .register(registry);
        counter.labels(labelValues.toArray(new String[0])).inc();

        try {
            client.push(registry, jobName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

}
