package cleancall.external;

import org.springframework.stereotype.Component;

@Component
public class CleanmanageServiceFallback implements CleanmanageService {
	 
	//@Override
	//public void 택시할당요청(Cleanmanage Cleanmanage)
	//{	
	//	System.out.println("Circuit breaker has been opened. Fallback returned instead.");
	//}
	
	
	@Override
	public void cleanManageCall( Cleanmanage cleanmanage) {
		// TODO Auto-generated method stub
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + cleanmanage.getId());
	}

}
