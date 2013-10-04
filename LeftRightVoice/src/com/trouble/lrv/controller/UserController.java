package com.trouble.lrv.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trouble.lrv.core.validator.FileCheckValidator;
import com.trouble.lrv.core.validator.UserExistCheckValidator;
import com.trouble.lrv.domain.enumset.PoliticsColor;
import com.trouble.lrv.domain.enumset.Roles;
import com.trouble.lrv.domain.user.SecurityUser;
import com.trouble.lrv.service.securityuser.IUserService;
import com.trouble.lrv.util.FileCheckUtil;

/**
 * 유저 등록, 권한추가, 친구추가.삭제 컨트롤러
 * @author Choi Bin
 * @since 1.0 beta
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = "user")
public class UserController {
	//사용자가 프로필 사진을 업로드 하지 않았을 때, 디폴트로 사용되는 이미지
	private static final String DEFAULT_FILENAME = "default_photo.jpg"; 
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	// 존재하는 유저인지 체크하는 validator
	private UserExistCheckValidator userExistCheckValidator;
	// 파일 확장자 및 용량 체크하는 validator
	private FileCheckValidator fileCheckValidator;
	// user 관련 처리 service
	private IUserService userService;
	//비밀번호 암호화
	private PasswordEncoder passwordEncoder;
	//암호화 된 비밀번호 에 간하기
	private SaltSource saltSource;
	//파일 업로드 경로 정보를 가지고 있는 리소스
	private FileSystemResource fileSystemResource;
	
	@Autowired
	public void init(UserExistCheckValidator validator, FileCheckValidator fileCheckValidator, IUserService userService, 
			PasswordEncoder encoder, SaltSource source, FileSystemResource fileSystemResource){
		this.userExistCheckValidator = validator;
		this.fileCheckValidator = fileCheckValidator;
		this.userService = userService;
		this.passwordEncoder = encoder;
		this.saltSource = source;
		this.fileSystemResource = fileSystemResource;
	}
	
	/**
	 * 이 컨트롤러로 호출된 페이지에서 허용하는(Allow) 필드 항목과
	 * 필수(Required) 필드 항목을 지정한다. 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.setAllowedFields("username", "password", "email", "politicsColor");
		binder.setRequiredFields("username", "password", "email", "politicsColor");
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void join(Model model){
		SecurityUser loginUser = new SecurityUser();
		
		model.addAttribute("user", loginUser);
	}
	
	//정치 성향 (진보 or 보수)
	@ModelAttribute
	public PoliticsColor[] politicsColor(){
		PoliticsColor[] politicsColor =PoliticsColor.values(); 
		return politicsColor;
	}
	
	/**
	 * 회원가입을 시도하였을 때 호출됨.
	 * 존재하는 유저 여부 체크.
	 * 사용자가 프로필 사진을 선택하였을 경우, 파일의 확장자가 지원되는것인지 , 파일 용량은 적절한지 체크.
	 *사용자의 권한을 insert하고 셋팅
	 *사용자가 입력한 암호를 암호화+소금 작업 하여 셋팅
	 *사용자가 프로필사진을 업로드하지 않았으면 디폴트 파일명. 업로드하였으면 업로드한 파일명을 셋팅
	 *셋팅한 유저 정보를 등록
	 *유저 기본 친구를 등록(자기 자신을 친구로 등록함)
	 *유저가 프로필 사진을 선택하였으면, 사진을 업로드 
	 * @param file 프로필사진
	 * @param user 등록할 user정보
	 * @param result validate 수행을 위한 result
	 * @param status 유저 정보를 저장한 session상태값
	 * @param ra 리다이렉트 하였을 경우 ModelAttribute로 인해 url 이 지저분해지는것을 방지
	 * @return
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST, consumes="multipart/form-data")
	public String submitJoin(@RequestPart(value="file", required=false) MultipartFile file, @ModelAttribute("user") @Valid SecurityUser user, BindingResult result, SessionStatus status, RedirectAttributes ra){
		this.userExistCheckValidator.validate(user, result);
		
		if(result.hasErrors()){
			return "/user/join"; 
		}
		
		if(!file.isEmpty()){
			this.fileCheckValidator.validate(file, result);
			
			if(result.hasErrors()){
				return "/user/join"; 
			}
		}
		
		user.setAuthorities(addAuthorities(user.getPoliticsColor()));
		user.setPassword(encodePassword(user));
		user.setEnabled(true);
		user.setUserPhoto(makeFileName(file.getOriginalFilename()));
		
		//유저 등록
		userService.createUser(user);
		//유저 기본 친구 등록 (자기 자신을 친구로 등록함)
		userService.createBasicFriend(user.getUsername());
		//유저 프로필 사진 업로드
		userPhotoUpload(file, user.getUserPhoto());
		status.setComplete();
		
		return "redirect:/";
		
	}
	
	/**
	 * 사용자가 프로필 사진을 선택하였을 경우, 업로드 처리
	 * @param file 파일
	 * @param fileName 파일명
	 */
	private void userPhotoUpload(MultipartFile file, String fileName) {
		if(!file.isEmpty()){
			try {
				file.transferTo(new File(this.fileSystemResource.getPath()+fileName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("{} upload success", file.getOriginalFilename());			
		}
	}

	/**
	 * 사용자가 파일을 선택하였다면 선택된 파일명, 선택하지 않았다면 디폴트 파일명을 리턴
	 * @param originalFilename File의 오리지널파일명
	 * @return 조건에 따른 파일명
	 */
	private String makeFileName(String originalFilename) {
		UUID uuid = UUID.randomUUID();
		String ext = FileCheckUtil.getExt(originalFilename);
		return !StringUtils.hasText(originalFilename) ? DEFAULT_FILENAME : uuid.toString()+"."+ext;
	}

	/**
	 * 패스워드 암호화 md5 + salt
	 * @param user
	 * @return
	 */
	private String encodePassword(SecurityUser user) {
		return passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(user));
	}
	
	/**
	 * 권한 추가 : 일반 user 이기 때문에 ROLE_USER, 정치성향에 따른 권한(ROLE_RIGHT(진보) OR ROLE_LEFT(보수)) 추가
	 * @param pColor
	 * @return
	 */
	private Collection<GrantedAuthority> addAuthorities(final int pColor) {
		Collection<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		
		authority.add(new GrantedAuthority() {
			private static final long serialVersionUID = 8144290082286813695L;
			
			@Override
			public String getAuthority() {
				return Roles.DEFAULT_ROLE.getName();
			}
		});
		
		authority.add(new GrantedAuthority() {
			private static final long serialVersionUID = 8144290082286813695L;
			
			@Override
			public String getAuthority() {
				return Roles.valueOf(pColor).getName();
			}
		});
		
		return authority;
	}
	
	/**
	 * FIXME 이 기능은 UserController 에 있을 기능이 아님.
	 * 이 컨트롤러의 이름은 UserRegistController 로 바뀌어야 마땅
	 * 전체 유저 목록
	 * @param userDetails
	 * @param model
	 */
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public void userlist(Principal userDetails, Model model){
		SecurityUser user = new SecurityUser();
		user.setFriendlist(userService.getFriend(userDetails.getName()));
		
		//현재 로그인한 user를 제외한 유저의 목록을 model 에 add한다.
		model.addAttribute("userlist", userService.userList(userDetails.getName()));
		//현재 로그인한 사용자의 친구목록을 셋팅한 SecurityUser를 model에 add한다. 
		model.addAttribute("user",user);
		
	}
	
	/**
	 * 친구등록 메서드.
	 * 1.친구 등록을 할때 자신의 모든 친구를 삭제하고,
	 * 2.자기자신을 친구로 등록하고,
	 * 3.친구로 추가 한 사람을 친구로 등록한다.
	 * FIXME 수정이 필요할 것으로 보임.
	 * @param userDetails 현재 로그인한 사용자 정보
	 * @param status 세션상태
	 * @param ra 리다이렉트어트리뷰트 - 페이지 리다이렉트가 일어날 때, ModelAttribute에 추가된 값때문에 url 이 지저분해지는 것을 방지하기 위함.
	 * @param friendlist 추가를 원하는 친구 아이디 리스트
	 * @return 메인페이지로 리다이렉트
	 */
	@RequestMapping(value = "/userlist", method = RequestMethod.POST)
	public String addFriend(Principal userDetails, SessionStatus status, RedirectAttributes ra, String... friendlist){
		userService.delFriend(userDetails.getName());
		userService.createBasicFriend(userDetails.getName());
		
		if(friendlist != null && friendlist.length != 0){
			userService.addFriend(userDetails.getName(), friendlist);
		}
		
		status.setComplete();
		return "redirect:/";		
	}
	
}
