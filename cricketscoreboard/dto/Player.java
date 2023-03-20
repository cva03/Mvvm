package mvvmconsole.cricketscoreboard.dto;

public class Player {
    private static int count=0;
    private int playerId;
    private String name;
    private int totalruns;

    public Player(String name) {
        this.name=name;
        this.playerId=++count;
    }

    public Player() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public int getTotalruns() {
        return totalruns;
    }

    public void addToTotal(int score){
        this.totalruns+=score;
    }

    public void print() {
        System.out.printf("%-15d%-25s%-15d\n",playerId,name,totalruns);
    }

}
