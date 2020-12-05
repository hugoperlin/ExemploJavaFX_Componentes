import javafx.geometry.Insets;
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
    private CheckBox chkLol;
    private CheckBox chkFortnite;
    private CheckBox chkCod;
    private CheckBox chkBF;

    private Label lbEhDoador;
    private RadioButton rbEhDoadorSim;
    private RadioButton rbEhDoadorNao;

    private Button btSalvar;
    private Button btLimpar;


    private ListView<Pessoa> lstPessoas;
    private TextArea areaInfos;


    private Cadastro cadastro;

    public Janela(Cadastro cadastro){
        this.cadastro = cadastro;
    }


    public Node getRoot(){

        root = new GridPane();

        root.setPadding(new Insets(10));
        root.setVgap(10);

        lbNome = new Label("_Nome:");
        tfNome = new TextField();
        lbNome.setLabelFor(tfNome);
        lbNome.setMnemonicParsing(true);

        root.add(lbNome,0,0);
        root.add(tfNome,1,0,3,1);


        lbEmail = new Label("_Email");
        tfEmail = new TextField();
        lbEmail.setLabelFor(tfEmail);
        lbEmail.setMnemonicParsing(true);

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
        chkLol = new CheckBox("LoL");
        chkBF = new CheckBox("Battle Field");
        chkCod = new CheckBox("Call of Duty");
        chkFortnite = new CheckBox("Fortnite");

        root.add(lbJogos,0,3);
        root.add(chkLol,0,4);
        root.add(chkBF,1,4);
        root.add(chkCod,0,5);
        root.add(chkFortnite,1,5);

        lbEhDoador = new Label("Doador de órgãos:");
        rbEhDoadorSim = new RadioButton("Sim");
        rbEhDoadorNao = new RadioButton("Não");

        ToggleGroup grpDoador = new ToggleGroup();
        rbEhDoadorNao.setToggleGroup(grpDoador);
        rbEhDoadorSim.setToggleGroup(grpDoador);

        root.add(lbEhDoador,0,6);
        root.add(rbEhDoadorSim,1,6);
        root.add(rbEhDoadorNao,2,6);

        btSalvar = new Button("Adicionar");
        btSalvar.setOnAction((evt)->{
            salvarPessoa();
        });


        btLimpar = new Button("Limpar");
        btLimpar.setOnAction((evt)->{
            limpar();
        });

        HBox boxBotoes = new HBox(btSalvar,btLimpar);
        boxBotoes.setSpacing(5);
        root.add(boxBotoes,3,7);



        lstPessoas = new ListView<>();

        


        lstPessoas.setOnMouseClicked((evt)->{
            atualizaAreaInfos();
        });

        areaInfos = new TextArea();
        areaInfos.setEditable(false);

        HBox boxInfos = new HBox();
        boxInfos.setSpacing(5);
        boxInfos.getChildren().add(lstPessoas);
        boxInfos.getChildren().add(areaInfos);


        root.add(boxInfos,0,8,4,1);

        return root;
    }

    private void salvarPessoa() {

        String nome = tfNome.getText();
        String email = tfEmail.getText();
        int anoNascimento = cbAnoNascimento.getValue();
        int anoEntrada = cbAnoEntrada.getValue();

        boolean ehDoador = rbEhDoadorSim.isSelected();

        ArrayList<String> jogos = new ArrayList<>();

        if(chkCod.isSelected()){
            jogos.add(chkCod.getText());
        }

        if(chkFortnite.isSelected()){
            jogos.add(chkFortnite.getText());
        }

        if(chkBF.isSelected()){
            jogos.add(chkBF.getText());
        }

        if(chkLol.isSelected()){
            jogos.add(chkLol.getText());
        }

        boolean ret = cadastro.adicionaPessoa(nome,email,anoNascimento,anoEntrada,jogos,ehDoador);

        if(ret){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Pessoa salva!!");
            alert.showAndWait();
            atualizaListaPessoas();
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
        chkLol.setSelected(false);
        chkBF.setSelected(false);
        chkFortnite.setSelected(false);
        chkCod.setSelected(false);


    }


    private void atualizaListaPessoas(){
        List<Pessoa> lista = cadastro.getLista();

        lstPessoas.getItems().clear();
        for(Pessoa p:lista){
            lstPessoas.getItems().add(p);
        }

    }


    private void atualizaAreaInfos() {

        Pessoa p = lstPessoas.getSelectionModel().getSelectedItem();

        if(p != null){
            String str = "";

            str += "Nome: "+p.getNome()+"\n";
            str += "E-mail: "+p.getEmail()+"\n";
            str += "Ano de Entrada: "+p.getAnoEntrada()+"\n";
            str += "Ano de Nascimento: "+p.getAnoNascimento()+"\n";
            str += "Doador de orgãos?: "+(p.isDoadorOrgaos()?"Sim":"Não")+"\n";
            str += "Jogos:"+ p.getJogos();


            areaInfos.clear();
            areaInfos.setText(str);
        }

    }


}
