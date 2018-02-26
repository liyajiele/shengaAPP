package com.sxp.sa.user.service;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.vo.FeedbackVo;

public interface FeedbackService {

    FeedbackVo addFeedback(Long uid,Integer type,String typeDesc,String content,String images)throws BusinessException;
}
