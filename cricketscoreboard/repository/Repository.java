package mvvmconsole.cricketscoreboard.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvvmconsole.cricketscoreboard.dto.Match;
import mvvmconsole.cricketscoreboard.dto.Player;
import mvvmconsole.cricketscoreboard.dto.Staff;
import mvvmconsole.cricketscoreboard.dto.Team;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {
    private static Repository data;
    private HashMap<String, Staff> staffDetails=new HashMap<>();
    private HashMap<Integer, Team> teamDetails=new HashMap<>();
    private HashMap<Integer, Match> matchDetails=new HashMap<>();

    private Repository(){
        readFile();
    }

    private void readFile() {
        ObjectMapper obj=new ObjectMapper();
        try {
            staffDetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com/console/cricketscoreboard/repository/staffdetails.json"), new TypeReference<HashMap<String, Staff>>() {});
            teamDetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com/console/cricketscoreboard/repository/teamdetails.json"), new TypeReference<HashMap<Integer, Team>>() {});
            matchDetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com/console/cricketscoreboard/repository/matchdetails.json"),new TypeReference<HashMap<Integer, Match>>(){});
        } catch (IOException e) {
            System.out.println("read error");
        }
    }

    private void writeFile()  {
    ObjectMapper obj=new ObjectMapper();
    try{
        obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/cricketscoreboard/repository/staffdetails.json"),staffDetails);
        obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/cricketscoreboard/repository/teamdetails.json"),teamDetails);
        obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/cricketscoreboard/repository/matchdetails.json"),matchDetails);
    }catch (IOException e){
        System.out.println("problem in writing file");
        }
    }

    public static Repository getInstance(){
        if(data==null){
            data=new Repository();
        }
        return data;
    }


    public Staff checkAdmin(String userName, String password) {
            return staffDetails.get(userName);
    }

    public boolean addStaff(Staff staff) {
        if(staffDetails.containsKey(staff.getUserName())){
            return false;
        }
        staffDetails.put(staff.getUserName(),staff);
        return true;
    }

    public void addTeam(Team team) {
        teamDetails.put(team.getTeamId(),team);
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(teamDetails.values());
    }

    public HashMap<Integer, Team> getAllTeamsForMatch() {
        return teamDetails;
    }

    public void addMatch(Match match) {
        match.getTeam1().addScores(match.getTeam1Score());
        match.getTeam2().addScores(match.getTeam2Score());
        matchDetails.put(match.getMatchId(),match);
    }

    public List<Match> getAllMatches() {
        return  new ArrayList<>(matchDetails.values());
    }

    public Team getTeam(int teamId) {
        return teamDetails.get(teamId);
    }

    public Match getMatch(int matchId) {
        return matchDetails.get(matchId);
    }

    public List<Player> getAllPlayers() {
        List<Player> players=new ArrayList<>();
        List<Team> teams= new ArrayList<>( teamDetails.values());
        for(Team team:teams){
            players.addAll(team.getPlayers());
        }
        return players;
    }

    public void upload() {
        writeFile();
    }
}
