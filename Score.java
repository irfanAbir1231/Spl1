public class Score {
    private String game;
    private String difficulty;
    private String player;
    private int score;

    public Score(String game, String difficulty, String player, int score) {
        this.game = game;
        this.difficulty = difficulty;
        this.player = player;
        this.score = score;
    }

    public String getGame() {
        return game;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return game + "," + difficulty + "," + player + "," + score;
    }

    public static Score fromString(String str) {
        String[] parts = str.split(",");
        return new Score(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
    }
}