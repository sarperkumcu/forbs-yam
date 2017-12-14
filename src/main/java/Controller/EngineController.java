package Controller;

import Models.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.*;
import java.util.*;

@ManagedBean
@ViewScoped
public class EngineController {
    String url;
    String keyword;
    Integer number;
    List<Url> urlList = new ArrayList<Url>();
    boolean step1Render = false;
    List<String> synonyms = new ArrayList<String>();

    public boolean isStep1Render() {
        return step1Render;
    }

    public void setStep1Render(boolean step1Render) {
        this.step1Render = step1Render;
    }

    boolean didUserClickQuery = false;

    public boolean isDidUserClickQuery() {
        return didUserClickQuery;
    }

    public void setDidUserClickQuery(boolean didUserClickQuery) {
        this.didUserClickQuery = didUserClickQuery;
    }


    public List<Url> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }

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

    public String cleanedHTMLDoc(String url) throws Exception {

        //try{
        Document dirtyDoc = Jsoup.connect(url).timeout(30000).execute().parse();
        return Jsoup.parse(new Cleaner(Whitelist.basic()).clean(Jsoup.parseBodyFragment(dirtyDoc.text())).html()).text().toLowerCase();


       /* }catch (Exception ex){
            System.out.println("An error occured.");
        }
            return "";*/
    }

    public void addKeywordToMap(Url url, String keyword, Integer number) {
        url.keywordNumber.put(keyword, number);
    }

    public void search() throws Exception {
        Url url = new Url(this.url);
        System.out.println(keyword);
        number = findNumberOfWord(url.getUrlAdress(), keyword);
        step1Render = true;
    }

    String[] keywordList;

    public void search2() throws Exception {
        urlList = new ArrayList<Url>();

        String[] urlAdressList = url.split(",");
        keywordList = keyword.split(",");
        for (String urlAdress : urlAdressList) {
            Url url = new Url(urlAdress);
            for (String keyword : keywordList) {
                url.addKeywordToList(keyword, findNumberOfWord(urlAdress, keyword));
            }
            urlList.add(url);
            System.out.println(url.keywordNumber);
        }
        calculatePoint();
        didUserClickQuery = true;

    }

    public void calculatePoint() {
        int totalNumberOfWord;

        for (String keyword : keywordList) {
            totalNumberOfWord = 0;
            for (Url url : urlList) {
                totalNumberOfWord += (Integer) url.keywordNumber.get(keyword);
            }
            Double average = (double) (totalNumberOfWord / urlList.size());
            for (Url url : urlList) {
                if (average.equals(0.0))
                    url.addPoint(0.0);
                else
                    url.addPoint(((Integer) (url.getKeywordNumber().get(keyword))) / average);
            }
        }

        /*for(Url url : urlList) {
            System.out.println( url.getUrlAdress() +" - " + url.getPoint());
        }*/

    }

    public void calculatePointTree(Url mainUrl) {
        int totalNumberOfWord;

        for (String keyword : keywordList) {
            totalNumberOfWord = 0;
            for (Url url : totalUrlList) {
                totalNumberOfWord += (Integer) url.keywordNumber.get(keyword);
            }
            Double average = (double) (totalNumberOfWord / totalUrlList.size());

            for (List<Url> url : urlTree) {
                for (Url currentURL : url) {
                    if (average.equals(0.0))
                        mainUrl.addPoint(0.0);
                    else /*if(currentURL.getUrlAdress().contains(mainUrl.getUrlAdress()))*/
                         mainUrl.addPoint(((Integer) (currentURL.getKeywordNumber().get(keyword))) / average);
                }
            }
        }
    }

    public Integer findNumberOfWord(String url, String keyword) throws Exception {
        String urlContent = cleanedHTMLDoc(url).replaceAll("\\p{P}", " ");
        urlContent = " " + urlContent + " ";
        // System.out.println(urlContent);
        keyword = " " + keyword + " ";
        Integer number = (urlContent.length() - urlContent.replaceAll(keyword, "").length()) / (keyword.length());
        if (keyword.contains("i"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("i", "ı"), "").length()) / (keyword.length());
        if (keyword.contains("ı"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ı", "i"), "").length()) / (keyword.length());
        if (keyword.contains("o"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("o", "ö"), "").length()) / (keyword.length());
        if (keyword.contains("ö"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ö", "o"), "").length()) / (keyword.length());
        if (keyword.contains("u"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("u", "ü"), "").length()) / (keyword.length());
        if (keyword.contains("ü"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ü", "u"), "").length()) / (keyword.length());
        if (keyword.contains("c"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("c", "ç"), "").length()) / (keyword.length());
        if (keyword.contains("ç"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ç", "c"), "").length()) / (keyword.length());
        if (keyword.contains("g"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("g", "ğ"), "").length()) / (keyword.length());
        if (keyword.contains("ğ"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ğ", "g"), "").length()) / (keyword.length());
        if (keyword.contains("s"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("s", "ş"), "").length()) / (keyword.length());
        if (keyword.contains("ş"))
            number += (urlContent.length() - urlContent.replaceAll(keyword.replaceAll("ş", "s"), "").length()) / (keyword.length());

        //System.out.println(number);
        return number;

    }

    public void search3() throws Exception {
        ArrayList<Url> parentUrlList = new ArrayList<Url>();
        urlList = new ArrayList<Url>();
        System.out.println("search 3");
        String[] urlAdressList = url.split(",");
        keywordList = keyword.split(",");
        for (String urlAdress : urlAdressList) {
            Url url = new Url(urlAdress);
            for(String keyword : keywordList)
            url.addKeywordToList(keyword,0);
            crawl(url, url.getUrlAdress());
            ArrayList<Url> subUrls = url.getSubUrls();
            for (Url subUrl : subUrls) {
                crawl(subUrl, url.getUrlAdress());
            }
            parentUrlList.add(url);
            calculatePointTree(url);
        }



        System.out.println("Size:" + urlTree.size());
        for (List<Url> url : urlTree) {
            for (Url currentURL : url) {
                System.out.println(currentURL.getUrlAdress() + " -- " + currentURL.getKeywordNumber() /*+ " -- " + currentURL.getPoint()*/ + " -- " + currentURL.getParentUrl().getUrlAdress());

            }
            System.out.println("  -----  --------- ----- --- -- -- -- -");
        }

        for (Url url : parentUrlList) {
            System.out.println("PARENT " + url.getUrlAdress() + " : " + url.getPoint());
        }
    }

    Integer numberOfTotalUrl = 0;
    ArrayList<List<Url>> urlTree = new ArrayList<List<Url>>();
    ArrayList<String> links = new ArrayList<String>();
    ArrayList<String> totalLinks = new ArrayList<String>();
    ArrayList<Url> totalUrlList = new ArrayList<Url>();

    public void crawl(Url url, String website) throws Exception {

        urlList = new ArrayList<Url>();
        //totalUrlList = new ArrayList<Url>();
        //links = new ArrayList<String>();
        try {
            Document htmlDocument = Jsoup.connect(url.getUrlAdress()).timeout(30000).execute().parse();

            //System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            for (Element link : linksOnPage) {
                if ((link.absUrl("href").contains(website) && link.absUrl("href").indexOf(website) == 0) && isValid(link.absUrl("href")) && !totalLinks.contains(link.absUrl("href")) && !link.absUrl("href").contains("#") && !link.absUrl("href").equals(website)) {
                    this.totalLinks.add(link.absUrl("href"));
                    Url subUrl = new Url(link.absUrl("href"));
                    for (String keyword : keywordList) {
                        subUrl.addKeywordToList(keyword, findNumberOfWord(subUrl.getUrlAdress(), keyword));
                        subUrl.setParentUrl(url);
                    }
                    url.addSubUrlToList(subUrl);
                    urlList.add(subUrl);
                    totalUrlList.add(subUrl);
                }
            }
            // System.out.println("Found (" + links.size() + ") links");


        } catch (IOException ioe) {
            // We were not successful in our HTTP request
            // System.out.println("Error in out HTTP request " + ioe);
        }
        if (!urlList.isEmpty())
            urlTree.add(urlList);
    }

    public boolean isValid(String url) {
        if (url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".jpg") ||
                url.toLowerCase().endsWith(".pdf") || url.toLowerCase().endsWith(".docx") ||
                url.toLowerCase().endsWith(".zip") || url.toLowerCase().endsWith(".xlsx") ||
                url.toLowerCase().endsWith(".doc") || url.toLowerCase().endsWith(".rar") ||
                url.toLowerCase().endsWith(".gif") || url.toLowerCase().endsWith(".ppt") ||
                url.toLowerCase().endsWith(".pptx") || url.toLowerCase().contains("\\\\") ||
                url.toLowerCase().endsWith(".xls")) {
            return false;
        }

        return true;

    }

    @PostConstruct
    public void init() {
        new Locale("tr", "TR");
        readSynonymsFile();
    }
    Map synonymMap = new HashMap();

    public void readSynonymsFile() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("kelime-esanlamlisi.txt").getFile());
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] word= line.split("\t");
                if(!synonymMap.containsKey(word[0]))
                     synonymMap.put(word[0],word[1]);

            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(synonymMap);
    }
}



