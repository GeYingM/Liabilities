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
	 * 用1000个线程分别计算当月公司给1000000个员工发出的工资，即当月公司的负债
	 * @path 保存员工信息的文件路径
	 * @JSONArray json数组
	 * @results 用来存放每个线程的计算返回结果的列表
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
		//初始化总的负债额
		double totalLiabilities=0;
		//从指定文件路径读取文件
	    File file = new File(path);
	    BufferedReader reader = null;
	    String json = "";
	    try {
	    /* System.out.println("以行为单位读取文件内容，一次读一整行：");*/
	     reader = new BufferedReader(new FileReader(file));
	     int line = 1;
	     String tempString = null;
	     //一次读入一行，直到读入null为文件结束
	     while ((tempString = reader.readLine()) != null) {
	      json = json +tempString;
	      //以1000为单位，将要计算的大概1000000个员工工资划分给1000个进程来处理
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
	     //当最后一个线程处理的员工数不足1000时，以将文件读完为准
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
	    //将所有线程的返回值相加，即得到最终结果，本月公司的总负债
	    for(FutureTask<Double>result:results){
	    	try{
	    	totalLiabilities+=result.get();
	    	}catch(ExecutionException e){
	    		e.printStackTrace();
	    	}
	    }
	    System.out.println("公司本月的总负债为："+totalLiabilities);
		
	}


	
}
