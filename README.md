# Order Summary Service - OpenTelemetry Tutorial

A Kotlin and Spring Boot application demonstrating next-level observability with OpenTelemetry. This project shows how to instrument a scheduled background job to solve log correlation problems in concurrent execution environments.

## Problem Solved

When multiple job executions run concurrently, logs from different runs get interleaved and become impossible to correlate. You can't tell which log belongs to which execution, making debugging difficult.

**Solution**: OpenTelemetry Java Agent automatically injects `trace_id` and `span_id` into every log line. Filter by `trace_id` to isolate all logs from a single execution, even when dozens of executions overlap.

## Architecture

The application runs a scheduled job every 5 seconds that:
1. Fetches orders created in the last 24 hours from an H2 database
2. Processes each order (with simulated work and occasional failures)
3. Writes a summary back to the database

With OpenTelemetry instrumentation, every log line includes trace context, making it easy to correlate logs by execution.

## Prerequisites

- JDK 17 or later
- Git CLI

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/kimanikevin254/jetbrains-otel-order-summary.git
cd jetbrains-otel-order-summary
```

### 2. Download the OpenTelemetry Java Agent
```bash
mkdir -p agents
curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar \
     -o agents/opentelemetry-javaagent.jar
```

### 3. Run the Application
```bash
./gradlew bootRun
```

### 4. Observe the Logs

You'll see logs with `trace_id` and `span_id`:
```
10:14:29.948 [task-1] [trace_id=c872173a56237202be5920c31f986ba5 span_id=837c5c79d1fe059c] INFO - Starting order summary job...
10:14:34.937 [task-2] [trace_id=634ca66e0f6fae02fddd9a604b209aed span_id=1f71ea6d044cb67b] INFO - Starting order summary job...
10:14:36.077 [task-1] [trace_id=c872173a56237202be5920c31f986ba5 span_id=837c5c79d1fe059c] INFO - Processing order 11...
```

Notice how all logs from the first execution share `trace_id=c872173a...`, while the second execution has a different `trace_id=634ca66e...`. You can now filter by trace ID to see exactly what happened in a single run.

## Project Structure
```
src/main/kotlin/com/example/order_summary/
├── OrderSummaryServiceApplication.kt     # Main application class
├── entity/
│   ├── Order.kt                          # Order entity
│   └── OrderSummary.kt                   # Summary result entity
├── repository/
│   ├── OrderRepository.kt                # Order data access
│   └── OrderSummaryRepository.kt         # Summary data access
├── service/
│   └── OrderSummaryJob.kt                # Scheduled job (the core of the demo)
└── config/
    └── DataInitializer.kt                # Seeds sample orders on startup
```

## How It Works

The OpenTelemetry Java Agent is configured in `build.gradle.kts`:
```kotlin
tasks.bootRun {
    jvmArgs = listOf(
        "-javaagent:${projectDir}/agents/opentelemetry-javaagent.jar",
        "-Dotel.service.name=order-summary-service",
        "-Dotel.traces.exporter=logging",
        "-Dotel.metrics.exporter=none",
        "-Dotel.logs.exporter=none"
    )
}
```

The agent automatically:
- Instruments `@Async` methods to create spans
- Injects `trace_id` and `span_id` into the logging MDC (Mapped Diagnostic Context)
- Propagates trace context across threads
