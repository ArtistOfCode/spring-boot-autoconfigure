package com.codeartist.component.autoconfigure.sleuth;

import brave.ScopedSpan;
import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.Propagation;
import com.codeartist.component.core.support.trace.Tracers;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Sleuth实现
 * <p>
 * TODO 现在实现不够优雅，后续继续优化
 *
 * @author AiJiangnan
 * @date 2023/8/4
 */
public class SleuthTracers implements Tracers {

    private static final Propagation.Setter<Map<String, String>, String> SETTER = Map::put;
    private static final Propagation.Getter<Map<String, String>, String> GETTER = Map::get;

    private final Tracer tracer;
    private final Tracing tracing;

    public SleuthTracers(Tracer tracer, Tracing tracing) {
        this.tracer = tracer;
        this.tracing = tracing;
    }

    @Override
    public <T> T startSpan(String name, Map<String, String> tags, Supplier<T> supplier, Map<String, String> extractor) {
        Span span = tracer.nextSpan(tracing.propagation().extractor(GETTER).extract(extractor)).name(name);
        tags.forEach(span::tag);
        try (Tracer.SpanInScope ignored = tracer.withSpanInScope(span.start())) {
            return supplier.get();
        } catch (Exception e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public <T> T startScopedSpan(String name, Map<String, String> tags, Supplier<T> supplier) {
        ScopedSpan span = tracer.startScopedSpan(name);
        tags.forEach(span::tag);
        try {
            return supplier.get();
        } catch (Exception e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public <T> T startScopedSpan(String name, Map<String, String> tags, Supplier<T> supplier, Map<String, String> injector) {
        ScopedSpan span = tracer.startScopedSpan(name);
        tags.forEach(span::tag);
        try {
            tracing.propagation().injector(SETTER).inject(span.context(), injector);
            return supplier.get();
        } catch (Exception e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }
}
