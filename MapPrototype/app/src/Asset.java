// Data model for Asset
// Created by Arash Parnia for TAMS
public class Asset {
     
    
    private Double latitude = 38.560884;
    private Double longitude = -121.422357;
    private String Title ="Riverside";
    private String address = "";
    

    public Asset(Double latitude,Double longitude){
      self.latitude = latitude;
      self.longitude = longitude;
    }
     public Asset(Double latitude,Double longitude, String title, String address){
      self.latitude = latitude;
      self.longitude = longitude;
      self.title = title;
      self.address=address;
    }
     
}
