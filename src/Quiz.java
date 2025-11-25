import java.io.*;
import java.util.*;

public class Quiz {
    private ArrayList<Question> questions = new ArrayList<>();
    private int score;
    private Scanner scanner = new Scanner(System.in);

    ////////////////// colors /////////////////
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public Quiz() {
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question(
                "Who created Java?",
                new String[]{"Oracle", "Google", "Sun Microsystems", "Microsoft"},
                2, "Java", "Easy"
        ));

        questions.add(new Question(
                "Which keyword is used to inherit a class?",
                new String[]{"inherit", "extends", "super", "implements"},
                1, "Java", "Medium"
        ));

        questions.add(new Question(
                "What is 12 x 11?",
                new String[]{"121", "132", "101", "112"},
                1, "Math", "Easy"
        ));

        questions.add(new Question(
                "Who painted the Mona Lisa?",
                new String[]{"Picasso", "Da Vinci", "Van Gogh", "Michelangelo"},
                1, "General Knowledge", "Easy"
        ));

        questions.add(new Question(
                "Solve: 55 / 5 + 10",
                new String[]{"21", "12", "15", "20"},
                0, "Math", "Medium"
        ));

        questions.add(new Question(
                "Which interface is used for collections that are ordered?",
                new String[]{"List", "Map", "Set", "Queue"},
                0, "Java", "Hard"
        ));
    }

    public void start() {
        score = 0;

        System.out.println(CYAN + "\n------- JAVA QUIZ GAME -------\n" + RESET);

        String category = pickCategory();
        String difficulty = pickDifficulty();

        /////////////// filter ///////////////
        ArrayList<Question> selected = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategory().equalsIgnoreCase(category) &&
                    q.getDifficulty().equalsIgnoreCase(difficulty)) {
                selected.add(q);
            }
        }

        if (selected.isEmpty()) {
            System.out.println(RED + "No questions found for this category/difficulty!" + RESET);
            return;
        }

        Collections.shuffle(selected);

        for (Question q : selected) askQuestion(q);

        finish(selected.size());
    }

    private String pickCategory() {
        System.out.println("Choose a category:");
        System.out.println("1. Java");
        System.out.println("2. Math");
        System.out.println("3. General Knowledge");

        while (true) {
            System.out.print("Select (1-3): ");
            String c = scanner.nextLine();

            switch (c) {
                case "1": return "Java";
                case "2": return "Math";
                case "3": return "General Knowledge";
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private String pickDifficulty() {
        System.out.println("\nChoose difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        while (true) {
            System.out.print("Select (1-3): ");
            String d = scanner.nextLine();

            switch (d) {
                case "1": return "Easy";
                case "2": return "Medium";
                case "3": return "Hard";
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void askQuestion(Question q) {
        System.out.println(YELLOW + "\nCategory: " + q.getCategory() + " | Difficulty: " + q.getDifficulty() + RESET);
        System.out.println(q.getQuestion());

        String[] choices = q.getChoices();
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }

        long start = System.currentTimeMillis();
        int answer = getUserAnswer();
        long time = (System.currentTimeMillis() - start) / 1000;

        if (answer == q.getAnswerIndex() + 1) {
            System.out.println(GREEN + "Correct!" + RESET);
            score++;
        } else {
            System.out.println(RED + "Incorrect!" + RESET);
            System.out.println("Correct answer: " + choices[q.getAnswerIndex()]);
        }

        System.out.println("Time taken: " + time + " seconds");
    }

    private int getUserAnswer() {
        while (true) {
            try {
                System.out.print("Your answer (1-4): ");
                int ans = Integer.parseInt(scanner.nextLine());
                if (ans >= 1 && ans <= 4) return ans;
            } catch (Exception ignored) {}
            System.out.println("Invalid input.");
        }
    }

    private void finish(int totalQuestions) {
        System.out.println(CYAN + "\n---------- QUIZ COMPLETE ---------" + RESET);
        System.out.println("Score: " + score + " / " + totalQuestions);
        System.out.println("Percentage: " + (score * 100 / totalQuestions) + "%");

        saveScore(score);

        System.out.println("\n---- Leaderboard ----");
        showLeaderboard();

        askToPlayAgain();
    }

    private void saveScore(int score) {
        try (FileWriter fw = new FileWriter("leaderboard.txt", true)) {
            fw.write(score + " points (" + new Date() + ")\n");
        } catch (IOException e) {
            System.out.println("Failed to save score.");
        }
    }

    private void showLeaderboard() {
        try (BufferedReader br = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            while ((line = br.readLine()) != null) System.out.println(line);
        } catch (Exception e) {
            System.out.println("No leaderboard yet.");
        }
    }

    private void askToPlayAgain() {
        System.out.print("\nWant to play again? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            start();
        } else {
            System.out.println(GREEN + "Thanks for playing!" + RESET);
        }
    }
}
