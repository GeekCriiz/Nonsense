package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.OrderrRepository;
import domain.Orderr;

@Component
@Transactional
public class StringToOrderrConverter implements Converter<String, Orderr>{

	@Autowired
	OrderrRepository orderrRepository;

	@Override
	public Orderr convert(String arg0) {
		Orderr result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = orderrRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
