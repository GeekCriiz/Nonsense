package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.QuestionRepository;
import domain.Question;

@Component
@Transactional
public class StringToQuestionConverter implements Converter<String, Question>{

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public Question convert(String arg0) {
		Question result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = questionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
