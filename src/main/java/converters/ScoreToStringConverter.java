package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Score;

@Component
@Transactional
public class ScoreToStringConverter implements Converter<Score, String>{
	@Override
	public String convert(Score arg0) {
		String result;
		if(arg0 == null){
			result = null;
		}else{
			result = String.valueOf(arg0.getId());
		}
		return result;
	}
}
