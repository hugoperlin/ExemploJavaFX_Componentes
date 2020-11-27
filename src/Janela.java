import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import modelo.Cadastro;
import modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class Janela {

    private GridPane root;

    private Label lbNome;
    private TextField tfNome;

    private Label lbEmail;
    private TextField tfEmail;

    private Label lbAnoNascimento;
    private ComboBox<Integer> cbAnoNascimento;

    private Label lbAnoEntrada;
    private ComboBox<Integer> cbAnoEntrada;

    private Label lbJogos;
    private CheckBox[] chkJogos;

    private Label lbEhDoador;
    private RadioButton rbEhDoadorSim;
    private RadioButton rbEhDoadorNao;

    private Button btSalvar;
    private Button btLimpar;

    private TextArea areaInfos;


    private Cadastro cadastro;

    public Janela(Cadastro cadastro){
        this.cadastro = cadastro;
        this.chkJogos = new CheckBox[Cadastro.NOMES_JOGOS.length];
    }


    public Node getRoot(){

        root = new GridPane();

        lbNome = new Label("Nome:");
        tfNome = new TextField();
        lbNome.setLabelFor(tfNome);

        root.add(lbNome,0,0);
        root.add(tfNome,1,0,3,1);



        lbEmail = new Label("Email");
        tfEmail = new TextField();
        lbEmail.setLabelFor(tfEmail);

        root.add(lbEmail,0,1);
        root.add(tfEmail,1,1,3,1);


        lbAnoNascimento = new Label("Ano de Nascimento:");
        cbAnoNascimento = new ComboBox<>();
        lbAnoNascimento.setLabelFor(cbAnoNascimento);

        for(int i=1900;i<=2020;i++){
            cbAnoNascimento.getItems().add(i);
        }

        root.add(lbAnoNascimento,0,2);
        root.add(cbAnoNascimento,1,2);

        lbAnoEntrada = new Label("Ano de Entrada:");
        cbAnoEntrada = new ComboBox<>();
        lbAnoEntrada.setLabelFor(cbAnoEntrada);

        for(int i=1900;i<=2020;i++){
            cbAnoEntrada.getItems().add(i);
        }

        root.add(lbAnoEntrada,2,2);
        root.add(cbAnoEntrada,3,2);


        lbJogos = new Label("Jogos:");
        root.add(lbJogos,0,3);




        for(int i=0;i<Cadastro.NOMES_JOGOS.length;i++){
            chkJogos[i] = new CheckBox(Cadastro.NOMES_JOGOS[i]);
            root.add(chkJogos[i],1,i+4);
        }

        lbEhDoador = new Label("Doador de órgãos:");
        rbEhDoadorSim = new RadioButton("Sim");
        rbEhDoadorNao = new RadioButton("Não");

        ToggleGroup grpDoador = new ToggleGroup();
        rbEhDoadorNao.setToggleGroup(grpDoador);
        rbEhDoadorSim.setToggleGroup(grpDoador);

        root.add(lbEhDoador,0,10);
        root.add(rbEhDoadorSim,1,10);
        root.add(rbEhDoadorNao,2,10);

        btSalvar = new Button("Adicionar");
        btSalvar.setOnAction((evt)->{
            salvarPessoa();
        });


        btLimpar = new Button("Limpar");

        HBox boxBotoes = new HBox(btSalvar,btLimpar);

        root.add(boxBotoes,3,11);

        areaInfos = new TextArea();
        areaInfos.setEditable(false);

        root.add(areaInfos,0,12,4,1);

        return root;
    }

    private void salvarPessoa() {

        String nome = tfNome.getText();
        String email = tfEmail.getText();
        int anoNascimento = cbAnoNascimento.getValue();
        int anoEntrada = cbAnoEntrada.getValue();
        boolean ehDoador = rbEhDoadorSim.isSelected();
        ArrayList<String> jogos = new ArrayList<>();

        for(int i=0;i<chkJogos.length;i++){
            if(chkJogos[i].isSelected()){
                jogos.add(chkJogos[i].getText());
            }
        }

        boolean ret = cadastro.adicionaPessoa(nome,email,anoNascimento,anoEntrada,jogos,ehDoador);

        if(ret){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Pessoa salva!!");
            alert.showAndWait();
            atualizaAreaInfos();
            limpar();

        }

    }

    private void limpar() {

        tfNome.setText("");
        tfEmail.setText("");
        cbAnoEntrada.getSelectionModel().clearSelection();
        cbAnoNascimento.getSelectionModel().clearSelection();
        rbEhDoadorSim.setSelected(false);
        rbEhDoadorNao.setSelected(false);
        for(int i=0;i<chkJogos.length;i++){
            chkJogos[i].setSelected(false);
        }


    }

    private void atualizaAreaInfos() {

        List<Pessoa> lista = cadastro.getLista();
        String infos = "";



        areaInfos.clear();

        for(Pessoa p:lista){
            infos += p.toString() +"\n";
        }

        areaInfos.setText(infos);

    }


}
