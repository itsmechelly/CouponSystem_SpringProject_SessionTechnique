package com.couponsystem.schedule;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couponsystem.repo.CouponRepository;

@Service
@Transactional
public class CouponExpirationDailyJobService {

	private CouponRepository couponRepository;

	@Autowired
	public CouponExpirationDailyJobService(CouponRepository couponRepository) {
		super();
		this.couponRepository = couponRepository;
	}

	public int deleteExpiredCoupons(LocalDate now) {
		return couponRepository.deleteAllByEndDateBefore(now);
	}
}
