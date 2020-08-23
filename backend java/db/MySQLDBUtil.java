package db;

public class MySQLDBUtil {
	//from AWS RDS
	private static final String INSTANCE = 
			"jobrecommandproject.ci5crgbolprq.us-west-1.rds.amazonaws.com";
	private static final String PORT_NUM = "3306";
	public static final String DB_NAME = "jupiter";
	private static final String USERNAME = "adminLu";
	private static final String PASSWORD = "123456789";
	
	public static final String URL = "jdbc:mysql://"
			+ INSTANCE + ":" + PORT_NUM + "/" + DB_NAME
			+ "?user=" + USERNAME + "&password=" + PASSWORD
			+ "&autoReconnect=true&serverTimezone=UTC";

}
