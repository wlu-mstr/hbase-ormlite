package test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class st{
	String name;
	String age;
	
	public st(){
		name = "jack";
		age = "234";
	}
}
public class testFields {
	private String s1;
	private int i1;
	private float f1;
	private double d1;
	private st st1;
	
	private List<String> sl;
	
	
	

	public String getS1() {
		return s1;
	}



	public void setS1(String s1) {
		this.s1 = s1;
	}



	public int getI1() {
		return i1;
	}



	public void setI1(int i1) {
		this.i1 = i1;
	}



	public float getF1() {
		return f1;
	}



	public void setF1(float f1) {
		this.f1 = f1;
	}



	public double getD1() {
		return d1;
	}



	public void setD1(double d1) {
		this.d1 = d1;
	}



	public st getSt1() {
		return st1;
	}



	public void setSt1(st st1) {
		this.st1 = st1;
	}



	public List<String> getSl() {
		return sl;
	}



	public void setSl(List<String> sl) {
		this.sl = sl;
	}



	public testFields(String s1, int i1, float f1, double d1) {
		super();
		this.s1 = s1;
		this.i1 = i1;
		this.f1 = f1;
		this.d1 = d1;
		this.st1 = new st();
		sl = new ArrayList<String>();
		sl.add("asdfasdfa");
	}



	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Class<testFields> clazz = testFields.class;
		Map<Field, Integer> m = new HashMap<Field, Integer>();
		for(Field f : clazz.getDeclaredFields()){
			m.put(f, f.hashCode());
		}
		
		System.out.println(m.size());
		
		for(Field f : clazz.getDeclaredFields()){
			m.put(f, f.hashCode());
		}
		
		System.out.println(m.size());
		
		
		testFields tf = new testFields("abc", 1, 1.0f, 2d);
		
		for(Field f : clazz.getDeclaredFields()){
			Object o = f.get(tf);
			System.out.println(o);
			System.out.println(f.getType().equals(List.class));
		}
		
		System.out.println(testFields.class.getSimpleName());
	}

}
