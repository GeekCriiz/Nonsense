package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Round;

@Component
@Transactional
public class RoundToStringConverter implements Converter<Round, String>{
	@Override
	public String convert(Round arg0) {
		String result;
		if(arg0 == null){
			result = null;
		}else{
			result = String.valueOf(arg0.getId());
		}
		return result;
	}
}
