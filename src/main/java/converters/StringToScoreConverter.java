package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ScoreRepository;
import domain.Score;

@Component
@Transactional
public class StringToScoreConverter implements Converter<String, Score>{

	@Autowired
	ScoreRepository scoreRepository;

	@Override
	public Score convert(String arg0) {
		Score result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = scoreRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
