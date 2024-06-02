package teknos.musicplayer.backoffice;

import cat.uvic.teknos.musicrep.domain.jdbc.models.JdbcModelFactory;
import cat.uvic.teknos.musicrep.domain.jdbc.repositories.JdbcRepositoryFactory;
import cat.uvic.teknos.musicrep.domain.jpa.models.JpaModelFactory;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaRepositoryFactory;
import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.RepositoryFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.stream.Collectors;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        showBanner();

        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));
        RepositoryFactory repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        ModelFactory modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();

        var backOffice = new BackOffice(System.in, System.out, repositoryFactory, modelFactory);

        backOffice.start();

    }
    private static void showBanner() {
        var bannerStream = App.class.getResourceAsStream("/banner.txt");

        var banner = new BufferedReader(new InputStreamReader(bannerStream))
                .lines().collect(Collectors.joining("\n"));

        System.out.println(banner);
    }
}
