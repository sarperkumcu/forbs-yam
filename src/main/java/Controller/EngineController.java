package Controller;

import Models.Url;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ManagedBean
@ViewScoped
public class EngineController {
    String url;
    String keyword;
    Integer number;
    List<Url> urlList = new ArrayList<Url>();

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.toLowerCase();
    }

    public String cleanedHTMLDoc(String url) throws Exception{

        return Jsoup.parse(new Cleaner(Whitelist.basic()).clean(Jsoup.connect(url).timeout(30000).execute().parse()).html()).text().toLowerCase();
    }

    public void addKeywordToMap(Url url, String keyword, Integer number){
        url.keywordNumber.put(keyword,number);
    }

    public void search() throws Exception{
        Url url = new Url(this.url);
        System.out.println(keyword);
        number = findNumberOfWord(url.getUrlAdress(),keyword);

    }

    public void search2() throws Exception{

        String[] urlAdressList = url.split(",");
        String[] keywordList = keyword.split(",");
        for(String urlAdress : urlAdressList){
            Url url = new Url(urlAdress);
            for(String keyword : keywordList){
                url.addKeywordToList(url.getUrlAdress(),findNumberOfWord(urlAdress,keyword));
            }
            urlList.add(url);
            System.out.println(url.keywordNumber);
        }
            calculatePoint();
    }

    public void calculatePoint(){
        for(Url url : urlList){

        }

    }

    public Integer findNumberOfWord(String url,String keyword) throws Exception{
        String urlContent = cleanedHTMLDoc(url).replaceAll("\\p{P}", " ");
        urlContent = " " + urlContent + " ";
        System.out.println(urlContent);
        keyword = " " + keyword + " ";
        Integer number = (urlContent.length() - urlContent.replaceAll(keyword, "").length()) / (keyword.length());
        if(keyword.contains("i"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("i","ı"),"").length()) / (keyword.length());
        if(keyword.contains("ı"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ı","i"),"").length()) / (keyword.length());
        if(keyword.contains("o"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("o","ö"),"").length()) / (keyword.length());
        if(keyword.contains("ö"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ö","o"),"").length()) / (keyword.length());
        if(keyword.contains("u"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("u","ü"),"").length()) / (keyword.length());
        if(keyword.contains("ü"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ü","u"),"").length()) / (keyword.length());
        if(keyword.contains("c"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("c","ç"),"").length()) / (keyword.length());
        if(keyword.contains("ç"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ç","c"),"").length()) / (keyword.length());
        if(keyword.contains("g"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("g","ğ"),"").length()) / (keyword.length());
        if(keyword.contains("ğ"))
            number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ğ","g"),"").length()) / (keyword.length());
        if(keyword.contains("s"))
             number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("s","ş"),"").length()) / (keyword.length());
        if(keyword.contains("ş"))
             number+=(urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ş","s"),"").length()) / (keyword.length());

        System.out.println(number);
        return number;

    }
    @PostConstruct
    public void init(){
        new Locale("tr", "TR");
    }




}
