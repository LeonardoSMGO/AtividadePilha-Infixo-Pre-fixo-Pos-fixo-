import java.util.*;

public class Operacoes {

    public static Set<String> extrairVariaveis(String[] tokens) {
        Set<String> vars = new HashSet<>();
        for (String token : tokens) {
            if (token.matches("[a-zA-Z]+")) {
                vars.add(token);
            }
        }
        return vars;
    }

    public static int avaliarPosfixa(String[] tokens, Map<String, Integer> vars) {
        Stack<Integer> pilha = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[a-zA-Z]+")) {
                pilha.push(vars.get(token));
            } else if (token.matches("-?\\d+")) {
                pilha.push(Integer.parseInt(token));
            } else {
                int b = pilha.pop();
                int a = pilha.pop();
                switch (token) {
                    case "+": pilha.push(a + b); break;
                    case "-": pilha.push(a - b); break;
                    case "*": pilha.push(a * b); break;
                    case "/": pilha.push(a / b); break;
                    default: throw new IllegalArgumentException("Operador inválido: " + token);
                }
            }
        }
        return pilha.pop();
    }

    public static String posfixaParaInfixa(String[] tokens) {
        Stack<String> pilha = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[a-zA-Z0-9]+")) {
                pilha.push(token);
            } else {
                String b = pilha.pop();
                String a = pilha.pop();
                pilha.push("(" + a + " " + token + " " + b + ")");
            }
        }
        return pilha.pop();
    }

    public static String posfixaParaPrefixa(String[] tokens) {
        Stack<String> pilha = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[a-zA-Z0-9]+")) {
                pilha.push(token);
            } else {
                String b = pilha.pop();
                String a = pilha.pop();
                pilha.push(token + " " + a + " " + b);
            }
        }
        return pilha.pop();
    }

    public static String prefixaParaPosfixa(String[] tokens) {
        Stack<String> pilha = new Stack<>();

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (token.matches("[a-zA-Z0-9]+")) {
                pilha.push(token);
            } else {
                String a = pilha.pop();
                String b = pilha.pop();
                pilha.push(a + " " + b + " " + token);
            }
        }
        return pilha.pop();
    }

    public static String infixaParaPosfixa(String[] tokens) {
        Stack<String> pilha = new Stack<>();
        StringBuilder resultado = new StringBuilder();

        for (String token : tokens) {
            if (token.matches("[a-zA-Z0-9]+")) {
                resultado.append(token).append(" ");
            } else if (token.equals("(")) {
                pilha.push(token);
            } else if (token.equals(")")) {
                while (!pilha.isEmpty() && !pilha.peek().equals("(")) {
                    resultado.append(pilha.pop()).append(" ");
                }
                pilha.pop(); // remove "("
            } else { // operador
                while (!pilha.isEmpty() && precedencia(pilha.peek()) >= precedencia(token)) {
                    resultado.append(pilha.pop()).append(" ");
                }
                pilha.push(token);
            }
        }

        while (!pilha.isEmpty()) {
            resultado.append(pilha.pop()).append(" ");
        }

        return resultado.toString().trim();
    }

    private static int precedencia(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            default: return 0;
        }
    }
}