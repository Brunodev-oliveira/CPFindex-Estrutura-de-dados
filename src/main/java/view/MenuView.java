package view;

import java.util.Scanner;

public class MenuView {

    private Scanner scanner;

    public MenuView(){
        this.scanner = new Scanner(System.in);

    }


    public void mainMenu(){
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     SISTEMA RECEITA FEDERAL - SIMULAÇÃO");
        System.out.println("=".repeat(50));
        //System.out.println("Base de CPFs carregada");
        System.out.println("-".repeat(50));
        System.out.println("[1] Cadastrar novo CPF");
        System.out.println("[2] Consultar CPF");
        System.out.println("[3] Remover CPF (cancelar)");
        System.out.println("[4] Listar CPFs");
        System.out.println("[0] Sair");
        System.out.println("=".repeat(50));
        System.out.print("Escolha: ");

    }


    public String readString(String message){
        System.out.print(message);
        return scanner.nextLine();
    }

    public int readInt(String message){
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public double readDouble(String message){
        System.out.print(message);
        return Double.parseDouble(scanner.nextLine());
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public void showError(String erro){
        System.err.println("X ERRO:" + erro);
    }

    public void showSucces(String message){
        System.out.println(">>>" + message);
    }
    public void waitEnter() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }











}
