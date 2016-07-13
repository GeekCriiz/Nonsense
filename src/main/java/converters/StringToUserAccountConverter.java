package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String, UserAccount>{

	@Autowired
	UserAccountRepository userAccountRepository;

	@Override
	public UserAccount convert(String arg0) {
		UserAccount result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = userAccountRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
