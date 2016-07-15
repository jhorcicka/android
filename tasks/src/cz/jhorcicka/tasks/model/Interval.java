package cz.jhorcicka.tasks.model;

import java.util.Date;

import cz.jhorcicka.tasks.utils.DateConverter;

public class Interval {
    private long id = 0;
    private long fkTask = 0;
    private String started = "";
    private String stopped = "";

    public Interval() {
        started = DateConverter.getNow();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFkTask() {
        return fkTask;
    }

    public void setFkTask(long fkTask) {
        this.fkTask = fkTask;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getStopped() {
        return stopped;
    }

    public void setStopped(String stopped) {
        this.stopped = stopped;
    }

    public long getLengthInMilis() {
        long start = DateConverter.stringToDate(started).getTime();
        long end = new Date().getTime();

        if (!stopped.equals("")) {
            end = DateConverter.stringToDate(stopped).getTime();
        }

        return end - start;
    }

    public String toString() {
        return String.format("<%s, %s>", started, stopped);
    }
}
