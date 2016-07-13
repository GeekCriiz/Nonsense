package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.AnswerPlayer;
import repositories.AnswerPlayerRepository;

@Component
@Transactional
public class StringToAnswerPlayerConverter implements Converter<String, AnswerPlayer>{

	@Autowired
	AnswerPlayerRepository answerPlayerRepository;

	@Override
	public AnswerPlayer convert(String arg0) {
		AnswerPlayer result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = answerPlayerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
