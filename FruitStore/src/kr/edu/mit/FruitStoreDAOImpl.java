package kr.edu.mit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FruitStoreDAOImpl implements FruitStoreDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet result=null;
	
	@Override
	public void insertFruit(FruitVO vo) {
		// DB�����ؼ� ���ϵ��
		// JAVA���� DB �����ϴ� ��� JDBC(�ڹٵ����ͺ��̽�Ŀ��Ʈ)
		//1. DB����
		//1-1. JDBC����̹� �ε�
		//1-2. �����ؼ� Connection ��ü����
		//2. �����۾�
		//2-1. Ŀ�ؼǰ�ü�� ������ Statement ��ü����
		//2-2. ������Ʈ��Ʈ ��ü�� ������ query �۾�
		//     select���� ����� ResultSet ��ü�� �޾Ƽ� �۾� ����	
		dbConn();			
		try {
			pstmt= conn.prepareStatement("insert into fruit(fruit_name, price,quantity) values (?,?,?)");
			//stmt= conn.createStatement(); createStatement�� ������ϰ� �ڷᰡ ����� �� �ֱ� ������ �ֱٿ��� ������� ����
			
			/*String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();*/
			pstmt.setString(1, vo.getFruit_name()); //? ä���
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getQuantity());
			
			//String query= "insert into fruit(fruit_name, price,quantity) values ('"+name+"',"+price+","+quantity+")";
			//stmt.executeUpdate(query);	
			
			pstmt.executeUpdate(); //����,����,�����ÿ��� executeUpdate()�� - ��ȯ�� int�� ó���� ���� ����
			                      //read(select)�ÿ��� executeQuery()�̿� - ��ȯ�� ResultSet ��ü ������� �����ش�		
		} catch (Exception e) {
			e.printStackTrace();
		}
					
		//3. ����� DB���� ����
		// ResultSet, Statement, Connection ��ü �ݾ��ֱ�
		dbClose();
	}

	@Override
	public ArrayList<FruitVO> listFruit() {
		ArrayList<FruitVO> list= new ArrayList<>();
		//1. db����
		dbConn();
		//2. �����۾��� �����������(ResultSet)
		try {
			pstmt= conn.prepareStatement("select * from fruit order by fruit_code");
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				//next()�� ������ �������� ����Ŵ(���ʿ��� �ƹ��͵� �� ����Ű������), ���ϰ��� boolean, ������ ������ ����Ű�°� �����̸� true, ������ false
				//while���� ������ ���������� ���� �������� ����Ű�°� ���� false�� ������ ����
				
				//int code= rs.getInt(1); //()���� ���ڴ� �Ӽ� ���� ex> ���⼭�� 1�� ������ �����ڵ尡, 3�� ������ price�� ����
				/*int code= rs.getInt("fruit_code"); //�ﰢ������ ������ ���ϴ��� �˼� �ְ� ��ó�� index�� �������� �ͺ��� String���� �������°� ����
				String name= rs.getString("fruit_name");
				int price= rs.getInt("price");
				int quantity= rs.getInt("quantity");
				*/				
				FruitVO vo= new FruitVO();	
				vo.setFruit_code(rs.getInt("fruit_code"));
				vo.setFruit_name(rs.getString("fruit_name"));
				vo.setPrice(rs.getInt("price"));
				vo.setQuantity(rs.getInt("quantity"));
				list.add(vo);

				//System.out.println(code+" "+name+" "+price+" "+quantity); //code�� �� ���ư����� �׽�Ʈ�ϱ� ���� �ڵ�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//3. ����Ÿ������ ��ȯ�ϱ�
		//4. db�ݱ�
		dbClose();
		//5. ��ȯ�Ѱ� ����		
		return list;
	}
	
	//DB����
	void dbConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");
			//����Ŭ�� ��쿡�� �ּ� �ǵڿ� �⺻��Ű���� ���� �����ʰ� SDI�� ��������Ѵ�. ex> jdbc:oracle:thin:@localhost:1521:xe
			System.out.println("���Ἲ��");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	//DB�ݱ�
	void dbClose() {
		if(result!=null) try {result.close();} catch (SQLException e) {e.printStackTrace();}
		if(pstmt !=null) try {pstmt.close(); } catch (SQLException e) {e.printStackTrace();}
		if(conn  !=null) try {conn.close();  } catch (SQLException e) {e.printStackTrace();}
		//conn�̶� ���� trycatch�� �ϴ� ������ pstmt�� ������ ���� conn�� �ݾƾ��ϱ� ����
		//if���� {}�� �����ϸ� �ٷ� �ؿ� �ѹ��常 �ش�, catch�� try�� ���Ե� �����̱⶧���� ���⼭�� if�� {} ��������
	}

	@Override
	public void updateQuantityFruit(FruitVO vo) {
		dbConn();
		try {
			pstmt= conn.prepareStatement("update fruit set quantity=quantity+? where fruit_code=?");
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getFruit_code());				
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}	
	}

	@Override
	public int totalFruit(FruitVO vo) {
		//1. db����
		dbConn();
		//2. �Ѱ���
		int total = 0; //-1ó�� ���ü� ���� ���� �־ ������ üũ�� ���� ����
		try {
			pstmt= conn.prepareStatement("select price*? from fruit where fruit_code=?");
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getFruit_code()); //Query �ϼ�			
			result= pstmt.executeQuery();
			result.next(); //�׻� ���� �ϳ��⶧���� ù��° ���� ����Ű�� �ѹ��� �ۼ�(�ݺ���X)
			total= result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return total;
	}

	@Override
	public void insertSales(int fruit_code, int quantity) {
		dbConn();
		try {
			conn.setAutoCommit(false); //1.����Ŀ�� ���� (Ʈ������� �����ϱ� ���� 1-3�� ��� �־������)
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			pstmt= conn.prepareStatement("insert into sales(sales_quantity) values(?)");
			pstmt.setInt(1, quantity);
			pstmt.executeUpdate();
			
			result= pstmt.executeQuery("select last_insert_id()"); //�Էµ� Ű�� Ȯ��
			result.next();
			int key= result.getInt(1);
			
			//pstmt.executeLargeUpdate("insert into fruit_has_sales values(" +fruit_code+ "," +key+ ")");
			//or
			pstmt.close();
			pstmt=conn.prepareStatement("insert into fruit_has_sales values(?,?)");
			pstmt.setInt(1, fruit_code);
			pstmt.setInt(2, key);
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=conn.prepareStatement("update fruit set quantity=quantity-? where fruit_code=?");
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, fruit_code);
			pstmt.executeUpdate();
			
/*			FruitVO vo= new FruitVO();
			vo.setFruit_code(fruit_code);
			vo.setQuantity(-quantity);
			updateQuantityFruit(vo);
*//*		�� ������� ������� ���ϰ� ���� �ڵ�ó�� �ٽ� �ۼ��ؼ� ����ϴ� ������
			1. updateQuantityFruit�޼ҵ忡�� ���Ӱ� conn�� �����ϱ� ������ ���� �����ߴ� ������ ������ ���ϰ� �����Ⱑ �Ǿ������.
			2. ���� ����Ǿ��⶧���� ����Ŀ�Ա����� �ɷ���������
			3. ���� fruit_has_sales���̺��� �ܷ�Ű�� �����־ ���� Ű�� �����Ϸ��� �̹� ���� �ɷ��־� ������Ʈ�� ��������
*/			
			conn.commit(); //2.����ó���Ǹ� Ŀ��ó��
			//Ŀ��ó���� ���� �ϰ� updateQuantityFruit()�� ����ϰ� �Ǹ� Ŀ��ó������ Ʈ�������� ����ǰ�
			//update~~()���� ������ ���� �ݿ��� ���� �ʱ⶧���� �ڷᰡ ����� �ݿ����� ����
			
		} catch (SQLException e) {
			System.out.println("�ǸŽ���");
			try {conn.rollback();} catch (SQLException e1) {e1.printStackTrace();}//3.�߰��� ������ ����� �ѹ�
			e.printStackTrace();
		}finally {
			dbClose();
		}		
	}

	@Override
	public long totalprice() {
		dbConn();	
		long totalPrice=-1;
		try {		
/*			String query="select sum(price*sales_quantity)" +
					   "from fruit" +
					   "     join  "+
					   "     (select fruit_fruit_code, sales_date, sales_quantity" +
					   "     from fruit_has_sales" +
					   "          join sales" +
					   "          on fruit_has_sales.sales_sales_code=sales.sales_code" +
					   "     ) t1" +
					   "     on fruit.fruit_code=t1.fruit_fruit_code";
					   
			//������ ������ �����		   
*/			pstmt= conn.prepareStatement("select sum(price*sales_quantity)" +
				   "from fruit " +
				   "     join "+
				   "     (select fruit_fruit_code, sales_date, sales_quantity" +
				   "     from fruit_has_sales" +
				   "          join sales" +
				   "          on fruit_has_sales.sales_sales_code=sales.sales_code" +
				   "     ) t1" +
				   "     on fruit.fruit_code=t1.fruit_fruit_code");
//			System.out.println(query);
//			���� ����Ȯ���� ���� String���� ������ ����ؼ� ���� ������ workbench�� �־ Ȯ���� �� ����

			result=pstmt.executeQuery(); //������ �����ؼ� ����� ��������
			result.next(); //����� ù��° ���� ����Ű��
			totalPrice= result.getLong(1); //ù��° �Ӽ����� longŸ������ ��ȭ�ؼ� �о�´�.
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}		
		return totalPrice;
	}

	@Override
	public List<SalesVO> listSales() {
		List<SalesVO> list = new ArrayList<>();
		dbConn();
		
		try {
			pstmt= conn.prepareStatement("select fruit_name, fruit_code, sales_date, sales_quantity, price*sales_quantity " + 
				   "from fruit " +
				   "     join "+
				   "     (select fruit_fruit_code, sales_date, sales_quantity" +
				   "     from fruit_has_sales" +
				   "          join sales" +
				   "          on fruit_has_sales.sales_sales_code=sales.sales_code" +
				   "     ) t1" +
				   "     on fruit.fruit_code=t1.fruit_fruit_code");
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				SalesVO vo= new SalesVO();	
				vo.setFruit_code(rs.getInt("fruit_code"));
				vo.setFruit_name(rs.getString("fruit_name"));
				vo.setSales_date(rs.getTimestamp("sales_date"));
				vo.setSales_quantity(rs.getInt("sales_quantity"));
				vo.setTotal(rs.getInt("price*sales_quantity"));
				list.add(vo);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}		
		return list;
	}



}
