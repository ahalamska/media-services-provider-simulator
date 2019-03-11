package simulation;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cache implements Serializable {
    private static Cache INSTANCE = new Cache();

    public synchronized static Cache getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new Cache();
        }
        return INSTANCE;
    }
   private List<Product> Products = new ArrayList<>();
    private List<LiveStream> Lives = new ArrayList<>();



    public Cache() {
        loadMoviesFromfile();
        loadSeriesFromfile();
        loadLiveStreamFromfile();
    }
    private void loadMoviesFromfile() {

        for (int i = 0; i < 500; i++) {
            String[] movie = readLine("Movies.data", i);
            String cast[] = new String[4];
            cast[0] = movie[9];
            cast[1] = movie[10];
            cast[2] = movie[11];
            cast[3] = movie[12];
            Movie newMovie = new Movie(movie[2], movie[5], movie[3], Integer.parseInt(movie[6]), movie[7], Double.parseDouble(movie[8]), Double.parseDouble(movie[13]), movie[0], movie[4], cast, movie[14], 365);
            this.Products.add(newMovie);


        }
        System.out.println("LoadedToCache - MOVIES");
    }

        private void loadLiveStreamFromfile() {

            for (int i = 0; i < 500; i++) {
                String[] live = readLine("Movies.data", i);
                LiveStream newLive = new LiveStream("Live " + live[2], live[5], live[3], Integer.parseInt(live[6]), live[7], Double.parseDouble(live[8]), Double.parseDouble(live[13]), live[0]);
                this.Lives.add(newLive);

            }
            System.out.println("LoadedToCache - LIVE");
        }

        private void loadSeriesFromfile(){
            for(int i = 0 ; i<499; i++){
                String[] serie = readLine("Series.data" , i);
               //*for(int b = 0; b <12 ; b++){
                //    System.out.println(serie[b].toString());
               // }
                String cast[] = new String[4];
                cast[0] = serie[9];
                cast[1] = serie[10];
                cast[2] = serie[11];
                cast[3] = serie[12];
                Series newSerie = new Series(serie[2],serie[5], serie[3], Integer.parseInt(serie[6]), serie[7], Double.parseDouble(serie[8]), Double.parseDouble(serie[13]) , serie[0] , serie[4], cast);
                this.Products.add(newSerie);

            }
            System.out.println("LoadedToCache SERIE");

    }

    public Product addToListOfProducts(){
        Random r = new Random();
        int nr = r.nextInt(999 - ProductMenager.getInstance().getProductList().size());
        Product p = this.Products.get(nr);

        ProductMenager.getInstance().getProductList().add(p);
        this.Products.remove(nr);
        return p;
    }

    public LiveStream addLiveToListOfProducts(){
        Random r = new Random();
        int nr = r.nextInt(500 - ProductMenager.getInstance().getLiveStreamList().size());
        LiveStream p = this.Lives.get(nr);
        p.setReleaseDate(Integer.toString( new Random().nextInt(14) + Time.getInstance().getDayNumber()));
        ProductMenager.getInstance().getLiveStreamList().add(p);
        this.Lives.remove(nr);
        return p;
    }

    private String[] readLine(String file, int line) {
        try {
            String product = Files.readAllLines(Paths.get(file)).get(line);
            return product.split("\t");
        } catch (java.io.IOException ex) {
            ex.getMessage();
            System.out.println("File does not exist!");
            return new String[]{};
        }
    }

}



