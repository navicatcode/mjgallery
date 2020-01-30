package com.mjgallery.mjgallery.event;

import com.mjgallery.mjgallery.MarginLottery;

import java.util.List;

public class SocketDataEvent {
    List<MarginLottery.LotteryNumber> lotteryNumbers;

    public SocketDataEvent(List<MarginLottery.LotteryNumber> lotteryNumbers) {
        this.lotteryNumbers = lotteryNumbers;
    }

    public List<MarginLottery.LotteryNumber> getLotteryNumbers() {
        return lotteryNumbers;
    }

    public void setLotteryNumbers(List<MarginLottery.LotteryNumber> lotteryNumbers) {
        this.lotteryNumbers = lotteryNumbers;
    }
}
