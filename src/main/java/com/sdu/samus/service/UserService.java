package com.sdu.samus.service;

import com.sdu.samus.dao.*;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.*;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.HobbyVO;
import com.sdu.samus.vo.UserLoginVO;
import com.sdu.samus.vo.UserUpdateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRelationshipDao userRelationshipDao;
	@Autowired
	private HobbyDao hobbyDao;
	@Autowired
	private ArtDao artDao;
	@Autowired
	private CartoonDao cartoonDao;
	@Autowired
	private FoodDao foodDao;
	@Autowired
	private GameDao gameDao;
	@Autowired
	private IdolDao idolDao;
	@Autowired
	private MovieDao movieDao;
	@Autowired
	private MusicDao musicDao;
	@Autowired
	private ReadingDao readingDao;
	@Autowired
	private SportDao sportDao;
	@Autowired
	private TravelDao travelDao;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserInfo getUserInfo(String id) throws ParameterException ,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty((id))){
			pe.addError(ResultCode.USERID_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}
		UserInfo user =  userDao.findUserInfoById(id);
		if(user == null){
			throw new ServiceException(ResultCode.ACCOUNT_NO_REGISTERD);
		}
		return user;
	}

	/**
	 * 登录
	 * @param user
	 * @return
	 * @throws ParameterException
	 */
	public UserInfo login(UserLoginVO user) throws ParameterException {
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(user.getUserId())){
			logger.info("UserService --- [user.getUserId]     :"+user.getUserId());
			pe.addError(ResultCode.USERNAME_EMPTY);
		}
		if(StringUtil.isEmpty(user.getPassword())){
			logger.info("UserService --- [user.getPassword]     :"+user.getPassword());
			pe.addError(ResultCode.PASSWORD_EMPTY);
		}
		if(pe.getErrors().size() == 2){
			pe.clearErrors();
			pe.addError(ResultCode.USERNAME_PASSWORD_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		UserInfo userinfo = userDao.findUserInfoById(user.getUserId());

		//判断用户是否注册
		if(userinfo == null){
			throw new ServiceException(ResultCode.ACCOUNT_NO_REGISTERD);
		}
		//判断密码是否正确
		if(!user.getPassword().equals(userinfo.getPassword())){
			throw new ServiceException(ResultCode.PASSWORD_WRONG);
		}

		return userinfo;
	}

	/**
	 * 注册
	 * @param schoolid5
	 * @param xuehao
	 * @param password
	 * @return
	 * @throws ParameterException
	 */
	public int register(String schoolid5,String xuehao,byte gender,String password) throws ParameterException,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(xuehao)){
			logger.info("UserService --- [xuehao]     :"+xuehao);
			pe.addError(ResultCode.XUEHAO_EMPTY);
		}
		if(StringUtil.isEmpty(password)){
			logger.info("UserService --- [password]     :"+password);
			pe.addError(ResultCode.PASSWORD_EMPTY);
		}
		if(pe.getErrors().size() == 2){
			pe.clearErrors();
			pe.addError(ResultCode.XUEHAO_PASSWORD_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		//新建UserInfo对象
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(schoolid5+xuehao);
		userInfo.setPassword(password);
		userInfo.setGender(gender);
		userInfo.setSchoolid5(schoolid5);
		userInfo.setNickname(StringUtil.generateRandom(5));

		int result = userDao.registerUser(userInfo);

		if(result == 0){
			throw new ServiceException(ResultCode.REGISTER_WRONG);
		}
		return  result;
	}

	public int updateUserInfo(UserUpdateVO user,String userId) throws ParameterException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(user.getNickname())){
			logger.info("UserService --- [Nickname]     :"+user.getNickname());
			pe.addError(ResultCode.USERNAME_EMPTY);
		}
		if(StringUtil.isEmpty(user.getPassword())){
			logger.info("UserService --- [password]     :"+user.getPassword());
			pe.addError(ResultCode.PASSWORD_EMPTY);
		}
		if(pe.getErrors().size() == 2){
			pe.clearErrors();
			pe.addError(ResultCode.NICKNAME_PASSWORD_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		//新建UserInfo对象
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userId);
		userInfo.setPassword(user.getPassword());
		userInfo.setGender(user.getGender());
		userInfo.setAge(user.getAge());

		String avatar =user.getAvatar();
		// 去掉base64数据头部data:image/png;base64,和尾部的” " “
		String[] ww= avatar.split(",");
		avatar = ww[1];
		String[] aa = avatar.split("\"");
		avatar = aa[0];
		//base64字符串解码为byte数组
		BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			byte[] bytes1 = decoder.decodeBuffer(avatar);
			userInfo.setAvatar(bytes1);
		} catch (IOException e) {
			throw new ServiceException(ResultCode.UPDATE_ERROR);
		}

		userInfo.setIntro(user.getIntro());
		userInfo.setNickname(user.getNickname());
		userInfo.setPhone(user.getPhone());
		logger.info("转化byte数组后的头像"+userInfo.getAvatar());
		int result = userDao.updateUser(userInfo);
		if(result == 0){
			throw new ServiceException(ResultCode.UPDATE_ERROR);
		}
		return  result;
	}

	public int updateHobby(HobbyVO hobbyVO,String userId)throws ParameterException ,ServiceException{
		UserInfo user = this.getUserInfo(userId);
		String featureVector = user.getFeaturevector();

		if(!StringUtil.isEmpty(hobbyVO.getArt())){
			//---------------------------------------------------------------------------------------------------Art------------
			//判断userInfo表的art字段是否为空，如果为空，hobby表的art的count+1，不为空则hobby表不变
			if(!StringUtil.isEmpty(user.getArt())){
				//在art表中以前用户的art二级标签 count-1
				for(String s : StringUtil.split(user.getArt(),";")){
					artDao.deUpdate(s);
				}
			}else{//userInfo表的art字段为空
				hobbyDao.hobbyUpdate("art");
			}

			//查看art表中是否有用户提交的兴趣，如果没有则新建，有则count+1
			for(String s : StringUtil.split(hobbyVO.getArt(),";")){
				if(artDao.hasArt(s) > 0){//如果有这个二级标签
					artDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Art art = new Art();
					art.setArtname(s);
					art.setArtcount(1);
					artDao.insertArt(art);
				}
			}

			//更新userInfo表
			String newFeature = "1"+featureVector.substring(1);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setArt(hobbyVO.getArt());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//----------------------------------------------------------------------------------------------------Cartoon-----------
		}else if(!StringUtil.isEmpty(hobbyVO.getCartoon())){
			if(!StringUtil.isEmpty(user.getCartoon())){
				for(String s : StringUtil.split(user.getCartoon(),";")){
					cartoonDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("cartoon");
			}

			for(String s : StringUtil.split(hobbyVO.getCartoon(),";")){
				if(cartoonDao.hasCartoon(s) > 0){//如果有这个二级标签
					cartoonDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Cartoon cartoon = new Cartoon();
					cartoon.setCartoonname(s);
					cartoon.setCartooncount(1);
					cartoonDao.insertCartoon(cartoon);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,1)+"1"+featureVector.substring(2);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setCartoon(hobbyVO.getCartoon());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Food----------
		}else if(!StringUtil.isEmpty(hobbyVO.getFood())){
			if(!StringUtil.isEmpty(user.getFood())){
				for(String s : StringUtil.split(user.getFood(),";")){
					foodDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("food");
			}

			for(String s : StringUtil.split(hobbyVO.getFood(),";")){
				if(foodDao.hasFood(s) > 0){//如果有这个二级标签
					foodDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Food food = new Food();
					food.setFoodname(s);
					food.setFoodcount(1);
					foodDao.insertFood(food);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,2)+"1"+featureVector.substring(3);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setFood(hobbyVO.getFood());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);

			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);

			return res;
			//-----------------------------------------------------------------------------------------------------Game----------
		}else if(!StringUtil.isEmpty(hobbyVO.getGame())){
			if(!StringUtil.isEmpty(user.getGame())){
				for(String s : StringUtil.split(user.getGame(),";")){
					gameDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("game");
			}

			for(String s : StringUtil.split(hobbyVO.getGame(),";")){
				if(gameDao.hasGame(s) > 0){//如果有这个二级标签
					gameDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Game game = new Game();
					game.setGamename(s);
					game.setGamecount(1);
					gameDao.insertGame(game);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,3)+"1"+featureVector.substring(4);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setGame(hobbyVO.getGame());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Idol----------
		}else if(!StringUtil.isEmpty(hobbyVO.getIdol())){
			if(!StringUtil.isEmpty(user.getIdol())){
				for(String s : StringUtil.split(user.getIdol(),";")){
					idolDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("idol");
			}

			for(String s : StringUtil.split(hobbyVO.getIdol(),";")){
				if(idolDao.hasIdol(s) > 0){//如果有这个二级标签
					idolDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Idol idol = new Idol();
					idol.setIdolname(s);
					idol.setIdolcount(1);
					idolDao.insertIdol(idol);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,4)+"1"+featureVector.substring(5);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setIdol(hobbyVO.getIdol());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Movie----------
		}else if(!StringUtil.isEmpty(hobbyVO.getMovie())){
			if(!StringUtil.isEmpty(user.getMovie())){
				for(String s : StringUtil.split(user.getMovie(),";")){
					movieDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("movie");
			}

			for(String s : StringUtil.split(hobbyVO.getMovie(),";")){
				if(movieDao.hasMovie(s) > 0){//如果有这个二级标签
					movieDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Movie movie = new Movie();
					movie.setMoviename(s);
					movie.setMoviecount(1);
					movieDao.insertMovie(movie);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,5)+"1"+featureVector.substring(6);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setMovie(hobbyVO.getMovie());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Music----------
		}else if(!StringUtil.isEmpty(hobbyVO.getMusic())){
			if(!StringUtil.isEmpty(user.getMusic())){
				for(String s : StringUtil.split(user.getMusic(),";")){
					musicDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("music");
			}

			for(String s : StringUtil.split(hobbyVO.getMusic(),";")){
				if(musicDao.hasMusic(s) > 0){//如果有这个二级标签
					musicDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Music music = new Music();
					music.setMusicname(s);
					music.setMusiccount(1);
					musicDao.insertMusic(music);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,6)+"1"+featureVector.substring(7);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setMusic(hobbyVO.getMusic());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Reading----------
		}else if(!StringUtil.isEmpty(hobbyVO.getReading())){
			if(!StringUtil.isEmpty(user.getReading())){
				for(String s : StringUtil.split(user.getReading(),";")){
					readingDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("reading");
			}

			for(String s : StringUtil.split(hobbyVO.getReading(),";")){
				if(readingDao.hasReading(s) > 0){//如果有这个二级标签
					readingDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Reading reading = new Reading();
					reading.setReadingname(s);
					reading.setReadingcount(1);
					readingDao.insertReading(reading);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,7)+"1"+featureVector.substring(8);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setReading(hobbyVO.getReading());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Sport----------
		}else if(!StringUtil.isEmpty(hobbyVO.getSport())){
			if(!StringUtil.isEmpty(user.getSport())){
				for(String s : StringUtil.split(user.getSport(),";")){
					sportDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("sport");
			}

			for(String s : StringUtil.split(hobbyVO.getSport(),";")){
				if(sportDao.hasSport(s) > 0){//如果有这个二级标签
					sportDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Sport sport = new Sport();
					sport.setSportname(s);
					sport.setSportcount(1);
					sportDao.insertSport(sport);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,8)+"1"+featureVector.substring(9);
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setSport(hobbyVO.getSport());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//-----------------------------------------------------------------------------------------------------Travel----------
		}else if(!StringUtil.isEmpty(hobbyVO.getTravel())){
			if(!StringUtil.isEmpty(user.getTravel())){
				for(String s : StringUtil.split(user.getTravel(),";")){
					travelDao.deUpdate(s);
				}
			}else{
				hobbyDao.hobbyUpdate("travel");
			}

			for(String s : StringUtil.split(hobbyVO.getTravel(),";")){
				if(travelDao.hasTravel(s) > 0){//如果有这个二级标签
					travelDao.inUpdate(s);
				}else{//如果没有这个二级标签
					Travel travel = new Travel();
					travel.setTravelname(s);
					travel.setTravelcount(1);
					travelDao.insertTravel(travel);
				}
			}

			//更新userInfo表
			String newFeature = featureVector.substring(0,9)+"1";
			UserInfo u = new UserInfo();
			u.setUserid(user.getUserid());
			u.setTravel(hobbyVO.getTravel());
			u.setFeaturevector(newFeature);
			int res = userDao.updateUser(u);
			//更新userRelationship的featurevector
			this.updateUserRelationship(userId,newFeature);
			return res;
			//---------------------------------------------------------------------------------------------------------------------
		}
		return 0;
	}

	public void updateUserRelationship(String userId,String newFeature){
		UserRelationshipWithBLOBs userRelationship = new UserRelationshipWithBLOBs();
		userRelationship.setUserid(userId);
		userRelationship.setFeaturevector(newFeature);
		userRelationshipDao.update(userRelationship);
	}
}
