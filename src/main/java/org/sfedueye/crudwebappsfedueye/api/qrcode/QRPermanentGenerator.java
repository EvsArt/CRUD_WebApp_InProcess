package org.sfedueye.crudwebappsfedueye.api.qrcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QRPermanentGenerator implements Runnable, CommandLineRunner {


        private final QRGenerator qrGenerator;

        private static String key;

        public static String getKey() {
            return key;
        }

        public QRPermanentGenerator(QRGenerator qrGenerator) {
            this.qrGenerator = qrGenerator;
        }

        @Override
        public void run() {
            while (true){
                key = qrGenerator.generate();
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run(String... args) throws Exception {
            run();
        }

}
