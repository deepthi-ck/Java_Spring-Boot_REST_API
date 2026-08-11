package com.example.restapi.analytics;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AnalyticsService {
    private final List<MetricPoint> points = new CopyOnWriteArrayList<>();

    public MetricPoint record(String name, double value, String tags) {
        MetricPoint point = new MetricPoint(name, value, tags);
        points.add(point);
        return point;
    }

    public List<MetricPoint> find(String name) {
        String needle = name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
        return points.stream().filter(p -> p.getName().toLowerCase(Locale.ROOT).equals(needle)).toList();
    }

    public Map<String, Object> summarize(String name) {
        List<MetricPoint> matched = find(name);
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("count", matched.size());
        if (matched.isEmpty()) {
            body.put("avg", 0.0);
            body.put("min", 0.0);
            body.put("max", 0.0);
            return body;
        }
        DoubleSummaryStatistics stats = matched.stream().mapToDouble(MetricPoint::getValue).summaryStatistics();
        body.put("avg", stats.getAverage());
        body.put("min", stats.getMin());
        body.put("max", stats.getMax());
        body.put("sum", stats.getSum());
        return body;
    }

    public List<MetricPoint> latest(int limit) {
        List<MetricPoint> copy = new ArrayList<>(points);
        int safe = Math.max(0, Math.min(limit, copy.size()));
        int from = copy.size() - safe;
        return List.copyOf(copy.subList(from, copy.size()));
    }

    public int size() { return points.size(); }

    public void clear() { points.clear(); }
}
