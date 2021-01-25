import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

public class FlowTest {

	public static void main(String[] args) throws InterruptedException {
		// 1.�����Զ��崴��������
		MyPublisher<MyBean> publiser = new MyPublisher<MyBean>();

		// 2.�����Զ���Ĵ�������
		MyProcessor processor = new MyProcessor();

		// 3.�����Զ���Ķ�����
		MySubscriber<String> subscriber = new MySubscriber();

		// 4.��������ע�ᵽ�������ϣ��ٽ�������ע�ᵽ�������ϡ�
		// �������൱��һ���м��ˡ���Ȼ�����߿���ֱ��ע�ᵽ�������ϡ����账������
		processor.subscribe(subscriber);
		publiser.subscribe(processor);
//      publiser.subscribe(subscriber);

		// 6. ��������, ������
		String[] input = { "A", "B", "C", "x", "y", "z" };
		ArrayList<MyBean> beanList = new ArrayList();

		for (String i : input) {
			MyBean bean = new MyBean();
			bean.setName(i);
			beanList.add(bean);
			publiser.submit(bean);
		}

		// 7. ������ �رշ�����
		publiser.close();
		// ���߳��ӳ�ֹͣ, ��������û�����Ѿ��˳�
		Thread.currentThread().join(1000);

		for (int i = 0; i < input.length; i++) {
			System.out.println(beanList.get(i).getName());
		}
	}
}
