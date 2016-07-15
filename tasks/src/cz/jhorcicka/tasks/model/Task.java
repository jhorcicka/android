package cz.jhorcicka.tasks.model;

import java.util.ArrayList;
import java.util.List;

import cz.jhorcicka.tasks.utils.DateConverter;

public class Task {
    private long id = 0;
    private String name = "";
    private String created = "";
    private List<Interval> intervals = new ArrayList<Interval>();

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public void startInterval() {
        endInterval();
        Interval newInterval = new Interval();
        intervals.add(newInterval);
    }

    public void endInterval() {
        if (intervals.size() <= 0) {
            return;
        }

        int lastIndex = intervals.size() - 1;
        Interval lastInterval = intervals.get(lastIndex);

        if (lastInterval.getStopped().equals("")) {
            lastInterval.setStopped(DateConverter.getNow());
        }
    }

    public boolean isRunning() {
        if (intervals.size() <= 0) {
            return false;
        }

        int lastIndex = intervals.size() - 1;
        Interval lastInterval = intervals.get(lastIndex);

        boolean started = !lastInterval.getStarted().equals("");
        boolean stopped = !lastInterval.getStopped().equals("");

        return started && !stopped;
    }

    private String getTimeString() {
        long miliseconds = 0;

        for (Interval interval : intervals) {
            miliseconds += interval.getLengthInMilis();
        }

        return DateConverter.getLengthString(miliseconds);
    }

    public long getLengthInMilis() {
        long timeInMilis = 0;

        for (Interval i : intervals) {
            timeInMilis += i.getLengthInMilis();
        }

        return timeInMilis;
    }

    public String toString() {
        String runningSign = isRunning() ? "*" : "";
        return String.format("%s%s (%s)", runningSign, name, getTimeString());
    }

    public String getInfo() {
        String info = toString();

        for (Interval i : getIntervals()) {
            info += "\n" + i.toString();
        }

        return info;
    }
}
