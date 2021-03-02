package cleanmanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import cleanmanage.config.kafka.KafkaProcessor;

@Service
public class CleanmanagePolicyHandler {
	@Autowired
	CleanmanageRepository 택시관리Repository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload CleancallCancelled 호출취소됨) {
		System.out.println("##### EVT TYPE[CleancallCancelled]  : " + 호출취소됨.getEventType());
		if (호출취소됨.isMe()) {
			System.out.println("##### listener  : " + 호출취소됨.toJson());

			if (호출취소됨.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				택시관리Repository.findById(Long.valueOf(호출취소됨.getId())).ifPresent((택시관리) -> {
					택시관리.set호출상태("호출요청취소됨");
					택시관리Repository.save(택시관리);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload CleanmanageAssigned 택시할당요청됨) {
		System.out.println("##### EVT TYPE[CleanmanageAssigned]  : " + 택시할당요청됨.getEventType());
		if (택시할당요청됨.isMe()) {
			System.out.println("##### listener[CleanassignCompleted]  : " + 택시할당요청됨.toJson());

			if (택시할당요청됨.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				택시관리Repository.findById(Long.valueOf(택시할당요청됨.getId())).ifPresent((택시관리) -> {
					택시관리.set호출상태(택시할당요청됨.get호출상태());
					택시관리Repository.save(택시관리);
				});

//        	CleanmanageRepository.findBy고객휴대폰번호(CleanmanageAssigned.get고객휴대폰번호()).ifPresent((Cleanmanage) -> {
//				System.out.println("CleanmanageAssigned = " + Cleanmanage.get고객휴대폰번호());
//				Cleanmanage.set호출상태(CleanmanageAssigned.get호출상태());
//				CleanmanageRepository.save(Cleanmanage);
//			});
//            Cleanmanage 관리 = new Cleanmanage();
//            관리.set호출상태(CleanassignCompleted.get호출상태());
//            관리.set택시기사이름(CleanassignCompleted.get택시기사이름());
//            관리.set택시기사전화번호(CleanassignCompleted.get택시기사전화번호());
//            관리.set택시번호(CleanassignCompleted.get택시번호());
//            CleanmanageRepository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever택시할당확인됨_(@Payload CleanassignCompleted CleanassignCompleted){
//    	System.out.println("##### EVT TYPE[CleanassignCompleted]  : " + CleanassignCompleted.getEventType());
//        if(CleanassignCompleted.isMe()){
//            System.out.println("##### listener  : " + CleanassignCompleted.toJson());
//            Cleanmanage 관리 = new Cleanmanage();
//            관리.set호출상태(CleanassignCompleted.get할당상태());
//            관리.set택시기사이름(CleanassignCompleted.get택시기사이름());
//            관리.set택시기사전화번호(CleanassignCompleted.get택시기사전화번호());
//            관리.set택시번호(CleanassignCompleted.get택시번호());
//            CleanmanageRepository.save(관리);
//        }
//    }

}
