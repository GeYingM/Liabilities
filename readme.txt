我用text类中的程序建了一个employeesc.json，里面有1000000个一模一样的employee的信息用于测试，总负债应为5000*（1+0.2）*1000000=6.0E9，与测试结果相符。所以你只需要打开src/default package/TotalLiabilitiesThread.java文件点击编译就可以了。
另外，作业我已经按要求删去commonList类，并通过创建callable对象的方式实现多线程。修改之后现在已经能处理1000000的员工数据，并准确计算出当月公司负债。但我没有在该作业中使用lambda表达式。原因是我两次试图用futuretask对callable进行包装，是否就在java项目中写两次如下语句？是不是太繁琐了？
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