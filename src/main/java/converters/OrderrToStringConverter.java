package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Orderr;

@Component
@Transactional
public class OrderrToStringConverter implements Converter<Orderr, String>{
	@Override
	public String convert(Orderr arg0) {
		String result;
		if(arg0 == null){
			result = null;
		}else{
			result = String.valueOf(arg0.getId());
		}
		return result;
	}
}
