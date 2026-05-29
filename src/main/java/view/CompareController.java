package view;

import controller.ReceitaController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Contribuinte;

public class CompareController {

    @FXML private TextField tfCpf;
    @FXML private Button    btnCompare;

    @FXML private Label lblLoading;

    // BST
    @FXML private Label lblBstResult;
    @FXML private Label lblBstComparisons;
    @FXML private Label lblBstTime;

    // Lista Ligada
    @FXML private Label lblListResult;
    @FXML private Label lblListComparisons;
    @FXML private Label lblListTime;

    // Veredito
    @FXML private Label lblVerdict;

    private ReceitaController receitaCtrll;
    private boolean ready = false;

    @FXML
    public void initialize() {
        btnCompare.setDisable(true);
        loadBases();
    }

    private void loadBases() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                receitaCtrll = new ReceitaController();
                receitaCtrll.populateBstBase(1000000);
                receitaCtrll.populateLkdBase(1000000);
                return null;
            }
        };
        task.setOnRunning(e -> lblLoading.setText("Carregando bases..."));
        task.setOnSucceeded(e -> {
            lblLoading.setText("✅  Pronto — " + receitaCtrll.getBstNodeAmount() + " registros em cada estrutura");
            lblLoading.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            btnCompare.setDisable(false);
            ready = true;
        });
        task.setOnFailed(e -> lblLoading.setText("Erro ao carregar base."));
        new Thread(task).start();
    }

    @FXML
    private void onCompare() {
        if (!ready) return;

        String cpf = tfCpf.getText().replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            lblVerdict.setText("CPF inválido.");
            return;
        }

        // ── BST ──────────────────────────────────────────────
        Contribuinte bstResult = receitaCtrll.compareBstSearch(cpf);
        int   bstComp = receitaCtrll.getCompareBstSearchCount();
        long  bstNs   = receitaCtrll.getBstSearchTimeNs();

        lblBstResult.setText(bstResult != null ? "✅  Encontrado" : "❌  Não encontrado");
        lblBstComparisons.setText(String.valueOf(bstComp));
        lblBstTime.setText(formatNs(bstNs));

        // ── Lista Ligada ──────────────────────────────────────
        Contribuinte listResult = receitaCtrll.compareListSearch(cpf);
        int  listComp = receitaCtrll.getCompareListSearchCount();
        long listNs   = receitaCtrll.getListSearchTimeNs();

        lblListResult.setText(listResult != null ? "✅  Encontrado" : "❌  Não encontrado");
        lblListComparisons.setText(String.valueOf(listComp));
        lblListTime.setText(formatNs(listNs));

        // ── Veredito ──────────────────────────────────────────
        if (bstComp > 0 && listComp > 0) {
            double ratio = (double) listComp / bstComp;
            lblVerdict.setText(String.format(
                "BST fez %.0fx menos comparações que a Lista Ligada.", ratio));
        } else {
            lblVerdict.setText("—");
        }
    }

    private String formatNs(long ns) {
        if (ns < 1_000) return ns + " ns";
        if (ns < 1_000_000) return String.format("%.2f µs", ns / 1_000.0);
        return String.format("%.2f ms", ns / 1_000_000.0);
    }
}
