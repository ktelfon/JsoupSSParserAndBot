package lv.parser.objects;


import java.util.List;

public class ParseResults {
    List<String> links;
    String fileName;

    public ParseResults(List<String> links, String fileName) {
        this.links = links;
        this.fileName = fileName;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
