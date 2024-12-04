import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
	private static Transaction instance;
	private List<String> transactionHistory;
	
	//private constructor
	private Transaction() {
		transactionHistory = new ArrayList<>();
	}

	//Singleton instance method
	public static Transaction getTransaction() {
		if (instance == null) {
		instance = new Transaction();
		}
		return instance;
		
	}
	
	 public void saveTransaction(String transactionDetails) {
	        try (FileWriter writer = new FileWriter("transactions.txt", true)) {
	            writer.write(transactionDetails + "\n");
	        } catch (IOException e) {
	            System.out.println("Error in saving the transaction: " + e.getMessage());
	        }
	    }
	
    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            transactionHistory.add(transactionDetails); // Add to memory
            saveTransaction(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            transactionHistory.add(transactionDetails); // Add to memory
            saveTransaction(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }
    
    // Transaction history
    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded yet.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
        }
    

    // Get the current date and time in a readable format
    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}