package test;

import kr.edu.mit.FruitStoreDAO;
import kr.edu.mit.FruitStoreDAOImpl;
import kr.edu.mit.FruitVO;
import kr.edu.mit.SalesVO;

public class Test {

	public static void main(String[] args) {
		// �ڹ�Ŭ���� �׽�Ʈ
		FruitStoreDAO dao= new FruitStoreDAOImpl();
		FruitVO vo= new FruitVO();
		vo.setFruit_name("�޵�");
		vo.setPrice(5000);
		vo.setQuantity(20);
		dao.insertFruit(vo);
	

	}

}
