import java.util.Random;
import java.util.Scanner;

public class FirstGame {

    private static final int FIELD_SIZE = 5;
    private static final int NUMBER_OF_VICTORY_SYMBOLS = 4;
    private static final char SYMBOL_HUMAN = 'X';
    private static final char EMPTY_ITEM = '.';
    private static final char SYMBOL_II = '0';
    private static char[][] array;
    private static Scanner in;
    private static Random r;
    private static boolean isHumanStep;

    public static void main(String[] args) {
        init();
        startGame();
        finish();
    }

    private static void finish() {
        if (in != null) {
            in.close();
        }
    }

    private static void startGame() {

        while (true) {

            if (isHumanStep) {
                printField();
                humanStep();
            } else {
                iiStep();
            }
            boolean isWon = checkWin();
            if (isWon && isHumanStep) {
                printField();
                System.out.println("Вы победили!");
                return;
            } else if (isWon && !isHumanStep) {
                printField();
                System.out.println("Выйграл ИИ!");
                return;
            }

            boolean hasEmptyItems = hasEmptyItems();
            if (!hasEmptyItems) {
                printField();
                System.out.println("Свободных ячеек больше нет, ничья!");
                return;
            }
            isHumanStep = !isHumanStep;
        }
    }

    private static void iiStep() {
        while (true) {
            int x = r.nextInt(FIELD_SIZE);
            int y = r.nextInt(FIELD_SIZE);
            boolean isValid = isCellValid(x, y);
            if (isValid) {
                array[x][y] = SYMBOL_II;
                return;
            }
        }
    }

    private static boolean isCellValid(int x, int y) {
        if (x < 0 || x > FIELD_SIZE || y < 0 || y > FIELD_SIZE) {
            return false;
        }
        if (array[x][y] == EMPTY_ITEM) {
            return true;
        }
        return false;
    }

    private static boolean hasEmptyItems() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == EMPTY_ITEM) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin() {

        char checkSymbol = SYMBOL_HUMAN;
        if (!isHumanStep) {
            checkSymbol = SYMBOL_II;
        }

        if (checkLines(checkSymbol, true)) {
            return true;
        }
        if (checkLines(checkSymbol, false)) {
            return true;
        }
        if (checkLeftDiagonal(checkSymbol)) {
            return true;
        }
        if (checkRightDiagonal(checkSymbol)) {
            return true;
        }
        return false;
    }

    private static boolean checkLeftDiagonal(char checkSymbol) {
        int counter = 0;
        int coefficient = FIELD_SIZE - NUMBER_OF_VICTORY_SYMBOLS;
        for (int j = 0; j <= coefficient; j++) {
            for (int i = 0; i < array.length - j; i++) {
                if (array[i][i + j] == checkSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
                if (counter == NUMBER_OF_VICTORY_SYMBOLS) {
                    return true;
                }
            }
        }
        for (int j = 0; j <= coefficient; j++) {
            for (int i = 0; i < array.length - j; i++) {
                if (array[i + j][i] == checkSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
                if (counter == NUMBER_OF_VICTORY_SYMBOLS) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkRightDiagonal(char checkSymbol) {

        int counter = 0;
        int coefficient = FIELD_SIZE - NUMBER_OF_VICTORY_SYMBOLS;
        for (int j = 0; j <= coefficient; j++) {
            for (int i = 0; i < array.length - j; i++) {
                if (array[i][FIELD_SIZE - (i + j) - 1] == checkSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
                if (counter == NUMBER_OF_VICTORY_SYMBOLS) {
                    return true;
                }
            }
        }
        for (int j = 0; j <= coefficient; j++) {
            for (int i = 0; i < array.length - j; i++) {
                if (array[i + j][FIELD_SIZE - i - 1] == checkSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
                if (counter == NUMBER_OF_VICTORY_SYMBOLS) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkLines(char checkSymbol, boolean isHorizontal) {

        for (int i = 0; i < array.length; i++) {
            int counter = 0;
            for (int j = 0; j < array[0].length; j++) {
                if (isHorizontal) {
                    if (array[i][j] == checkSymbol) {
                        counter++;
                    } else {
                        counter = 0;
                    }
                } else {
                    if (array[j][i] == checkSymbol) {
                        counter++;
                    } else {
                        counter = 0;
                    }
                }
                if (counter == NUMBER_OF_VICTORY_SYMBOLS) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void humanStep() {
        System.out.println("Ваш ход:");
        while (true) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            boolean isValid = isCellValid(x, y);
            if (isValid) {
                array[x][y] = SYMBOL_HUMAN;
                return;
            } else {
                System.out.println("Неверно указаны координаты, либо ячейка заполнена, введите еще раз! ");
            }
        }
    }

    private static void init() {
        array = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = EMPTY_ITEM;
            }
        }
        in = new Scanner(System.in);
        r = new Random();
        isHumanStep = true;
    }

    private static void printField() {
        for (int i = 0; i <= FIELD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }


}
