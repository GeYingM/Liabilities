import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * ��employees.json�ļ���д��һ�����Ա������Ϣ�����ڲ���
 * @author Administrator
 *
 */
public class Test {
	public boolean setElect(String path,String sets){
		   try {
		    writeFile(path,sets);
		    return true;
		   } catch (IOException e) {
		    e.printStackTrace();
		    return false;
		   }
		}
	
	public void writeFile(String filePath, String sets) throws IOException {
	    int i=1;
		FileWriter fw = new FileWriter(filePath);
	    PrintWriter out = new PrintWriter(fw);
	    
	    for(;i<=1000000;i++){
	    	out.write(sets);
	 	    out.println();
	    }
	    
	    fw.close();
	    out.close();
	   }
	
	public static void main(String[] arg){
		
		Test test=new Test();
		test.setElect("src/employeesc.json","{firstName:'Brett',lastName:'Black',monthSalary:'5000',monthSalaryRaise:'0.2'},");
	}
}

