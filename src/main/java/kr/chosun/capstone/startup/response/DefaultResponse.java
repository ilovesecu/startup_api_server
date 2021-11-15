package kr.chosun.capstone.startup.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
@Data
/*
ResponseEntity의 진짜 상태코드는 DefaultRes의 status에 넣어준다.
모든 응답메세지에 한국말로 넣어준다.
응답 데이터가 있다면 제네릭 타입을 이용해서 데이터에 넣어준다.
 */
public class DefaultResponse<T> {
	private int statusCode;
	private String responseMessage;
	private T data;
	
	//응답할 데이터가 없는 경우 해당 생성자를 사용한다.
	public DefaultResponse(int statusCode, String responseMessage){
		this.statusCode=statusCode;
		this.responseMessage=responseMessage;
		this.data=null;
	}
	
	public static<T> DefaultResponse<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> DefaultResponse<T> res(final int statusCode, final String responseMessage, final T t) {
        return DefaultResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
}
