package kr.edu.mit;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class StoreMain {

	public static void main(String[] args) {
		//1. 과일입고  2. 재고파악  3. 판매하기  4. 매출확인
		Scanner in= new Scanner(System.in);
		
		FruitStoreDAO dao = new FruitStoreDAOImpl();
		
		int menuNum;
		do {
			//메뉴출력하기(ctrl+alt+방향키 위아래 ->  한줄복사 가능)
			System.out.println("1. 과일입력");
			System.out.println("2. 재고파악");
			System.out.println("3. 판매하기");
			System.out.println("4. 매출확인");
			System.out.print("메뉴를 선택해주세요.(0은 종료): ");
			
			//사용자입력받고 해당메뉴 실행하기
			menuNum=in.nextInt();
			List<FruitVO> list = null;
			if(menuNum==1) {				
				//1. 과일목록보여주기				
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println("과일코드: "+vo.getFruit_code()+" 과일이름: "+vo.getFruit_name()+" 과일가격: "+vo.getPrice()+" 과일수량: "+vo.getQuantity());
				}
				System.out.println("과일목록입니다.\n"+"1번. 새로운 과일을 추가\n2번. 기존 과일에 수량만 추가");
				int addFruit =in.nextInt();
				//2. 선택할지 추가할지 입력받고
				FruitVO vo= new FruitVO();
				if(addFruit==1) {	
				//3-1. 선택일 경우
				//     입고수량 받고 DB처리(업데이트) -> 보내줘야할 내용: 과일코드, 수량					
					System.out.println("과일이름: ");
					String fruit_name= in.next();
					System.out.println("과일가격: ");
					int price= in.nextInt();
					System.out.println("과일수량: ");
					int quantity= in.nextInt();
							
					vo.setFruit_name(fruit_name);
					vo.setPrice(price);
					vo.setQuantity(quantity);
					dao.insertFruit(vo);					
				}else if(addFruit==2) {
				//3-2. 추가일 경우
				//     과일이름, 가격, 수량 받고 DB처리(삽입)					
					System.out.println("과일코드: ");
					int fruit_code= in.nextInt();
					System.out.println("과일수량: ");
					int quantity= in.nextInt();
					vo.setFruit_code(fruit_code);
					vo.setQuantity(quantity);
					dao.updateQuantityFruit(vo);
				}				
			}else if(menuNum==2) {
				System.out.println("현재 재고입니다.");
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println("과일코드: "+vo.getFruit_code()+" 과일이름: "+vo.getFruit_name()+" 과일가격: "+vo.getPrice()+" 과일수량: "+vo.getQuantity());
				}				
			}else if(menuNum==3) {
				//1. 과일목록보여주기 - (DB)과일목록보여주기
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}
				System.out.println("과일목록입니다. 과일번호를 선택해주세요: ");		
				
				//2. 사용자가 선택(코드, 개수)
				int fruit_code=in.nextInt(); //과일번호 입력
				System.out.println("구매할 수량을 입력해주세요: ");
				int quantity=in.nextInt(); //과일수량
				
				//3. 지불금액안내 - (DB)과일별 총가격알려주기
				FruitVO vo = new FruitVO();
				vo.setFruit_code(fruit_code);
				vo.setQuantity(quantity);					
				System.out.println("총구매금액은 "+dao.totalFruit(vo)+"입니다.");
				System.out.println("구매하시겠습니까?(1:구매, 2:취소)");
				if(in.nextInt()==1) {
				//4. 판매완료 - (DB)판매처리
					dao.insertSales(fruit_code, quantity);					
				}				
			}else if(menuNum==4) {
				System.out.println("매출내역입니다.");
				List<SalesVO> listSales= dao.listSales();
				for(SalesVO vo : listSales) {
					System.out.println("과일이름: "+vo.getFruit_name()+" 과일코드: "+vo.getFruit_code()+" 판매일자: "+vo.getSales_date()+" 판매수량: "+vo.getSales_quantity()+" 판매금액: "+vo.getTotal());
					System.out.println();
				}
				System.out.println("총판매금액은 "+dao.totalprice()+"입니다.");
				System.out.println();
						
			}else if(menuNum==0) {
				System.out.println("이용해주셔서 감사합니다.");
			}
		}while(menuNum!=0);
	
	}

}
