package cleancall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import cleancall.external.Cleanmanage;
import cleancall.external.CleanmanageService;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Cleancall_table")
public class Cleancall {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String tel;
    private String 호출위치;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer 예상요금;
    
	
    @PostPersist
    public void onPostPersist(){
//        Cleancalled Cleancalled = new Cleancalled();
//        BeanUtils.copyProperties(this, Cleancalled);
//        Cleancalled.publishAfterCommit();
    	
    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("호출위치 " + get호출위치());
        System.out.println("호출상태 " + getStatus());
        System.out.println("예상요금 " + get예상요금());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Cleanmanage cleanmanage = new Cleanmanage();
			cleanmanage.setId(getId());
			cleanmanage.setOrderId(String.valueOf(getId()));
			cleanmanage.set고객휴대폰번호(getTel());
	        if(get호출위치()!=null)
				cleanmanage.set호출위치(get호출위치());
	        if(getStatus()!=null)
				cleanmanage.set호출상태(getStatus());
	        if(get예상요금()!=null)
				cleanmanage.set예상요금(get예상요금());
	        
	        // mappings goes here
	        CleancalllApplication.applicationContext.getBean(CleanmanageService.class).cleanManageCall(cleanmanage);
		}

    }

	@PreRemove
	public void onPreRemove(){
		CleancallCancelled 호출취소됨 = new CleancallCancelled();
		BeanUtils.copyProperties(this, 호출취소됨);
		호출취소됨.publishAfterCommit();

		//Following code causes dependency to external APIs
		// it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

		//Cleanmanage Cleanmanage = new Cleanmanage();
		// mappings goes here
		//Cleanmanage.setId(getId());
		//Cleanmanage.setOrderId(String.valueOf(getId()));
		//Cleanmanage.set호출상태("호출취소");
		//Cleanmanage.set고객휴대폰번호(get휴대폰번호());
		
		// mappings goes here
		//CleancalllApplication.applicationContext.getBean(CleanmanageService.class).택시할당요청(Cleanmanage);
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getTel() {
		return tel;
	}


	public void setTel( String tel ) {
		this.tel = tel;
	}


	public String get호출위치() {
		return 호출위치;
	}


	public void set호출위치(String 호출위치) {
		this.호출위치 = 호출위치;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus( String status ) {
		this.status = status;
	}


	public Integer get예상요금() {
		return 예상요금;
	}


	public void set예상요금(Integer 예상요금) {
		this.예상요금 = 예상요금;
	}


}
