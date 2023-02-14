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
		// DB연결해서 과일등록
		// JAVA에서 DB 연결하는 방법 JDBC(자바데이터베이스커넥트)
		//1. DB연결
		//1-1. JDBC드라이버 로드
		//1-2. 연결해서 Connection 객체생성
		//2. 쿼리작업
		//2-1. 커넥션객체를 가지고 Statement 객체생성
		//2-2. 스테이트먼트 객체를 가지고 query 작업
		//     select문의 결과는 ResultSet 객체로 받아서 작업 가능	
		dbConn();			
		try {
			pstmt= conn.prepareStatement("insert into fruit(fruit_name, price,quantity) values (?,?,?)");
			//stmt= conn.createStatement(); createStatement는 무방비하게 자료가 노출될 수 있기 때문에 최근에는 사용하지 않음
			
			/*String name=vo.getFruit_name();
			int price=vo.getPrice();
			int quantity=vo.getQuantity();*/
			pstmt.setString(1, vo.getFruit_name()); //? 채우기
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getQuantity());
			
			//String query= "insert into fruit(fruit_name, price,quantity) values ('"+name+"',"+price+","+quantity+")";
			//stmt.executeUpdate(query);	
			
			pstmt.executeUpdate(); //삽입,삭제,수정시에는 executeUpdate()를 - 반환값 int는 처리된 행의 개수
			                      //read(select)시에는 executeQuery()이용 - 반환값 ResultSet 객체 결과값을 돌려준다		
		} catch (Exception e) {
			e.printStackTrace();
		}
					
		//3. 사용후 DB연결 끊기
		// ResultSet, Statement, Connection 객체 닫아주기
		dbClose();
	}

	@Override
	public ArrayList<FruitVO> listFruit() {
		ArrayList<FruitVO> list= new ArrayList<>();
		//1. db연결
		dbConn();
		//2. 쿼리작업후 결과가져오기(ResultSet)
		try {
			pstmt= conn.prepareStatement("select * from fruit order by fruit_code");
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				//next()는 언제나 다음행을 가르킴(최초에는 아무것도 안 가르키고있음), 리턴값이 boolean, 리턴은 다음행 가르키는게 성공이면 true, 없으면 false
				//while문에 넣으면 순차적으로 돌고 마지막에 가르키는게 없어 false가 나오면 멈춤
				
				//int code= rs.getInt(1); //()안의 숫자는 속성 순서 ex> 여기서는 1을 넣으면 과일코드가, 3을 넣으면 price가 나옴
				/*int code= rs.getInt("fruit_code"); //즉각적으로 무엇을 뜻하는지 알수 있게 위처럼 index로 가져오는 것보다 String으로 가져오는게 좋음
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

				//System.out.println(code+" "+name+" "+price+" "+quantity); //code가 잘 돌아가는지 테스트하기 위한 코드
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//3. 리턴타입으로 변환하기
		//4. db닫기
		dbClose();
		//5. 변환한거 리턴		
		return list;
	}
	
	//DB연결
	void dbConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "aaa", "Wpqkfehlfk@0");
			//오라클의 경우에는 주소 맨뒤에 기본스키마를 따로 적지않고 SDI를 적어줘야한다. ex> jdbc:oracle:thin:@localhost:1521:xe
			System.out.println("연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	//DB닫기
	void dbClose() {
		if(result!=null) try {result.close();} catch (SQLException e) {e.printStackTrace();}
		if(pstmt !=null) try {pstmt.close(); } catch (SQLException e) {e.printStackTrace();}
		if(conn  !=null) try {conn.close();  } catch (SQLException e) {e.printStackTrace();}
		//conn이랑 따로 trycatch를 하는 이유는 pstmt가 에러가 나도 conn을 닫아야하기 때문
		//if문에 {}를 생략하면 바로 밑에 한문장만 해당, catch는 try에 포함된 문장이기때문에 여기서는 if의 {} 생략가능
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
		//1. db연결
		dbConn();
		//2. 총가격
		int total = 0; //-1처럼 나올수 없는 값을 넣어서 에러를 체크할 수도 있음
		try {
			pstmt= conn.prepareStatement("select price*? from fruit where fruit_code=?");
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setInt(2, vo.getFruit_code()); //Query 완성			
			result= pstmt.executeQuery();
			result.next(); //항상 행이 하나기때문에 첫번째 행을 가르키게 한번만 작성(반복문X)
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
			conn.setAutoCommit(false); //1.오토커밋 금지 (트랜잭션을 수행하기 위해 1-3을 모두 넣어줘야함)
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			pstmt= conn.prepareStatement("insert into sales(sales_quantity) values(?)");
			pstmt.setInt(1, quantity);
			pstmt.executeUpdate();
			
			result= pstmt.executeQuery("select last_insert_id()"); //입력된 키값 확인
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
*//*		이 방식으로 사용하지 못하고 위의 코드처럼 다시 작성해서 사용하는 이유는
			1. updateQuantityFruit메소드에서 새롭게 conn을 연결하기 때문에 위에 연결했던 내용이 사용되지 못하고 쓰레기가 되어버린다.
			2. 새로 연결되었기때문에 오토커밋금지가 걸려있지않음
			3. 위에 fruit_has_sales테이블이 외래키로 잡혀있어서 같은 키로 수행하려면 이미 락이 걸려있어 업데이트가 되지않음
*/			
			conn.commit(); //2.정상처리되면 커밋처리
			//커밋처리를 먼저 하고 updateQuantityFruit()를 사용하게 되면 커밋처리에서 트랜젝션이 종료되고
			//update~~()에서 에러가 나면 반영이 되지 않기때문에 자료가 제대로 반영되지 않음
			
		} catch (SQLException e) {
			System.out.println("판매실패");
			try {conn.rollback();} catch (SQLException e1) {e1.printStackTrace();}//3.중간에 문제가 생기면 롤백
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
					   
			//수행할 쿼리를 만들고		   
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
//			쿼리 오류확인을 위해 String으로 쿼리를 출력해서 나온 쿼리를 workbench에 넣어서 확인할 수 있음

			result=pstmt.executeQuery(); //쿼리를 수행해서 결과를 가져오고
			result.next(); //결과에 첫번째 행을 가르키고
			totalPrice= result.getLong(1); //첫번째 속성값을 long타입으로 변화해서 읽어온다.
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
