package com.geybriyel.music.art;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AsciiArtRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String art1 =
                "                              ()\n" +
                        "                           () |\n" +
                        "                    _      |  |\n" +
                        "                   |       |.'\n" +
                        "                   |       '\n" +
                        "      __          ()   \\\n" +
                        "    ('__`>           .  \\  | /\n" +
                        "    // -(         ,   `. \\ |\n" +
                        "    /:_ /        /   ___________\n" +
                        "   / /_;\\       /____\\__________)____________\n" +
                        "  **/ ) \\\\,-_  /                       \\\\  \\ `.\n" +
                        "    | |  \\\\(\\\\J                        \\\\  \\  |\n" +
                        "    |  \\_J,)|~                         \\\\  \\  ;\n" +
                        "     \\._/' `|_______________,------------+-+-'\n" +
                        "      `.___.  \\     ||| /                | |\n" +
                        "     |_..__.'. \\    |||/                 | |\n" +
                        "       ||  || \\_\\__ |||                  `.|\n" +
                        "       ||  ||  \\_-'=|||                   ||\n" +
                        "  -----++--++-------++--------------------++--------\n";

        String art2 =
                        "------------------------------------------------\n" +
                        "                          ,\n" +
                        "                      ,   |\n" +
                        "   _,,._              |  0'\n" +
                        " ,'     `.__,--.     0'\n" +
                        "/   .--.        |           ,,,\n" +
                        "| [=========|==|==|=|==|=|==___]\n" +
                        "\\   \"--\"  __    |           '''\n" +
                        " `._   _,'  `--'\n" +
                        "    \"\"'     ,   ,0     ,\n" +
                        "            |)  |)   ,'|\n" +
                        "  ____     0'   '   | 0'\n" +
                        "  |  |             0'\n" +
                        " 0' 0'\n" +
                        "------------------------------------------------\n";
        System.out.println(art2);

    }
}
