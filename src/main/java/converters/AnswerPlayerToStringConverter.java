package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AnswerPlayer;

@Component
@Transactional
public class AnswerPlayerToStringConverter implements Converter<AnswerPlayer, String>{

	@Override
	public String convert(AnswerPlayer arg0) {
		String result;
		if(arg0 == null){
			result = null;
		}else{
			result = String.valueOf(arg0.getId());
		}
		return result;
	}	
}
