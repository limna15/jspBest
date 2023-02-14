package kr.edu.mit;

import java.util.Date;

public class SalesVO {
	private String fruit_name;
	private int fruit_code;
	private Date sales_date;
	private int sales_quantity;
	//Integer 객체로 선언하면 null값으로 래핑할 수 있음
	//int로 선언하면 0인지 null인지 구분할 수 없음
	private int total;
	
	public String getFruit_name() {
		return fruit_name;
	}
	public void setFruit_name(String fruit_name) {
		this.fruit_name = fruit_name;
	}
	public int getFruit_code() {
		return fruit_code;
	}
	public void setFruit_code(int fruit_code) {
		this.fruit_code = fruit_code;
	}
	public Date getSales_date() {
		return sales_date;
	}
	public void setSales_date(Date sales_date) {
		this.sales_date = sales_date;
	}
	public int getSales_quantity() {
		return sales_quantity;
	}
	public void setSales_quantity(int sales_quantity) {
		this.sales_quantity = sales_quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
		
	//콤마용 메소드를 하나 만들어보기
	
	@Override
	public String toString() {
		return "SalesVO [fruit_name=" + fruit_name + ", fruit_code=" + fruit_code + ", sales_date=" + sales_date
				+ ", sales_quantity=" + sales_quantity + ", total=" + total + "]";
	}
}
