package Component;

public class Score {
    int score = 0;
    public Score() {
        score = 0;
    }
    public void addScore(int score) {
        this.score += score;
    }
    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
    }
}