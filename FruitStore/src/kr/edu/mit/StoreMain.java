package kr.edu.mit;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class StoreMain {

	public static void main(String[] args) {
		//1. �����԰�  2. ����ľ�  3. �Ǹ��ϱ�  4. ����Ȯ��
		Scanner in= new Scanner(System.in);
		
		FruitStoreDAO dao = new FruitStoreDAOImpl();
		
		int menuNum;
		do {
			//�޴�����ϱ�(ctrl+alt+����Ű ���Ʒ� ->  ���ٺ��� ����)
			System.out.println("1. �����Է�");
			System.out.println("2. ����ľ�");
			System.out.println("3. �Ǹ��ϱ�");
			System.out.println("4. ����Ȯ��");
			System.out.print("�޴��� �������ּ���.(0�� ����): ");
			
			//������Է¹ް� �ش�޴� �����ϱ�
			menuNum=in.nextInt();
			List<FruitVO> list = null;
			if(menuNum==1) {				
				//1. ���ϸ�Ϻ����ֱ�				
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println("�����ڵ�: "+vo.getFruit_code()+" �����̸�: "+vo.getFruit_name()+" ���ϰ���: "+vo.getPrice()+" ���ϼ���: "+vo.getQuantity());
				}
				System.out.println("���ϸ���Դϴ�.\n"+"1��. ���ο� ������ �߰�\n2��. ���� ���Ͽ� ������ �߰�");
				int addFruit =in.nextInt();
				//2. �������� �߰����� �Է¹ް�
				FruitVO vo= new FruitVO();
				if(addFruit==1) {	
				//3-1. ������ ���
				//     �԰���� �ް� DBó��(������Ʈ) -> ��������� ����: �����ڵ�, ����					
					System.out.println("�����̸�: ");
					String fruit_name= in.next();
					System.out.println("���ϰ���: ");
					int price= in.nextInt();
					System.out.println("���ϼ���: ");
					int quantity= in.nextInt();
							
					vo.setFruit_name(fruit_name);
					vo.setPrice(price);
					vo.setQuantity(quantity);
					dao.insertFruit(vo);					
				}else if(addFruit==2) {
				//3-2. �߰��� ���
				//     �����̸�, ����, ���� �ް� DBó��(����)					
					System.out.println("�����ڵ�: ");
					int fruit_code= in.nextInt();
					System.out.println("���ϼ���: ");
					int quantity= in.nextInt();
					vo.setFruit_code(fruit_code);
					vo.setQuantity(quantity);
					dao.updateQuantityFruit(vo);
				}				
			}else if(menuNum==2) {
				System.out.println("���� ����Դϴ�.");
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println("�����ڵ�: "+vo.getFruit_code()+" �����̸�: "+vo.getFruit_name()+" ���ϰ���: "+vo.getPrice()+" ���ϼ���: "+vo.getQuantity());
				}				
			}else if(menuNum==3) {
				//1. ���ϸ�Ϻ����ֱ� - (DB)���ϸ�Ϻ����ֱ�
				list= dao.listFruit();
				for(FruitVO vo : list) {
					System.out.println(vo);
				}
				System.out.println("���ϸ���Դϴ�. ���Ϲ�ȣ�� �������ּ���: ");		
				
				//2. ����ڰ� ����(�ڵ�, ����)
				int fruit_code=in.nextInt(); //���Ϲ�ȣ �Է�
				System.out.println("������ ������ �Է����ּ���: ");
				int quantity=in.nextInt(); //���ϼ���
				
				//3. ���ұݾ׾ȳ� - (DB)���Ϻ� �Ѱ��ݾ˷��ֱ�
				FruitVO vo = new FruitVO();
				vo.setFruit_code(fruit_code);
				vo.setQuantity(quantity);					
				System.out.println("�ѱ��űݾ��� "+dao.totalFruit(vo)+"�Դϴ�.");
				System.out.println("�����Ͻðڽ��ϱ�?(1:����, 2:���)");
				if(in.nextInt()==1) {
				//4. �ǸſϷ� - (DB)�Ǹ�ó��
					dao.insertSales(fruit_code, quantity);					
				}				
			}else if(menuNum==4) {
				System.out.println("���⳻���Դϴ�.");
				List<SalesVO> listSales= dao.listSales();
				for(SalesVO vo : listSales) {
					System.out.println("�����̸�: "+vo.getFruit_name()+" �����ڵ�: "+vo.getFruit_code()+" �Ǹ�����: "+vo.getSales_date()+" �Ǹż���: "+vo.getSales_quantity()+" �Ǹűݾ�: "+vo.getTotal());
					System.out.println();
				}
				System.out.println("���Ǹűݾ��� "+dao.totalprice()+"�Դϴ�.");
				System.out.println();
						
			}else if(menuNum==0) {
				System.out.println("�̿����ּż� �����մϴ�.");
			}
		}while(menuNum!=0);
	
	}

}
