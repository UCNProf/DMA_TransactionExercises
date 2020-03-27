import java.sql.*;

public class DeadlockExercise {

	private final int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;

	public void transferAmount(int fromAccountId, int toAccountId, float amount) throws SQLException {
		
		String withdrawSql = "UPDATE Account SET Balance = Balance - ? WHERE Id = ?";
		String depositSql = "UPDATE Account SET Balance = Balance + ? WHERE Id = ?";		
		String readBalanceSql = "SELECT Name, Balance FROM Account WHERE Id = ? OR Id = ?";
		
		Connection conn = Database.getConnection(isolationLevel);
		
		try {
			conn.setAutoCommit(false);
					
			PreparedStatement withdraw = conn.prepareStatement(withdrawSql);
			withdraw.setFloat(1, amount);
			withdraw.setInt(2, fromAccountId);
			
			withdraw.execute();
			
			Program.printMsg("Withdrew: "+ amount);
			
			PreparedStatement deposit = conn.prepareStatement(depositSql);
			deposit.setFloat(1, amount);
			deposit.setFloat(2, toAccountId);
			
			deposit.execute();
			
			Program.printMsg("Deposited: "+ amount);
			
			PreparedStatement readBalance = conn.prepareStatement(readBalanceSql);		
			readBalance.setInt(1, fromAccountId);
			readBalance.setInt(2, toAccountId);
			
			ResultSet rs = readBalance.executeQuery();
			while(rs.next()) {
				
				Program.printMsg("Account: "+ rs.getString(1) +" Balance: "+ rs.getFloat(2));
			}

			conn.commit();
			
		} catch (SQLException e) {

			conn.rollback();
			throw e;
			
		} finally {
			
			conn.setAutoCommit(true);
		}
	}
}
