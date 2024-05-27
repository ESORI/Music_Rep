package teknos.musicplayer.backoffice;

import cat.uvic.teknos.musicrep.domain.jdbc.models.JdbcModelFactory;
import cat.uvic.teknos.musicrep.domain.jdbc.repositories.JdbcRepositoryFactory;
import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.RepositoryFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        showBanner();
        RepositoryFactory repositoryFactory = new JdbcRepositoryFactory();
        ModelFactory modelFactory = new JdbcModelFactory();
        var backOffice = new BackOffice(System.in, System.out, repositoryFactory, modelFactory);

        backOffice.start();

    }
    private static void showBanner() {
        //var loc = System.getProperty("user.dir");
        //var bannerStream = Main.class.getResourceAsStream(loc + "/src/main/resources/banner.txt");
        var bannerStream = App.class.getResourceAsStream("/banner.txt");

        var banner = new BufferedReader(new InputStreamReader(bannerStream))
                .lines().collect(Collectors.joining("\n"));

        System.out.println(banner);
    }
}
