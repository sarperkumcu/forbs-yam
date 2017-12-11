package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Url {
    private String urlAdress;
    private Double point = 0.0;
    public Map keywordNumber = new HashMap();
    private ArrayList<Url> subUrls = new ArrayList<Url>();

    public ArrayList<Url> getSubUrls() { return subUrls; }

    public void setSubUrls(ArrayList<Url> subUrls) { this.subUrls = subUrls; }

    public void addPoint(Double point){
        this.point += point;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
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

    public void addKeywordToList(String keyword,Integer number){
        keywordNumber.put(keyword,number);
    }


}
