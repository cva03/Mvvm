package mvvmconsole.cricketscoreboard.viewer;


import mvvmconsole.cricketscoreboard.dto.Match;
import mvvmconsole.cricketscoreboard.dto.Player;
import mvvmconsole.cricketscoreboard.dto.Team;
import mvvmconsole.cricketscoreboard.repository.Repository;

import java.util.List;

public class ViewerModelView {
    private ViewerView viewerView;
    private Repository data= Repository.getInstance();
    public ViewerModelView(ViewerView viewerView) {
        this.viewerView =viewerView;
    }

    public void getAllTeams() {
        List<Team> teams=data.getAllTeams();
        if(teams.isEmpty()){
            viewerView.noTeamsFound();
        }else{
            viewerView.showAllTeams(teams);
        }
    }

    public void getTeam(int teamId) {
        viewerView.showTeam(data.getTeam(teamId));
    }

    public void getAllMatches() {
        List<Match> matches=data.getAllMatches();
        if(matches.isEmpty()){
            viewerView.noMatchFound();
        }else{
            viewerView.showAllMatches(matches);
        }
    }

    public void getMatch(int matchId) {
        viewerView.showMatch(data.getMatch(matchId));
    }

    public void getAllPlayers() {
        List<Player> players=data.getAllPlayers();
        if(players.isEmpty()){
            viewerView.noPlayerFound();
        }else{
            viewerView.showPlayer(players);
        }
    }
}
