package teknos.musicplayer.backoffice;

import cat.uvic.teknos.musicrep.domain.jdbc.models.JdbcModelFactory;
import cat.uvic.teknos.musicrep.domain.jdbc.repositories.JdbcRepositoryFactory;
import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.RepositoryFactory;

public class App {
    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = new JdbcRepositoryFactory();
        ModelFactory modelFactory = new JdbcModelFactory();
        var backOffice = new BackOffice(System.in, System.out, repositoryFactory, modelFactory);

        backOffice.start();

    }
}
