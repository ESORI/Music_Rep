package teknos.musicplayer.backoffice;

import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.RepositoryFactory;

public class App {
    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = null;
        ModelFactory modelFactory = null;
        var backOffice = new BackOffice(System.in, System.out, repositoryFactory, modelFactory);

        backOffice.start();

    }
}
