package utils;

import java.sql.SQLException;

import connect.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import models.Cart;
import models.Product;
import models.TransactionDetail;
import models.TransactionHeader;

public class ConnectHandler {
	Connect con;
	
	//------------------validate-data--------------------//
	public boolean validateCartData(String userId, String cartId) {
		String query = "SELECT * FROM `carttable`";
		con.rs = con.execQuery(query);
		
		try {
			while (con.rs.next()) {
				String userIdDB = con.rs.getString("UserID");
				String cartIdDB = con.rs.getString("ProductID");
				
				if (userId.equals(userIdDB) && cartId.equals(cartIdDB)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean validateLoginData(String email, String pass) {
		String query = "SELECT * FROM `msuser`";
		con.rs = con.execQuery(query);
		
		try {
			while (con.rs.next()) {
				String emailDB = con.rs.getString("UserEmail");
				String passwordDB = con.rs.getString("UserPassword");
				
				if (email.equals(emailDB) && pass.equals(passwordDB)) {
					Main.currentUser = con.rs.getString("UserID"); 
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false; 
	}
	
	public boolean validateRegisterData(String email) {
		String query = "SELECT * FROM `msuser`";
		con.rs = con.execQuery(query);
		
		try {
			while (con.rs.next()) {
				String emailDB = con.rs.getString("UserEmail");
				
				if (email.equals(emailDB)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false; 
	}
	
	//------------------count-data--------------------//
	public int countData(String input) {
		int result = 0;
		String countedId = input.equals("msproduct")? "ProductID" : input.equals("msuser")? "UserID" : "TransactionID";
		
		String query = String.format("SELECT MAX(%s) as MaxID FROM `%s`", countedId, input);
		con.rs = con.execQuery(query);
		
		try {
			while (con.rs.next()) {
				String id = con.rs.getString("MaxID");
				result = Integer.parseInt(id.substring(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//------------------add-data--------------------//
	//user
	public void addData(String id, String email, String pass, String gender, String nat, int age) {
		String query = String.format("INSERT INTO `msuser` VALUES ('%s', '%s', '%s', '%d', '%s', '%s', '%s')", id, email, pass, age, gender, nat, "User");
		con.execUpdate(query);
	}
	//cart
	public void addData(String userId, String productId, int qty) {
		String query = String.format("INSERT INTO `carttable` VALUES ('%s','%s','%d')", userId, productId, qty);
		con.execUpdate(query);
	}
	
	//product
	public void addData(String id, String name, String brand, int price, int stock) {
		String query = String.format("INSERT INTO `msproduct` VALUES ('%s','%s','%s','%d','%d')", id, name, brand, price, stock);
		con.execUpdate(query);
	}
	
	//TrHeader
	public void addData(String trId, String userId, int delIns, String courierType) {
		String query = String.format("INSERT INTO `transactionheader` VALUES ('%s','%s',CURDATE(),'%d','%s')", trId, userId, delIns, courierType);
		con.execUpdate(query);
	}
	
	//TrDetail
	public void addDataDetail(String productId, String transactionId, int qty) {
		String query = String.format("INSERT INTO `transactiondetail` VALUES ('%s','%s','%d')", productId, transactionId, qty);
		con.execUpdate(query);
	}
	
	//------------------get-data--------------------//
	public ObservableList<Product> getProductData(boolean admin) {
		ObservableList<Product> list = FXCollections.observableArrayList();
		String query;
		if(admin) query = "SELECT * FROM `msproduct`";
		else  query = "SELECT * FROM `msproduct` WHERE ProductStock != 0";
		con.execQuery(query);
		
		try {
			while(con.rs.next()) {
				String id = con.rs.getString("ProductID");
				String name = con.rs.getString("ProductName");
				String mrek = con.rs.getString("ProductMerk");
				int stock = con.rs.getInt("ProductStock");
				int price = con.rs.getInt("ProductPrice");
				
				list.add(new Product(id, name, mrek, price, stock));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Cart> getCartData() {
		ObservableList<Cart> list = FXCollections.observableArrayList();
		String query = String.format("SELECT c.ProductID, ProductName, ProductMerk, ProductPrice, Quantity, SUM(ProductPrice*Quantity) as Total\n"
				+ "FROM CartTable c JOIN MsProduct m ON c.ProductID = m.ProductID WHERE UserID = '%s'\n"
				+ "GROUP BY m.ProductName, m.ProductMerk, m.ProductPrice;", Main.currentUser);
		con.execQuery(query);
		
		try {
			while(con.rs.next()) {
				String id = con.rs.getString("ProductID");
				String name = con.rs.getString("ProductName");
				String brand = con.rs.getString("ProductMerk");
				int price = con.rs.getInt("ProductPrice");
				int qty = con.rs.getInt("Quantity");
				int total = con.rs.getInt("Total");
				
				list.add(new Cart(id, name, brand, price, qty, total));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<TransactionHeader> getTHData(String userId) {
		ObservableList<TransactionHeader> list = FXCollections.observableArrayList();
		String query;
		if (userId.equals("1")) query = "SELECT TransactionID, msuser.UserEmail, TransactionDate FROM `transactionheader`\n"
				+ "JOIN `msuser` ON msuser.UserID = transactionheader.UserID ORDER BY TransactionID\n"; 
		else query = String.format("SELECT TransactionID, msuser.UserEmail, TransactionDate FROM `transactionheader`\n"
				+ "JOIN `msuser` ON msuser.UserID = transactionheader.UserID\n"
				+ "WHERE transactionheader.UserID = '%s' ORDER BY TransactionID", userId); 
		con.execQuery(query);
		
		try {
			while(con.rs.next()) {
				String id = con.rs.getString("TransactionID");
				String email = con.rs.getString("UserEmail");
				String date = con.rs.getString("TransactionDate");
				
				list.add(new TransactionHeader(id, email, date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<TransactionDetail> getTDData(String trId) {
		ObservableList<TransactionDetail> list = FXCollections.observableArrayList();
		String query = String.format("SELECT transactionheader.TransactionID, ProductName, ProductPrice, Quantity, (ProductPrice*Quantity) as TotalPrice  FROM transactionheader\n"
				+ "JOIN transactiondetail ON transactiondetail.TransactionID = transactionheader.TransactionID\n"
				+ "JOIN msproduct ON msproduct.ProductID = transactiondetail.ProductID\n"
				+ "WHERE transactionheader.TransactionID = '%s'", trId);
		con.execQuery(query);
		
		try {
			while(con.rs.next()) {
				String id = con.rs.getString("TransactionID");
				String name = con.rs.getString("ProductName");
				int price = con.rs.getInt("ProductPrice");
				int qty = con.rs.getInt("Quantity");
				int total = con.rs.getInt("TotalPrice");
				
				list.add(new TransactionDetail(id, name, price, qty, total));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//------------------update-data--------------------//
	public void updateStockData(String id, int changedValue, boolean add) {
		String query;
		if (add) query = String.format("UPDATE `msproduct` SET `ProductStock`= ProductStock + '%d' WHERE ProductID = '%s'", changedValue, id);
		else query = String.format("UPDATE `msproduct` SET `ProductStock`= ProductStock - '%d' WHERE ProductID = '%s'", changedValue, id);
		con.execUpdate(query);
	}
	public void updateCartData(String userid, String productid, int changedValue) {
		String query = String.format("UPDATE `carttable` SET `Quantity`= Quantity + '%d' WHERE `UserID`='%s' AND `ProductID`='%s'", changedValue, userid, productid);
		con.execUpdate(query);
	}
	
	//------------------remove-data--------------------//
	public void removeCartData(String userId, String productId) {
		 String query = String.format("DELETE FROM CartTable "
		            + "WHERE UserID = '%s' AND ProductID = '%s';",
		            userId, productId);
		    con.execUpdate(query);
	}
	
	public void removeAllCartData(String id) {
		String query = String.format("DELETE FROM CartTable WHERE UserID = '%s'", id);
		con.execUpdate(query);
	}
	
	public void removeProductData(String id) {
		String query = String.format("DELETE FROM msproduct WHERE ProductID = '%s'", id);
	    con.execUpdate(query);
	}
	
	public ConnectHandler() {
		con = Connect.getInstance();
	}
}
