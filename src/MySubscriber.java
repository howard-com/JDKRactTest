import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber<T> implements Subscriber<MyBean> {
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("�����߱�ע�ᵽ��������");

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
		String res = (String) new StringBuffer("[").append(input).append("]").toString();
		System.out.println("�����ߴ�������: " + res);
		bean.setName(res);
		
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
		System.out.println("�����ߴ�������");
	}
}
