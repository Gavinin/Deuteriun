package com.deuteriun.system.utils;

import org.springframework.stereotype.Component;

@Component
public class LogoUtil {

    public static void StartLogo() {
        String logo =
                "|  _  \\             | |              (_)              \n" +
                "| | | |  ___  _   _ | |_   ___  _ __  _  _   _  _ __  \n" +
                "| | | | / _ \\| | | || __| / _ \\| '__|| || | | || '_ \\ \n" +
                "| |/ / |  __/| |_| || |_ |  __/| |   | || |_| || | | |\n" +
                "|___/   \\___| \\__,_| \\__| \\___||_|   |_| \\__,_||_| |_|\n" +
                "                                                      \n" +
                "                                                      ";

        System.out.println(logo+"\n Start successful!");
    }
}
