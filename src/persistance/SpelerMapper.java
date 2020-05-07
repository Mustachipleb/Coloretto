package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper
{
	private static ConnectionManager manager = new ConnectionManager();
	
	public static void addPlayer(Speler speler)
	{
		try(Connection conn = manager.getConnection())
		{
            PreparedStatement queryNieuweGebruiker = conn.prepareStatement("INSERT INTO players(idPlayer) VALUES (?, ?)");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
