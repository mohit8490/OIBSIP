package com.oibsip.library.config;

import com.oibsip.library.service.FineService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FineScheduler {

    private final FineService fineService;

    public FineScheduler(
            FineService fineService) {

        this.fineService = fineService;
    }


    // =====================================================
    // GENERATE OVERDUE FINES EVERY MINUTE
    // =====================================================

    @Scheduled(cron = "0 * * * * *")
    public void generateOverdueFines() {

        fineService.generateOverdueFines();
    }
}