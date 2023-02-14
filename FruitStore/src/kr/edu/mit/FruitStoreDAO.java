package kr.edu.mit;

import java.util.ArrayList;
import java.util.List;

public interface FruitStoreDAO { //DAO data access object �����ͺ��̽��� data�� �����ϱ� ���� ��ü

	//////////�������� ����///////////
	//���ϵ��
	void insertFruit(FruitVO vo);
	
	//���ϸ�Ϻ����ֱ�
	ArrayList<FruitVO> listFruit();
	
	//����������Ʈ
	void updateQuantityFruit(FruitVO vo);
	
	//���Ϻ� �Ѱ��� �˷��ֱ�
	int totalFruit(FruitVO vo);
	
	//�Ǹ�ó��
	//1. �Ǹų��� �߰� - 2. �Ǹų��� �߰� Ű�� �˾ƿ��� - 3. �������̺� ����(�߰�) - 4. ������ó��
	//(Ŀ�ؼ��� �����Ŀ� Ű���� 0�� ���� �˼� ���⶧���� �ѹ� ��������� ���� �۾��ؾ��� > ���� Ʈ�����)	
	void insertSales(int fruit_code, int quantity); //Ű���� ����
	
	//���Ǹűݾ�
	long totalprice();
	
	//���⳻������
	List<SalesVO> listSales();
	//List�� �����ؼ� ����ϸ� �˸´°ɷ� �˾Ƽ� �������
}
