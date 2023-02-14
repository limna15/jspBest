package kr.edu.mit;

import java.util.ArrayList;
import java.util.List;

public interface FruitStoreDAO { //DAO data access object 데이터베이스의 data에 접근하기 위한 객체

	//////////쿼리별로 구현///////////
	//과일등록
	void insertFruit(FruitVO vo);
	
	//과일목록보여주기
	ArrayList<FruitVO> listFruit();
	
	//수량업데이트
	void updateQuantityFruit(FruitVO vo);
	
	//과일별 총가격 알려주기
	int totalFruit(FruitVO vo);
	
	//판매처리
	//1. 판매내용 추가 - 2. 판매내용 추가 키값 알아오기 - 3. 교차테이블 갱신(추가) - 4. 재고수정처리
	//(커넥션을 닫은후에 키값이 0이 나와 알수 없기때문에 한번 연결됐을때 같이 작업해야함 > 같은 트랜잭션)	
	void insertSales(int fruit_code, int quantity); //키값을 리턴
	
	//총판매금액
	long totalprice();
	
	//매출내역보기
	List<SalesVO> listSales();
	//List로 선언해서 사용하면 알맞는걸로 알아서 사용해줌
}
