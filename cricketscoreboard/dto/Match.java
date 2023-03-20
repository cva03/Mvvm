package mvvmconsole.cricketscoreboard.dto;

public class Match {
    private static int count=0;
    private int matchId;
    private Team team1;
    private Team team2;
    private int[] team1Score;
    private int[] team2Score;
    private int team1total;
    private int team2total;
    private int team1Wickets;
    private int team2Wickets;
    private String won;
    private String lose;
    private boolean draw;
    private int runDifference;
    private String manOfTheMatch;

    public Match() {
        count++;
    }

    public void printSpecificMatch() {
        System.out.println("\n\t\tMatch-Id : "+matchId+" | Match : "+printMatchName()+" | "+team1.getTeamName()+" : "+printTotal1()+" | "+team2.getTeamName()+" : "+printTotal2()+" | "+won+" won by "+runDifference+" runs.");
        System.out.printf("\n\t\t%-35s%-50s%-35s",team1.getTeamName()," ",team2.getTeamName());
        for(int i=0;i<team1.getPlayers().size();i++){
            System.out.printf("\n%-20s%-15d%-50s%-20s%-15d",team1.getPlayers().get(i).getName(),team1Score[i]," ",team2.getPlayers().get(i).getName(),team2Score[i]);
        }
        System.out.printf("\n\n%70s\n",("Man of the Match is : "+manOfTheMatch));
    }

    public void print(){
        System.out.printf("%-15d%-40s%-15s%-15s%-20s%-20s%-20s%-20s\n",matchId,printMatchName(),printTotal1(),printTotal2(),won,lose,printDraw(),manOfTheMatch);
    }

    private String printMatchName() {
        return team1.getTeamName()+" VS "+team2.getTeamName();
    }

    private String printTotal2() {
        return team2total+"/"+team1Wickets;
    }

    private String printTotal1() {
        return team1total+"/"+team2Wickets;
    }

    private String printDraw() {
        if(draw){
            return "Draw";
        }
        return "-";
    }

    public static int getCount() {
        return count;
    }

    public int getMatchId() {
        return matchId;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int[] getTeam1Score() {
        return team1Score;
    }

    public int[] getTeam2Score() {
        return team2Score;
    }

    public int getTeam1total() {
        return team1total;
    }

    public int getTeam2total() {
        return team2total;
    }

    public int getTeam1Wickets() {
        return team1Wickets;
    }

    public int getTeam2Wickets() {
        return team2Wickets;
    }

    public String getWon() {
        return won;
    }

    public String getLose() {
        return lose;
    }

    public int getRunDifference() {
        return runDifference;
    }

    public String getManOfTheMatch() {
        return manOfTheMatch;
    }

    public Match(Team team1, Team team2, int[] team1Score, int[] team2Score, int team1Wickets, int team2Wickets) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.team1Wickets = team1Wickets;
        this.team2Wickets = team2Wickets;
        this.matchId=++count;
        this.team1.addMatches();
        this.team2.addMatches();
        makeTeamTotal(team1Score,team2Score);
        if(team1total>team2total){
            this.won=team1.getTeamName();
            this.lose=team2.getTeamName();
            this.getTeam1().addWin();
            this.getTeam2().addLose();
            findManOfTheMatch(team1Score);
        }else if (team1total==team2total){
            this.draw=true;
            this.getTeam2().addDraws();
            this.getTeam1().addDraws();
            findManOfTheMatch(team1Score,team2Score);
        }else {
                this.won=team2.getTeamName();
                this.lose=team1.getTeamName();
                this.getTeam2().addWin();
                this.getTeam1().addLose();
                findManOfTheMatch(team2Score);
            }
        this.runDifference=Math.abs(team1total-team2total);
    }

    private void findManOfTheMatch(int[] team1Score, int[] team2Score) {
        int max=0;
        for(int i=0;i<team1Score.length;i++){
            if(max<team1Score[i]){
                max=team1Score[i];
                this.manOfTheMatch=team1.getPlayers().get(i).getName();
            }
            if(max<team2Score[i]){
                max=team2Score[i];
                this.manOfTheMatch=team2.getPlayers().get(i).getName();
            }
        }
    }

    private void findManOfTheMatch(int[] teamScore) {
        int max=0;
        for(int i=0;i<teamScore.length;i++){
            if(max<teamScore[i]){
                max=teamScore[i];
                this.manOfTheMatch=team1.getPlayers().get(i).getName();
            }
        }
    }

    private void makeTeamTotal(int[] team1Score, int[] team2Score) {
        int max=0;
        for(int i=0;i<team1Score.length;i++){
            if(max<team1Score[i]){
                max=team1Score[i];
                this.manOfTheMatch=team1.getPlayers().get(i).getName();
            }
            if(max<team2Score[i]){
                max=team2Score[i];
                this.manOfTheMatch=team1.getPlayers().get(i).getName();
            }
            this.team1total+=team1Score[i];
            this.team2total+=team2Score[i];
        }
    }


}
