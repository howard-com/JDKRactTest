import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class BackPressureTest {

	public static void main(String[] args) {

		SubmissionPublisher<String> publisher = new SubmissionPublisher<String>() {
			@Override
		    public int submit(String item) {
				System.out.println(">>>发布数据-" + item);
				return super.submit(item);
		    }
		};

		Subscriber<String> subscriber = new Subscriber<String>() {
			private Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				this.subscription = subscription;
				subscription.request(1);
			}

			@Override
			public void onNext(String item) {
				System.out.println("<<<处理数据-" + item);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				subscription.request(10);
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println(throwable);

			}

			@Override
			public void onComplete() {
				System.out.println("处理完毕！");
			}
		};

		publisher.subscribe(subscriber);

		for (int i = 0; i <= 1000; i++) {
			publisher.submit(Integer.toString(i));
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
