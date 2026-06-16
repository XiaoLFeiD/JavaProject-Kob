package com.kob.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer userId, Integer rating,Integer botid);
    String removePlayer(Integer userId);
}
