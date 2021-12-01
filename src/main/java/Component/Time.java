package Component;

public class Time {
    private long timeStart;
    private long timeEnd;
    private long timeStop;
    private boolean stop = false;
    public Time() {
        timeStart = System.currentTimeMillis();
    }

    public int countSecond() {
        timeEnd = System.currentTimeMillis();
        if (stop) {
            long x = timeEnd - timeStop;
            setTimeStart(x);
        }
        return (int) (timeEnd - timeStart) / 1000;
    }

    public int countMilliSecond() {
        if (stop) {
            long x = timeEnd - timeStop;
            setTimeStart(x);
        }
        timeEnd = System.currentTimeMillis();
        return (int) (timeEnd - timeStart);
    }

    public void reset() {
        timeStart = System.currentTimeMillis();
        stop = false;
    }

    public void stop() {
        timeStop = this.countMilliSecond();
        stop = true;
    }

    public boolean isStop() {
        return stop;
    }

    public void present() {
        stop = false;
    }

    public void setTimeStart(long x) {
        timeStart = x;
    }
}