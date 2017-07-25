package lv.parser;

import lv.parser.objects.ParseResults;
import lv.parser.objects.SearchParams;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SsLvParser {

    Calendar c = Calendar.getInstance();
    private static final String fileExtension = ".txt";
    private String fileName = "FileNameToWrite" + c.get(Calendar.DAY_OF_YEAR) + fileExtension;
    private static final String url = "https://www.ss.lv/ru/real-estate/flats/";

    public SearchParams buildSearchParams(){
        SearchParams sp = new SearchParams("riga",2,35,2,150,250);
        return sp;
    }

    public ParseResults getAppartamentRentOptions(SearchParams sp) throws IOException {

        List<String> allOptions = new ArrayList();

        String city = sp.getCity();
        Document doc = Jsoup.connect(url+city+"/today/hand_over/").get();

        int numberOfPages = getNumberOFPages(doc);
        allOptions.addAll(getPage(doc, sp));
        for(int i = 0; i < numberOfPages; i++) {
            doc = Jsoup.connect(url+city+"/today/hand_over/page"+(i+2)+".html").get();
            allOptions.addAll(getPage(doc, sp));
        }

        System.out.println(" SsLvParser.getAppartamentRentOptions() Options("+allOptions.size()+") found!");

        List<String> ssLinks = new ArrayList<String>();
        try {
            ssLinks = FileUtils.readLines(new File(fileName));
        }catch (IOException e){
            System.out.println(" No file with name: " + fileName);
        }

        List<String> newOptions = new ArrayList();
        for(String link : allOptions){

            boolean isWritten = false;
            for(String writtenLink : ssLinks){
                if(writtenLink.equals(link)){
                    isWritten = true;
                    break;
                }
            }

            if(isWritten){
                continue;
            }

            newOptions.add(link);
            FileUtils.writeStringToFile(new File(fileName), link+"\n", true);
        }

        return new ParseResults(newOptions, fileName);
    }

    private List<String> getPage(Document doc, SearchParams sp){
        List<String> allOptions = new ArrayList();
        Elements tBodys = doc.select("tbody");
        Element tBodyWithAds = tBodys.get(1);
        Elements allTrs = tBodyWithAds.select("tr");
        for(int i = 4; i< allTrs.size() - 4; i++){
            String link  = filterAd( allTrs.get(i) , sp);
            if(link != null){
                allOptions.add("https://www.ss.lv"+link);
            }
        }

        return allOptions;
    }

    private int getNumberOFPages(Document doc){
        Elements pageMenuLinks = doc.select("div.td2");
        Elements pureLinks = pageMenuLinks.select("a");
        return  pureLinks.size()-2;
    }

    @org.jetbrains.annotations.Nullable
    private String filterAd(Element element, SearchParams sp){
        Elements allTds = element.select("td");
        String linkHref = allTds.get(1).select("a").attr("href");

        String address = allTds.get(3).select("b").get(0).text();
        String rooms = allTds.get(4).select("b").get(0).text();
        String area = allTds.get(5).select("b").get(0).text();
        String floor = allTds.get(6).select("b").get(0).text();
        double cost = Double.parseDouble(allTds.get(9).select("b").get(0).text().replaceAll(",", ""));

        if(sp.getCostBottom() > cost || sp.getCostTop() < cost){
            return null;
        }

        if(sp.getArea() > Integer.parseInt(area)){
            return null;
        }

        if(sp.getRooms() > Integer.parseInt(rooms)){
            return null;
        }

        if(sp.getFloor() > Integer.parseInt(floor)){
            return null;
        }

        return linkHref;
    }
}
