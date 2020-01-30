package com.mjgallery.mjgallery.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CollectionBean implements MultiItemEntity {


    private int itemType;
    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
    /**
     * id : 708
     * userId : 722
     * relatedId : 44
     * pictureId : 3508665
     * pictureName : 管家婆
     * type : 1
     * filePath : http://192.168.0.212/files/system/big-pic/2014/col/102/805.jpg
     * year : 2014
     * terms : 102
     * createTime : 2019-11-01 19:43:58
     * subType : 2
     * content : <p>{
     * "code": 0,
     * "message": "获取开奖数据成功",
     * "result": [
     * {
     * "typeName": "特肖记录",
     * "lotteryData": [
     * {
     * "sx": "鼠肖",
     * "number": [
     * 12,
     * 24,
     * 36,
     * 48
     * ],
     * "year": 2019,
     * "latestPeriod": 89,
     * "continuousPeriodUnopened": 25,
     * "maxPeriodUnopened": {
     * "period": 74,
     * "beginPeriod": "1985050",
     * "endPeriod": "1986020"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "牛肖",
     * "number": [
     * 11,
     * 23,
     * 35,
     * 47
     * ],
     * "year": 2019,
     * "latestPeriod": 104,
     * "continuousPeriodUnopened": 10,
     * "maxPeriodUnopened": {
     * "period": 116,
     * "beginPeriod": "2011082",
     * "endPeriod": "2012043"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "虎肖",
     * "number": [
     * 10,
     * 22,
     * 34,
     * 46
     * ],
     * "year": 2019,
     * "latestPeriod": 101,
     * "continuousPeriodUnopened": 13,
     * "maxPeriodUnopened": {
     * "period": 62,
     * "beginPeriod": "1997033",
     * "endPeriod": "1997094"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "兔肖",
     * "number": [
     * 9,
     * 21,
     * 33,
     * 45
     * ],
     * "year": 2019,
     * "latestPeriod": 73,
     * "continuousPeriodUnopened": 41,
     * "maxPeriodUnopened": {
     * "period": 73,
     * "beginPeriod": "1999008",
     * "endPeriod": "1999080"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "龙肖",
     * "number": [
     * 8,
     * 20,
     * 32,
     * 44
     * ],
     * "year": 2019,
     * "latestPeriod": 114,
     * "continuousPeriodUnopened": 0,
     * "maxPeriodUnopened": {
     * "period": 67,
     * "beginPeriod": "1995018",
     * "endPeriod": "1995084"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 3,
     * "beginPeriod": "2017143",
     * "endPeriod": "2017145"
     * }
     * },
     * {
     * "sx": "蛇肖",
     * "number": [
     * 7,
     * 19,
     * 31,
     * 43
     * ],
     * "year": 2019,
     * "latestPeriod": 106,
     * "continuousPeriodUnopened": 8,
     * "maxPeriodUnopened": {
     * "period": 99,
     * "beginPeriod": "2016140",
     * "endPeriod": "2017087"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 9,
     * "beginPeriod": "2019105",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "马肖",
     * "number": [
     * 6,
     * 18,
     * 30,
     * 42
     * ],
     * "year": 2019,
     * "latestPeriod": 99,
     * "continuousPeriodUnopened": 15,
     * "maxPeriodUnopened": {
     * "period": 63,
     * "beginPeriod": "1977078",
     * "endPeriod": "1978038"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "羊肖",
     * "number": [
     * 5,
     * 17,
     * 29,
     * 41
     * ],
     * "year": 2019,
     * "latestPeriod": 100,
     * "continuousPeriodUnopened": 14,
     * "maxPeriodUnopened": {
     * "period": 66,
     * "beginPeriod": "1988092",
     * "endPeriod": "1989055"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "猴肖",
     * "number": [
     * 4,
     * 16,
     * 28,
     * 40
     * ],
     * "year": 2019,
     * "latestPeriod": 98,
     * "continuousPeriodUnopened": 16,
     * "maxPeriodUnopened": {
     * "period": 112,
     * "beginPeriod": "1983043",
     * "endPeriod": "1984051"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "鸡肖",
     * "number": [
     * 3,
     * 15,
     * 27,
     * 39
     * ],
     * "year": 2019,
     * "latestPeriod": 102,
     * "continuousPeriodUnopened": 12,
     * "maxPeriodUnopened": {
     * "period": 70,
     * "beginPeriod": "2016129",
     * "endPeriod": "2017047"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "狗肖",
     * "number": [
     * 2,
     * 14,
     * 26,
     * 38
     * ],
     * "year": 2019,
     * "latestPeriod": 95,
     * "continuousPeriodUnopened": 19,
     * "maxPeriodUnopened": {
     * "period": 60,
     * "beginPeriod": "1997073",
     * "endPeriod": "1998028"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * },
     * {
     * "sx": "猪肖",
     * "number": [
     * 1,
     * 13,
     * 25,
     * 37,
     * 49
     * ],
     * "year": 2019,
     * "latestPeriod": 103,
     * "continuousPeriodUnopened": 11,
     * "maxPeriodUnopened": {
     * "period": 75,
     * "beginPeriod": "2006081",
     * "endPeriod": "2007001"
     * },
     * "maxPeriodContinuousOpen": {
     * "period": 7,
     * "beginPeriod": "2019107",
     * "endPeriod": "2019113"
     * }
     * }
     * ]
     * }
     * ],
     * "count": 0
     * }<br></p>
     */

    private int id;
    private int userId;
    private int relatedId;
    private int pictureId;
    private String pictureName;
    private int type;
    private String filePath;
    private int year;
    private int terms;
    private String createTime;
    private int subType;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

