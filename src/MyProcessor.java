import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class MyProcessor extends SubmissionPublisher<MyBean> implements Processor<MyBean, MyBean> {
	private Subscription subscription;
	
	@Override
	public void subscribe(Subscriber subscriber) {
		// ע�ᶩ����
		System.out.println("������ע���µĶ�����");
		super.subscribe(subscriber);
	}	

	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("��������ע�ᵽ��������");
		// ���涩�Ĺ�ϵ, ��Ҫ����������������Ӧ
		this.subscription = subscription;

		// ����һ������
		this.subscription.request(1);
	}

	@Override
	public void onNext(MyBean bean) {
		// ���ܵ�һ������, ����
		String input = bean.getName();
		System.out.println("���������ܵ�����: " + input);
		
		// ��Сдת��
		String res = input.toUpperCase();
		if (input == res) {
			res = input.toLowerCase();
		}
		
		bean.setName(res);
		
		// �Լ���Ϊ�����ߣ�������������
		this.submit(bean);

		// ���������request������һ������
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		// �������쳣(���紦�����ݵ�ʱ��������쳣)
		throwable.printStackTrace();

		// ���ǿ��Ը��߷�����, ���治����������
		this.subscription.cancel();
	}

	@Override
	public void onComplete() {
		// ȫ�����ݴ�������(�����߹ر���)
		System.out.println("��������������");
        // �رշ�����
        this.close();
	}
}
