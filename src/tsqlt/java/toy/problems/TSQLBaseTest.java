package toy.problems;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class TSQLBaseTest {
	private final static String TEST_DB = "InfiniteCampusTest";
	private final static String DB_SOURCE = "DB-README.md";
	private final static String TSQLT_CLASS = "tSQLt.class.sql";

	private static void createDatabase(Connection con, String dbName) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("EXEC sp_configure 'clr enabled', 1");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("RECONFIGURE");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("EXEC sp_configure 'show advanced option', '1'");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("RECONFIGURE");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("EXEC sp_configure 'clr strict security', 0");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("RECONFIGURE");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("EXEC sp_configure 'show advanced option', '0'");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("RECONFIGURE");
		}
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("create database " + dbName + " WITH TRUSTWORTHY ON");
		}
	}

	private static void deleteDatabase(Connection con, String dbName) throws SQLException {
		try (Statement stmt = con.createStatement();) {
			String SQL = "drop database " + dbName;
			stmt.executeUpdate(SQL);
		}
	}

	private static void processSourceFiles(Connection con, Path srcDir)
			throws SQLException, IOException, URISyntaxException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(srcDir, "*.sql")) {
			for (Path entry : stream) {
				String sqlString = Files.readString(entry);
				executeComplexStatement(con, sqlString);
			}
		}

	}

	private static void executeComplexStatement(Connection con, String sql) {
		try (Statement stmt = con.createStatement();) {
			boolean results = stmt.execute(sql);
			int count = 0;
			do {
				if (results) {
					@SuppressWarnings("unused")
					ResultSet rs = stmt.getResultSet();
					System.out.println("Result set data displayed here.");
				} else {
					count = stmt.getUpdateCount();
					if (count >= 0) {
						System.out.println("DDL or update data displayed here.");
					} else {
						System.out.println("No more results to process.");
					}
				}
				results = stmt.getMoreResults();
			} while (results || count != -1);
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
		TSQLBaseTest app = new TSQLBaseTest();
		URL url = app.getClass().getClassLoader().getResource(DB_SOURCE);
		Path srcDir = Paths.get(url.toURI()).getParent();
		SQLServerDataSource datasource = new SQLServerDataSource();
		datasource.setApplicationName("tsqlt");
		datasource.setApplicationIntent("ReadWrite");
		datasource.setDisableStatementPooling(true);
		datasource.setResponseBuffering("adaptive");
		datasource.setWorkstationID("myLocalHost");
		try (Connection masterConn = datasource.getConnection("tsqlt", "tsqlt")) {
//			deleteDatabase(masterConn, TEST_DB);
			createDatabase(masterConn, TEST_DB);
			datasource.setDatabaseName(TEST_DB);
			try (Connection buildConn = datasource.getConnection("tsqlt", "tsqlt")) {
				processSourceFiles(buildConn, srcDir);
				URL tsqltUrl = app.getClass().getClassLoader().getResource(TSQLT_CLASS);
				String[] sqlStatements = Files.readString(Paths.get(tsqltUrl.toURI())).split("GO");
				for (String stmt : sqlStatements) {
					executeComplexStatement(buildConn, stmt);
				}
				executeComplexStatement(buildConn, "tSQLt.RunAll");				
			}

			deleteDatabase(masterConn, TEST_DB);
		} finally {
		}
	}

}
