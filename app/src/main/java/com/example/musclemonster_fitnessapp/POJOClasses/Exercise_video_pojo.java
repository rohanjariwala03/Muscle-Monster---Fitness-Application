package com.example.musclemonster_fitnessapp.POJOClasses;

public class Exercise_video_pojo {
    String id,Title,videoUrl;

    public Exercise_video_pojo(String id, String title, String videoUrl) {
        this.id = id;
        Title = title;
        this.videoUrl = videoUrl;
    }

    public Exercise_video_pojo(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
