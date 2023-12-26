package com.java.mahongbo.model;



public class NewsData {

    private String image;



    private String title;
    private String publishTime;

    private String publisher;

    private String content;

    private String video;
    public NewsData(String image,String publishTime,String publisher,String title,String content){
        this.image=image;
        this.content=content;
        this.publisher=publisher;
        this.publishTime=publishTime;
        this.title=title;
    }
    public String getImage() {
        return image;
    }


    public String getPublishTime() {
        return publishTime;
    }


    public String getVideo() {
        return video;
    }


    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }



    public String getPublisher() {
        return publisher;
    }

    public int Type() {
        if(image.length()<=0){
            return 2;
        }else{
            if(image.length()>5&&image.charAt(1)=='h') {
                return 1;
            } else {
                return 2;
            }
        }

    }

}
