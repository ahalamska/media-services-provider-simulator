package simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Season implements Serializable {
    private int episodeNumber;
    private List<Episode> episodeList = new ArrayList<Episode>();

    public Season() {
        this.episodeNumber = new Random().nextInt(7);
        for (int i = 0; i<this.episodeNumber ; i++) {
            this.episodeList.add(new Episode());
        }
    }



    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }
    public Episode getEpisode(){
        return this.episodeList.get(new Random().nextInt(this.episodeNumber));
    }
}
