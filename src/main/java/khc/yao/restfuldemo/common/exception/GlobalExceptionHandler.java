package khc.yao.restfuldemo.common.exception;

import khc.yao.restfuldemo.common.dto.BasicResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 接受所有沒特別聲明處理的例外
    @ExceptionHandler(Exception.class)
    public BasicResponseDTO any(Exception e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        log.error("Global Error", e);
        return responseDTO;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BasicResponseDTO any(HttpRequestMethodNotSupportedException e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        responseDTO.setMessage("呼叫的 API 不存在，請確認是否少輸入路徑參數。");
        log.info("呼叫的 API 不存在，請確認是否少輸入路徑參數。");
        return responseDTO;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BasicResponseDTO any(HttpMessageNotReadableException e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        responseDTO.setMessage("參數格式錯誤。");
        log.info("參數格式錯誤。");
        return responseDTO;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResponseDTO any(MethodArgumentNotValidException e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuffer message = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            message.append(fieldError.getDefaultMessage());
        }
        responseDTO.setMessage(message.toString());
        log.info(e.getMessage());
        return responseDTO;
    }

    // ISBN 無效
    @ExceptionHandler(ISBNNotVaildException.class)
    public BasicResponseDTO any(ISBNNotVaildException e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        responseDTO.setMessage("無效的 ISBN。");
        log.info("無效的 ISBN。");
        return responseDTO;
    }
}
