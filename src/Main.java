import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Cadastro;

public class Main extends Application {


    private Cadastro cadastro;

    public static void main(String[] args) {

        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        cadastro = new Cadastro();

        Janela janela = new Janela(cadastro);

        stage.setScene(new Scene((Parent) janela.getRoot(),480,640));
        stage.setTitle("Cadastro de Pessoas");
        stage.show();

    }
}
