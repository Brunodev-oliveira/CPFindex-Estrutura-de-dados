package view;

import controller.ReceitaController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import model.Contribuinte;

import javafx.scene.control.ListCell;

import java.util.List;

public class OperationController {

    @FXML private Label lblLoading;
    @FXML private Label lblAmountData;
    @FXML private Label lblStatus;
    @FXML private Label lblAmountNodes;
    @FXML private Label lblHeight ;
    @FXML private Label lblLastSearch;
    @FXML private Label lblBalancingFactor;
    @FXML private Label lblBalance;

    @FXML private TableView<Contribuinte>            tableResult;
    @FXML private TableColumn<Contribuinte, String>  colCpf;
    @FXML private TableColumn<Contribuinte, String>  colNome;
    @FXML private TableColumn<Contribuinte, String>  colSituacao;

    @FXML private TextField tfBuscarCpf;
    @FXML private TextField tfCadCpf;
    @FXML private TextField tfCadNome;
    @FXML private TextField tfCadSit;
    @FXML private TextField tfRemoverCpf;

    @FXML private ListView<Contribuinte> listViewInOrder;

    private ReceitaController receitaCtrll;

    @FXML
    public void initialize() {
        tableResult.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpfFormatted"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situation"));
        listViewInOrder.setCellFactory(lv -> new FormatterListCell());
        loadDataBase();
    }

    private void loadDataBase() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                receitaCtrll = new ReceitaController();
                receitaCtrll.populateBstBase(1000000);
                return null;
            }
        };
        task.setOnRunning(e -> lblLoading.setText("Carregando..."));
        task.setOnSucceeded(e -> {
            lblLoading.setText("✅  Pronto");
            lblLoading.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            refreshData();
            lblAmountData.setText("(" + receitaCtrll.getBstNodeAmount() + " registros)");
        });
        task.setOnFailed(e -> lblLoading.setText("  Erro ao carregar base"));
        new Thread(task).start();
    }

    @FXML
    private void onSearch() {
        if (receitaCtrll == null) { lblStatus.setText("Base ainda carregando..."); return; }
        String cpf = tfBuscarCpf.getText().replaceAll("[^0-9]", "");
        Contribuinte c = receitaCtrll.searchCPF(cpf);
        if (c != null) {
            tableResult.setItems(FXCollections.observableArrayList(c));
            lblStatus.setText("CPF encontrado.");
        } else {
            tableResult.setItems(FXCollections.emptyObservableList());
            lblStatus.setText("CPF não encontrado.");
        }
        refreshData();
    }

    @FXML
    private void onInsert() {
        if (receitaCtrll == null) { lblStatus.setText("Base ainda carregando..."); return; }
        String resultado = receitaCtrll.registerCPF(
                tfCadCpf.getText(), tfCadNome.getText(), tfCadSit.getText()
        );
        switch (resultado) {
            case "Contribuinte Cadastrado com sucesso"           -> { lblStatus.setText(" CPF cadastrado."); resetInput(); }
            case "CPF duplicado"    -> lblStatus.setText("  CPF já cadastrado.");
            case "CPF Inválido" -> lblStatus.setText("  CPF inválido.");
        }
        refreshData();
    }

    @FXML
    private void onRemove() {
        if (receitaCtrll == null) { lblStatus.setText("Base ainda carregando..."); return; }
        boolean ok = receitaCtrll.removeCPF(tfRemoverCpf.getText());
        lblStatus.setText(ok ? "  CPF removido." : " CPF não encontrado.");
        if (ok) tfRemoverCpf.clear();
        refreshData();
    }

    @FXML
    private void onList(){
        List <Contribuinte> list = receitaCtrll.listCPF();
        listViewInOrder.getItems().setAll(list);
    }




    private void resetInput() {
        tfCadCpf.clear(); tfCadNome.clear(); tfCadSit.clear();
    }

    public void refreshData(){
        lblAmountNodes.setText(String.valueOf(receitaCtrll.getBstNodeAmount()));
        lblHeight.setText(String.valueOf(receitaCtrll.getBstHeight()));
        lblLastSearch.setText(String.valueOf(receitaCtrll.getBstAmountLastSearch()));
        lblBalancingFactor.setText(String.valueOf(receitaCtrll.getBstBalanceFactor()));
        lblBalance.setText(receitaCtrll.isBalanced() ? "Sim" : "Não");
        onList();

    }
 }