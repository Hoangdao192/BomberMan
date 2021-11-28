package Component;

public class Time {
    private long timeStart;
    private long timeEnd;
    private long timeStop;
    private boolean Stop = false;
    public Time() {
        timeStart = System.currentTimeMillis();
    }

    public int countSecond() {
        timeEnd = System.currentTimeMillis();
        if (Stop) {
            long x = timeEnd - timeStop;
            setTimeStart(x);
        }
        return (int) (timeEnd - timeStart) / 1000;
    }

    public void stop() {
        timeStop = this.countMilliSecond();
        Stop = true;
    }

    public void present() {
        Stop = false;
    }

    public void setTimeStart(long x) {
        timeStart = x;
    }

    public int countMilliSecond() {
        timeEnd = System.currentTimeMillis();
        if (Stop) {
            long x = timeEnd - timeStop;
            setTimeStart(x);
        }
        return (int) (timeEnd - timeStart);
    }

    public void reset() {
        timeStart = System.currentTimeMillis();
        Stop = false;
    }
}