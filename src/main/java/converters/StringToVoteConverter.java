package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Vote;
import repositories.VoteRepository;

@Component
@Transactional
public class StringToVoteConverter implements Converter<String, Vote>{

	@Autowired
	VoteRepository voteRepository;

	@Override
	public Vote convert(String arg0) {
		Vote result;
		int id;
		
		try {
			if(StringUtils.isEmpty(arg0)){
				result = null;
			}else{
				id = Integer.valueOf(arg0);
				result = voteRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException();
		}		
		return result;
	}

}
