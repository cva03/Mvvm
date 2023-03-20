package mvvmconsole.cricketscoreboard.dto;

import java.util.List;

public class Team {
    private static int count=0;
    private String teamName;
    private int teamId;
    private List<Player> players;
    private int totalMatches;
    private int wins;
    private int loses;
    private int draws;

    public Team() {
        count++;
    }

    public void print() {
        System.out.printf("%-15d%-20s%-15d%-15d%-15d%-15d\n",teamId,teamName,totalMatches,wins,loses,draws);
    }

    public Team(String teamName, List<Player> players) {
        this.teamName = teamName;
        this.players = players;
        this.teamId=++count;
    }

    public void addWin(){
        this.wins++;
    }
    public void addLose(){
        this.loses++;
    }
    public void addDraws(){
        this.draws++;
    }

    public static int getCount() {
        return count;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getDraws() {
        return draws;
    }

    public void addMatches(){
        this.totalMatches++;
    }

    public void addScores(int[] teamScore) {
        for(int i=0;i<players.size();i++){
            players.get(i).addToTotal(teamScore[i]);
        }
    }

    public void printSpecific(){
        System.out.println("\nTeam-Id : "+teamId+" | Team-name : "+teamName+" | Total-Matches : "+totalMatches+" | Wins : "+wins+" | Loses : "+loses+" | Draws : "+draws);
        System.out.printf("%-15s%-25s%-15s\n","Player-Id","Name","Total-Runs");
        for(Player player:players){
            player.print();
        }
    }



}
