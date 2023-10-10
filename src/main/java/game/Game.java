package game;

public class Game {
    private int score = 0;
    private int level = 1;
    private int limitTimePlay;
    private boolean isPlaying;
    private long startTime;
    public Game() {

    }
    public void startGame () {
        startTime = System.currentTimeMillis();
        isPlaying = true;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore() {
        this.score += 1;
    }

    public void endGame() {
        if( calculateTimeplay() < limitTimePlay) {
            isPlaying = false;
        }
    }

    /**
     * Hàm tính thời gian còn lại. Hết thời gian thì nghỉ game.
     */
    public long calculateTimeplay() {
        long playTime = 0;

        while (isPlaying) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            // Chuyển đổi thời gian từ mili-giây sang giây
            playTime = elapsedTime / 1000;

            try {
                Thread.sleep(1000); // Đợi 1 giây trước khi cập nhật lại
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return playTime;
    }
}