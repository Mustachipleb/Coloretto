package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domein.DomeinController;
import domein.Kaart;
import domein.Spel;
import domein.Speler;
import domein.Stapel;

public class GameMapper
{
	private static ConnectionManager manager = new ConnectionManager();
	
	public static DomeinController retrieveGame(int gameId) throws SQLException
	{
		Connection conn = manager.getConnection();
		DomeinController dc = new DomeinController();

        List<Speler> players = new ArrayList<Speler>();
        List<Stapel> stacks = new ArrayList<Stapel>();
        Speler playerNext = null;
        
		PreparedStatement query = conn.prepareStatement(
        		"SELECT p.name, c.name, amount, isNext FROM `Players/Cards` AS pc "
        		+ "INNER JOIN `Cards` AS c ON c.idCard = pc.idCard "
        		+ "INNER JOIN `Players` AS p ON p.idPlayer = pc.idPlayer "
        		+ "INNER JOIN `Games` AS g ON g.idGame = p.idGame "
        		+ "WHERE g.idGame = ?");
		query.setInt(1, gameId);
        ResultSet rs = query.executeQuery();
        Map<String, List<Kaart>> playerCards = new HashMap<String, List<Kaart>>();
        String nextPlayer = "";
        while (rs.next())
        {
        	playerCards.putIfAbsent(rs.getString(1), new ArrayList<Kaart>());
        	for (int i = 0; i < rs.getInt(3); i++)
        	{
            	playerCards.get(rs.getString(1)).add(new Kaart(rs.getString(2)));
        	}
        	if (rs.getBoolean(4))
        	{
        		nextPlayer = rs.getString(1);
        	}
        }
        
        for (Map.Entry<String, List<Kaart>> player : playerCards.entrySet())
        {
        	Speler s = new Speler(player.getKey(), player.getValue());
        	if (player.getKey().equals(nextPlayer))
        		playerNext = s;
        	players.add(s);
        }
		
        query = conn.prepareStatement(
        		"SELECT s.idStack, c.name FROM `Stacks/Cards` AS sc "
        		+ "INNER JOIN `Cards` AS c ON c.idCard = sc.idCard "
        		+ "INNER JOIN `Stacks` AS s ON s.idStack = sc.idStack "
        		+ "WHERE s.idGame = ?");
        query.setInt(1, gameId);
        rs = query.executeQuery();
        Map<Integer, List<Kaart>> stackCards = new HashMap<Integer, List<Kaart>>();
        while (rs.next())
        {
        	stackCards.putIfAbsent(rs.getInt(1), new ArrayList<Kaart>());
        	stackCards.get(rs.getInt(1)).add(new Kaart(rs.getString(2)));
        }
        
        int stackNo = 0;
        for (Map.Entry<Integer, List<Kaart>> stack : stackCards.entrySet())
        {
        	Stapel s = new Stapel(stackNo);
        	stackNo++;
        	s.getKaarten().addAll(stack.getValue());
        	stacks.add(s);
        }
        
        dc.resumeGame(players, stacks, playerNext);
        return dc;
	}
	
	public static Map<String, Integer> retrieveHighScores() throws SQLException
	{
		Connection conn = manager.getConnection();
		Map<String, Integer> highScores = new HashMap<String, Integer>();
		
        PreparedStatement query = conn.prepareStatement(
        		"SELECT name, score FROM `Highscores` "
        		+ "ORDER BY score DESC "
        		+ "LIMIT 10");
        ResultSet rs = query.executeQuery();
        while (rs.next())
        {
        	highScores.put(rs.getString(1), rs.getInt(2));
        }
        
        return highScores;
	}
}
