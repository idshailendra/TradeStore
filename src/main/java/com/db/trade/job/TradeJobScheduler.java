package com.db.trade.job;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.db.trade.constant.ApplicationConstant;
import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;

@Component
public class TradeJobScheduler {
	
	@Autowired
	private TradeDao tradeDetailsRepository;

	private static final Logger logger = LoggerFactory.getLogger(TradeJobScheduler.class);

	@Scheduled(cron = "0 10 0 * * *")
	public void expireMaturedTradesSchedules() {
		logger.info("Expire Matured Trades Scheduler Started");
		List<Trade> tradeDetails = tradeDetailsRepository
				.findByExpiredAndMaturityDateLessThan(ApplicationConstant.TRADE_DETAILS_EXPIRED_N, LocalDate.now());

		if (null != tradeDetails && tradeDetails.size() != 0) {
			tradeDetails = tradeDetails.stream().map(s -> new Trade(s, ApplicationConstant.TRADE_DETAILS_EXPIRED_Y))
					.collect(Collectors.toList());

			tradeDetailsRepository.saveAll(tradeDetails);
			logger.info("Total Trades Expired - {}", tradeDetails.size());
		}
		logger.info("Expire Matured Trades Scheduler Completed");
	}

}
