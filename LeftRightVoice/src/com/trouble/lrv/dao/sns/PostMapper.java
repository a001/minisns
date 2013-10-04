package com.trouble.lrv.dao.sns;

import java.util.List;

import com.trouble.lrv.domain.sns.PostLike;
import com.trouble.lrv.domain.sns.Posting;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public interface PostMapper extends SnsMapper<Posting>{
	List<PostLike> getPostLike(int postId);
}
