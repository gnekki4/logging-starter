package ru.gnekki4.loggingstarter;

public class LoggingStarterAutoConfiguration {

    public static void println(String str) {
        System.out.println("Вызвано из Gradle библиотеки: " + str);
    }
}