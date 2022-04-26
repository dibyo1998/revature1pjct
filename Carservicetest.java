package Carservice.car_m_service;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class Carservicetest {
	

	@Test
	public void multiplyTest() {
		//Admin m=new Admin();
		String act;
		
		try {
			act = Carserv.test();
			act=act.toLowerCase();
			//if(act.equals("admin")==true)
			assertEquals("admin",act);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
