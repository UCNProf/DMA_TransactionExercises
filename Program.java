import java.sql.SQLException;

public class Program {

	public static void main(String[] args) {

		//PessimisticConcurrencyExercise ex = new PessimisticConcurrencyExercise();
		//DeadlockExercise ex = new DeadlockExercise();
		OptimisticConcurrencyExercise ex = new OptimisticConcurrencyExercise();
		
		Thread t1 = new Thread("T1") {
			public void run() {
				
				try {
					
					ex.closeAccount(1, 2);
					
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		};
		
		Thread t2 = new Thread("T2") {
			
			public void run() {
				
				try {
					
					ex.addInterest(1);
					
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		};
		
		t1.start();
		t2.start();
	}
	
	public static void printMsg(String msg) {
		System.out.println(Thread.currentThread().getName() +" - "+ msg);
	}
}
