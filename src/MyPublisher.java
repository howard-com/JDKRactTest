import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscriber;

public class MyPublisher<MyBean> extends SubmissionPublisher<MyBean> {
	@Override
    public void subscribe(Subscriber subscriber) {
		// 注册订阅者
		System.out.println("发布者注册新的订阅者");
		super.subscribe(subscriber);
	}
}
