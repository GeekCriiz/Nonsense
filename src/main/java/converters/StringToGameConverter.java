package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.GameRepository;
import domain.Game;

@Component
@Transactional
public class StringToGameConverter implements Converter<String, Game>{

	@Autowired
	GameRepository gameRepository;

	@Override
	public Game convert(String arg0) {
		Game result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = gameRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
