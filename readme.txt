����text���еĳ�����һ��employeesc.json��������1000000��һģһ����employee����Ϣ���ڲ��ԣ��ܸ�ծӦΪ5000*��1+0.2��*1000000=6.0E9������Խ�������������ֻ��Ҫ��src/default package/TotalLiabilitiesThread.java�ļ��������Ϳ����ˡ�
���⣬��ҵ���Ѿ���Ҫ��ɾȥcommonList�࣬��ͨ������callable����ķ�ʽʵ�ֶ��̡߳��޸�֮�������Ѿ��ܴ���1000000��Ա�����ݣ���׼ȷ��������¹�˾��ծ������û���ڸ���ҵ��ʹ��lambda���ʽ��ԭ������������ͼ��futuretask��callable���а�װ���Ƿ����java��Ŀ��д����������䣿�ǲ���̫�����ˣ�
 FutureTask<Double>task=new FutureTask<>((Callable<Double>)()->{
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
		  	    	
		  	    });