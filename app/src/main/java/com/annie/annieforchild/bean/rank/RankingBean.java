package com.annie.annieforchild.bean.rank;

/**
 * Created by WangLei on 2018/1/31 0031
 */

public class RankingBean {
    private int ranking;
    private int headPic;
    private String name;
    private String ranking_time;

    public RankingBean(int ranking, int headPic, String name, String ranking_time) {
        this.ranking = ranking;
        this.headPic = headPic;
        this.name = name;
        this.ranking_time = ranking_time;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getHeadPic() {
        return headPic;
    }

    public void setHeadPic(int headPic) {
        this.headPic = headPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRanking_time() {
        return ranking_time;
    }

    public void setRanking_time(String ranking_time) {
        this.ranking_time = ranking_time;
    }
}
