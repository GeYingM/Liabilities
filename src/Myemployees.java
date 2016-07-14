import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import net.sf.json.JSONArray;

public class Myemployees implements Callable<Double> {
	/**
	 * ��1000���̷ֱ߳���㵱�¹�˾��1000000��Ա�������Ĺ��ʣ������¹�˾�ĸ�ծ
	 * @path ����Ա����Ϣ���ļ�·��
	 * @JSONArray json����
	 * @results �������ÿ���̵߳ļ��㷵�ؽ�����б�
	 */
	static private String path="src/employeesc.json";
	
	static JSONArray jsonArray=null;
	static List<FutureTask<Double>>results=new ArrayList<>(); 
	
	@Override
	public Double call() throws Exception {
		double liabilities=0;
  		@SuppressWarnings("rawtypes")
  		Collection con=JSONArray.toCollection(jsonArray,Employee.class);
  		Object[] stt = con.toArray();
		for(Object object:stt){
			Employee employee=(Employee)object;
			liabilities+=employee.getMonthSalary()*(1+employee.getMonthSalaryRaise());
			System.out.println(Thread.currentThread().getName()+" "+employee.getFirstName()+" "+liabilities);
	  	    }
		return liabilities;
	}
	
	public static void main(String[] args) throws InterruptedException{
		//��ʼ���ܵĸ�ծ��
		double totalLiabilities=0;
		//��ָ���ļ�·����ȡ�ļ�
	    File file = new File(path);
	    BufferedReader reader = null;
	    String json = "";
	    try {
	    /* System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");*/
	     reader = new BufferedReader(new FileReader(file));
	     int line = 1;
	     String tempString = null;
	     //һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	     while ((tempString = reader.readLine()) != null) {
	      json = json +tempString;
	      //��1000Ϊ��λ����Ҫ����Ĵ��1000000��Ա�����ʻ��ָ�1000������������
	      if(line%1000==0){
	    	json="["+json+"]";
	    	jsonArray=JSONArray.fromObject(json);
	  	    Myemployees myemployees=new Myemployees();
	  	    FutureTask<Double>task=new FutureTask<>(myemployees);
	  	    results.add(task);
	  	    Thread t=new Thread(task);
	  	    t.start();
	  	    json="";
	      }
	      line++ ;
	     }
	     //�����һ���̴߳����Ա��������1000ʱ���Խ��ļ�����Ϊ׼
	     if(json!=""){
		    	json="["+json+"]";
		    	jsonArray=JSONArray.fromObject(json);
		  	    Myemployees myemployees=new Myemployees();
		  	    FutureTask<Double>task=new FutureTask<>(myemployees);
		  	    results.add(task);
		  	    Thread t=new Thread(task);
		  	    t.start();
		      }
	     
	     reader.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } catch (IOException e1) {
	      }
	     }
	    }
	    //�������̵߳ķ���ֵ��ӣ����õ����ս�������¹�˾���ܸ�ծ
	    for(FutureTask<Double>result:results){
	    	try{
	    	totalLiabilities+=result.get();
	    	}catch(ExecutionException e){
	    		e.printStackTrace();
	    	}
	    }
	    System.out.println("��˾���µ��ܸ�ծΪ��"+totalLiabilities);
		
	}


	
}
