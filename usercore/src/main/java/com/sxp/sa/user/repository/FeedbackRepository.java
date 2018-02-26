package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.Feedback;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends PagingAndSortingRepository<Feedback,Long>,JpaSpecificationExecutor<Feedback> {
}
