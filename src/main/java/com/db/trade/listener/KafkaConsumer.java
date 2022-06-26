package com.db.trade.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.db.trade.exception.BusinessException;
import com.db.trade.exception.SystemException;
import com.db.trade.request.TradeDto;
import com.db.trade.service.TradeService;


@Service
public class KafkaConsumer {
	
	@Autowired
	@Qualifier("tradeServiceImpl")
	private TradeService tradeService;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group}")
    public void execute(TradeDto tradeDto, Acknowledgment acknowledgment, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offsets) {
        System.out.println("Consumed JSON Message: " + tradeDto);
        try {
        	logger.error("Processing start for trade {}, partitioId {}, offset {}", tradeDto.getTradeId(), partition, offsets);
        tradeService.execute(tradeDto);
        }catch(BusinessException be) {
        	acknowledgment.acknowledge();
        } catch(SystemException se) {
        	logger.error("System Exception retrying trade {}", tradeDto.getTradeId());
        } catch (Exception e) {
        	logger.error("Unkown Exception retrying trade {}", tradeDto.getTradeId());
        }
        acknowledgment.acknowledge();
    }
}
