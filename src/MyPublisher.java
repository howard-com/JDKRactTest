import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class MyPublisher<MyBean> extends SubmissionPublisher<MyBean> {
	@Override
    public void subscribe(Subscriber subscriber) {
		// ע�ᶩ����
		System.out.println("������ע���µĶ�����");
		super.subscribe(subscriber);
	}	
}
