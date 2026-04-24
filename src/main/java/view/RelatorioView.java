package view;
import model.Contribuinte;
import java.util.List;
public class RelatorioView {

    public void showContribuinte(Contribuinte contribuinte){
        System.out.println("----DADOS DO CONTRIBUINTE----");
        System.out.println("-".repeat(40));
        System.out.println("CPF:" + contribuinte.getCpfFormatted());
        System.out.println("Nome: " + contribuinte.getNome());
        System.out.println("Situação: " + contribuinte.getSituation() );
        System.out.println("-".repeat(40));

    }
    public void listContribuintes(List<Contribuinte> contribuintes){
        System.out.println("\n ----LISTA DE CONTRIBUINTES----");
        System.out.println("=".repeat(40));
        System.out.printf("%-15s | %-30s | %-10s\n", "CPF","NOME","SITUAÇÃO");
        System.out.println("\n ----LISTA DE CONTRIBUINTES----");
        System.out.println("=".repeat(40));

        for (Contribuinte contribuinte : contribuintes){
            System.out.printf(
                    "%-15s | %-30s | %-10s\n",
                    contribuinte.getCpf(),
                    contribuinte.getNome().length() > 30 ? contribuinte.getNome().substring(0,27) + "...": contribuinte.getNome(),
                    contribuinte.getSituation()
            );
            System.out.println("=".repeat(40));
            System.out.printf("Total: %d contribuintes\n", contribuintes.size());
        }


    }

    //public void showStatistic(){

    //}



}
