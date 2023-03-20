package mvvmconsole.cricketscoreboard.staff;


import mvvmconsole.cricketscoreboard.dto.Match;
import mvvmconsole.cricketscoreboard.dto.Team;
import mvvmconsole.cricketscoreboard.repository.Repository;

import java.util.HashMap;
import java.util.List;

public class StaffModelView {
    private StaffView staffView;
    private Repository data= Repository.getInstance();
    public StaffModelView(StaffView staffView) {
        this.staffView=staffView;
    }


    public void addTeam(Team team) {
        data.addTeam(team);
        staffView.teamCreationSuccessfull(team);
    }

    public void getAllTeams() {
        List<Team> teams=data.getAllTeams();
        if(teams.isEmpty()){
            staffView.noTeamsFound();
        }else{
            staffView.teamsFound(teams);
        }
    }

    public void getTeamsForMatch() {
        HashMap<Integer, Team> teams=data.getAllTeamsForMatch();
        if(teams.size()<=1){
            staffView.notEnoughtTeams();
        }else{
            staffView.teamsFoundForMatch(teams);
        }
    }

    public void addMatch(Match match) {
        data.addMatch(match);
        staffView.matchAddSuccess(match);
    }

    public void getAllMatches() {
        List<Match> matches=data.getAllMatches();
        if(matches.isEmpty()){
            staffView.noMatchFound();
        }else{
            staffView.showAllMatches(matches);
        }
    }

    public void getTeam(int teamId) {
        staffView.showTeam(data.getTeam(teamId));
    }

    public void getMatch(int matchId) {
        staffView.showMatch(data.getMatch(matchId));
    }


}
