package jdbc_pool_study.exception;

@SuppressWarnings("serial")
public class NotItemSelectedException extends RuntimeException {

	public NotItemSelectedException() {
		super("항목을 선택하세요");
	}

}
