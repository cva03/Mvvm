package mvvmconsole.cricketscoreboard.staff;

import mvvmconsole.cricketscoreboard.dto.Match;
import mvvmconsole.cricketscoreboard.dto.Player;
import mvvmconsole.cricketscoreboard.dto.Staff;
import mvvmconsole.cricketscoreboard.dto.Team;
import mvvmconsole.cricketscoreboard.login.LoginView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StaffView  {
    private StaffModelView staffModelView;
    private Scanner sc=new Scanner(System.in);
    private Staff staff;
    public StaffView(){
        this.staffModelView=new StaffModelView(this);
    }

    public void init(Staff staff) {
        this.staff=staff;
        start();
    }

    private void start(){
        System.out.printf("\n%50s\n","<---- Staff page Staff : "+staff.getUserName()+" ---->");
        System.out.println("\n1. Add new team");
        System.out.println("2. Add Match");
        System.out.println("3. Show all teams");
        System.out.println("4. Show all matches");
        System.out.println("0. Exit");
        int choice;
        while(true) {
            try {
                System.out.println("Enter your choice");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        addNewTeam();
                        break;
                    case 2:
                        staffModelView.getTeamsForMatch();
                        break;
                    case 3:
                        staffModelView.getAllTeams();
                        break;
                    case 4:
                        staffModelView.getAllMatches();
                    case 0:
                        LoginView lv=new LoginView();
                        lv.init();
                    default:
                        throw new NumberFormatException();

                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    private void addNewTeam() {
        System.out.printf("\n%50s\n","<----- Add new team ----->");
        System.out.println("Enter the team name : ");
        String teamName=sc.nextLine();
        List<Player> players=new ArrayList<>();
        for(int i=0;i<11;i++) {
            System.out.println("Enter the player "+(i+1)+"'s Name");
            String name = sc.nextLine();
            players.add(new Player(name));
        }
        Team team=new Team(teamName,players);
        staffModelView.addTeam(team);
    }


    public void teamCreationSuccessfull(Team team) {
        System.out.println("Team created successfully and the team id is "+team.getTeamId());
        pressAnyKey();
    }

    public void noTeamsFound() {
        System.out.println("No teams added yet.");
        pressAnyKey();
    }

    public void teamsFound(List<Team> teams) {
        System.out.println("<----- Team list ----->");
        System.out.println(teams.size()+" teams found:");
        System.out.printf("\n%-15s%-20s%-15s%-15s%-15s%-15s\n","TeamId","Team-Name","Total-Matches","Wins","Loses","Draws");
        for(Team team:teams){
            team.print();
        }
        while (true){
            try {
                System.out.println("Enter the TeamId to show more information about the team or 0 to Exit");
                int teamId=sc.nextInt();
                sc.nextLine();
                if(teamId==0){
                    System.out.println("Returning to main menu!");
                    pressAnyKey();
                }
                if(teamId>teams.size()){
                    throw new NumberFormatException();
                }
                staffModelView.getTeam(teamId);
            }catch (NumberFormatException e){
                System.out.println("Enter valid teamId");
            }
        }
    }

    public void notEnoughtTeams() {
        System.out.println("Not Enough teams present for match making!");
        pressAnyKey();
    }

    public void teamsFoundForMatch(HashMap<Integer, Team> teams) {
        System.out.printf("\n%50s\n","<---- Add match ---->");
        while(true){
            try{
                System.out.println("Enter the team 1's team id");
                int team1=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the team 2's team id");
                int team2=sc.nextInt();
                sc.nextLine();
                if(!teams.containsKey(team1) || !teams.containsKey(team2)){
                    System.out.println("One of the teams not found Re-enter id's!");
                    throw new NumberFormatException();
                }
                getMatchScores(teams.get(team1),teams.get(team2));
            }catch (NumberFormatException e){
                System.out.println("Invalid input!");
            }
        }

    }

    public void matchAddSuccess(Match match) {
        System.out.println("Match details added successfully and the match id is "+match.getMatchId());
        pressAnyKey();
    }

    public void noMatchFound() {
        System.out.println("No matches started yet");
        pressAnyKey();
    }

    public void showAllMatches(List<Match> matches) {
        System.out.println("<----- Match list ----->");
        System.out.println(matches.size()+" matches found:");
        System.out.printf("\n%-15s%-40s%-15s%-15s%-20s%-20s%-20s%-20s\n","Match-Id","Match","Team1-Total","Team2-Total","Won","Lose","Draw","Man-Of-The-Match");
        for(Match match:matches){
            match.print();
        }
        while (true){
            try {
                System.out.println("Enter the Match-Id to show more information about the Match or 0 to Exit");
                int matchId=sc.nextInt();
                sc.nextLine();
                if(matchId==0){
                    System.out.println("Returning to main menu!");
                    pressAnyKey();
                }
                if(matchId>matches.size()){
                    throw new NumberFormatException();
                }
                staffModelView.getMatch(matchId);
            }catch (NumberFormatException e){
                System.out.println("Enter valid teamId");
            }
        }
    }

    public void showTeam(Team team) {
        team.printSpecific();
        pressAnyKey();
    }

    public void showMatch(Match match) {
        match.printSpecificMatch();
        pressAnyKey();
    }

    private void getMatchScores(Team team1, Team team2) {
        System.out.println("Two teams picked :");
        System.out.println(team1.getTeamName()+" VS "+team2.getTeamName());
        int[] team1Score=new int[11];
        int[] team2Score=new int[11];
        while (true){
            try {
                System.out.println("Enter the number of wickets taken by team 1 : "+team1.getTeamName());
                int team1Wickets=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the number of wickets taken by team 2 : "+team2.getTeamName());
                int team2Wicktes=sc.nextInt();
                sc.nextLine();
                if(team1Wickets>10 || team2Wicktes>10 || team1Wickets<0 || team2Wicktes<0 ){
                    throw new NumberFormatException();
                }
                getPlayerScores(team1Score,team1,team2Wicktes);
                getPlayerScores(team2Score,team2,team1Wickets);
                Match match=new Match(team1,team2,team1Score,team2Score,team1Wickets,team2Wicktes);
                staffModelView.addMatch(match);
            }catch (NumberFormatException e){
                System.out.println("Invalid input! Re-enter !");
            }
        }
    }

    private void getPlayerScores(int[] team1Score, Team team1, int teamWickets) {
        System.out.println(team1.getTeamName()+"s Scores");
        for(int i=0;i<team1Score.length;i++){
            if(i-2==teamWickets){
                break;
            }
            System.out.println("Enter "+team1.getPlayers().get(i).getName()+"'s Score :");
            team1Score[i]=sc.nextInt();
            sc.nextLine();
            if(team1Score[i]<0){
                System.out.println("Invalid score enter score greater than 0");
                --i;
            }
        }
    }

    private void pressAnyKey(){
        System.out.println("Press any key to go to Main menu");
        start();
    }

}
