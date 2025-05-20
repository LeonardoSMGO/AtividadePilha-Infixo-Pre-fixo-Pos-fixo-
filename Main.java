import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nInforme a notação da expressão (infixa, posfixa, prefixa): ");
        String tipo = sc.nextLine().trim().toLowerCase();

        System.out.print("Digite a expressão separada por espaço: ");
        String[] tokens = sc.nextLine().trim().split("\\s+");

        String posfixa;
        switch (tipo) {
            case "infixa":
                posfixa = Operacoes.infixaParaPosfixa(tokens);
                break;
            case "prefixa":
                posfixa = Operacoes.prefixaParaPosfixa(tokens);
                break;
            case "posfixa":
            default:
                posfixa = String.join(" ", tokens);
                break;
        }

        Set<String> vars = Operacoes.extrairVariaveis(posfixa.split("\\s+"));
        Map<String, Integer> valores = new HashMap<>();

        for (String var : vars) {
            System.out.print("Informe o valor de " + var + ": ");
            valores.put(var, sc.nextInt());
        }

        int resultado = Operacoes.avaliarPosfixa(posfixa.split("\\s+"), valores);

        String infixa = Operacoes.posfixaParaInfixa(posfixa.split("\\s+"));
        String prefixa = Operacoes.posfixaParaPrefixa(posfixa.split("\\s+"));

        System.out.println("\n=== RESULTADO ===");
        System.out.println("Expressão Pós-fixa: " + posfixa);
        System.out.println("Expressão Infixa:   " + infixa);
        System.out.println("Expressão Prefixa:  " + prefixa);
        System.out.println("Valor final:        " + resultado);
    }
}