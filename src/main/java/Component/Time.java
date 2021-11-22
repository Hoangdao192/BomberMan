package Component;

public class Time {
    private long timeStart;
    private long timeEnd;
    public Time() {
        timeStart = System.currentTimeMillis();
    }
    public int countSecond() {
        timeEnd = System.currentTimeMillis();
        return (int) (timeEnd - timeStart) / 1000;
    }
    public int countMilliSecond() {
        timeEnd = System.currentTimeMillis();
        return (int) (timeEnd - timeStart);
    }
    public void reset() {
        timeStart = System.currentTimeMillis();
    }
}