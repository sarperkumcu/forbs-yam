package Controller;

import Models.Url;
import org.jsoup.Jsoup;

import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class EngineController {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String cleanedHTMLDoc() throws Exception{
        return Jsoup.parse(new Cleaner(Whitelist.basic()).clean(Jsoup.connect(url).timeout(30000).execute().parse()).html()).text().toLowerCase();
    }

    public void addKeywordToMap(Url url, String keyword, Integer number){
        url.keywordNumber.put(keyword,number);
    }




}
