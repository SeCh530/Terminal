package com.company;

import com.company.TerminalExceptions.TerminalException;

import java.util.Scanner;

public class Window {
  private Terminal terminal = new TerminalImpl();

  private void printPinEnterText() {
    System.out.println("Добро пожаловать в терминал!");
    System.out.println("Введите пин-код для доступа к счету");
  }

  private String processInput(String currpin, String userLine) {
    for (char c : userLine.toCharArray()) {
      if (!Character.isDigit(c)) {
        System.out.println(TERMINAL_MESSAGES.BAD_PIN_CHAR);
        continue;
      }
      currpin += c;
      if (currpin.length() == 4) break;
    }
    return currpin;
  }

  private void authUserSceen(Scanner userInput) {
    // Эмуляция ввода по букве
    String currpin = "";
    printPinEnterText();
    while (currpin.length() != 4) {
      String input = userInput.nextLine().trim();
      currpin = processInput(currpin, input);
      if (currpin.length() == 4) {
        try {
          terminal.authUserWithPin(currpin);
        } catch (TerminalException ex) {
          currpin = "";
          System.out.println(ex.getMessage());
        }
      }
    }
    System.out.println();
  }

  private void deposit(Scanner sc) {
      do{
          System.out.print("Введите сумму:");
          String input = sc.nextLine();
          if (input.equals("stop")) break;
          try {
              terminal.deposit(Double.parseDouble(input));
              return;
          } catch (TerminalException e) {
              System.out.println(e.getMessage());
          }
      } while (true);
  }

  private void withdraw(Scanner sc) {
    do{
        System.out.print("Введите сумму для выдачи:");
      String input = sc.nextLine();
      if (input.equals("stop")) break;
      try {
        terminal.withdraw(Double.parseDouble(input));
        return;
      } catch (TerminalException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  private void showBalance() {
    try {
      System.out.println(terminal.showBalance());
    } catch (TerminalException e) {
      System.out.println(e.getMessage());
    }
  }

  public void Start() {
    Scanner userInput = new Scanner(System.in);
    authUserSceen(userInput);
    System.out.println("Доступные команды:");
    System.out.println("1 - Показать доступный баланс");
    System.out.println("2 - Пополнить баланс");
    System.out.println("3 - Вывести средства");
    String command;
    do {
      System.out.print("Введите команду:");
      command = userInput.nextLine();
      switch (command) {
        case "1":
          showBalance();
          break;
        case "2":
          deposit(userInput);
          break;
        case "3":
          withdraw(userInput);
          break;
        default:
          if(!command.equals("stop"))System.out.println("Неизвестная команда!");
      }
    } while ((!command.equals("stop")));
  }
}
