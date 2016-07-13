package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RoundRepository;
import domain.Round;

@Component
@Transactional
public class StringToRoundConverter implements Converter<String, Round>{

	@Autowired
	RoundRepository roundRepository;

	@Override
	public Round convert(String arg0) {
		Round result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = roundRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
