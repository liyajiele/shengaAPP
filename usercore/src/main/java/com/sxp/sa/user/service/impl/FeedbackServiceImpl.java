package com.sxp.sa.user.service.impl;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.user.entity.Feedback;
import com.sxp.sa.user.repository.FeedbackRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.service.FeedbackService;
import com.sxp.sa.user.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;

@Service
public class FeedbackServiceImpl extends BaseService implements FeedbackService{

    @Autowired
    private UserRepository userDao;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackVo addFeedback(Long uid, Integer type, String typeDesc, String content, String images) throws BusinessException {

        Feedback feedback = new Feedback();
        feedback.setType(type);
        feedback.setTypeDesc(typeDesc);
        feedback.setContent(content);
        feedback.setImages(images);

        if(isNotEmpty(uid)){
            feedback.setUser(userDao.findByIdAndStatus(uid,valid));
        }
        feedback = feedbackRepository.save(feedback);
        return BeanMapper.map(feedback,FeedbackVo.class);
    }
}
