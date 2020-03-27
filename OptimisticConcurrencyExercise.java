import java.sql.*;

public class OptimisticConcurrencyExercise {

	private final int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;
	
	public void closeAccount(int closeAccountId, int transferAccount) throws SQLException {
		
		float amount = 0;
		
		String readBalanceSql = "SELECT Balance FROM Account WHERE Id = ?";
		String closeAccountSql = "UPDATE Account SET Balance = 0 WHERE Id = ?";
		String depositSql = "UPDATE Account SET Balance = Balance + ? WHERE Id = ?";
		
		Connection conn = Database.getConnection(isolationLevel);
		
		PreparedStatement readBalanceFromClosingAccount = conn.prepareStatement(readBalanceSql);
		readBalanceFromClosingAccount.setInt(1, closeAccountId);
		ResultSet rs = readBalanceFromClosingAccount.executeQuery();
		if(rs.next()) {
			amount = rs.getFloat(1);
			
			Program.printMsg("Transferring "+ amount +" from accountId: "+ closeAccountId);
			
			PreparedStatement closeAccount = conn.prepareStatement(closeAccountSql);
			closeAccount.setInt(1, closeAccountId);
			closeAccount.executeUpdate();
			
			Program.printMsg("Account "+ closeAccountId +" closed");
			
			PreparedStatement deposit = conn.prepareStatement(depositSql);
			deposit.setFloat(1, amount);
			deposit.setInt(2, transferAccount);
			deposit.executeUpdate();
			
			Program.printMsg(amount +" Transferred to account "+ transferAccount);
		}
	}

	public void addInterest(int accountId) throws SQLException {
		
		float amount = 0;
		float interest = 0;
		
		String readBalanceSql = "SELECT Balance FROM Account WHERE Id = ?";
		String addInterestSql = "UPDATE Account SET Balance = Balance + ? WHERE Id = ?";
		
		Connection conn = Database.getConnection(isolationLevel);

		PreparedStatement readBalance = conn.prepareStatement(readBalanceSql);
		readBalance.setInt(1, accountId);
		
		ResultSet rs = readBalance.executeQuery();
		if(rs.next()) {
			amount = rs.getFloat(1);	
			
			Program.printMsg("Read amount: "+ amount);
			
			interest = amount * 0.1f;
		
			PreparedStatement addInterest = conn.prepareStatement(addInterestSql);
			addInterest.setInt(2, accountId);
			addInterest.setFloat(1, interest);
			
			addInterest.execute();
			
			Program.printMsg("Added interest: "+ interest);
		}		
	}
}
