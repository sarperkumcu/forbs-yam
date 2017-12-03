package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Url {
    private String urlAdress;
    private Integer point;
    public Map keywordNumber = new HashMap();

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Map getKeywordNumber() {
        return keywordNumber;
    }

    public void setKeywordNumber(Map wordNumber) {
        this.keywordNumber = wordNumber;
    }

    public String getUrlAdress() {
        return urlAdress;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    public Url(String urlAdress){
        this.urlAdress = urlAdress;
    }

    public void addKeywordToList(String url,Integer number){
        keywordNumber.put(url,number);
    }


}
