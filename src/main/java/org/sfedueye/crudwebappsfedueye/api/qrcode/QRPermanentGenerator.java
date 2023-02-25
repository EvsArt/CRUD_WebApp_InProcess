package org.sfedueye.crudwebappsfedueye.api.qrcode;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class QRPermanentGenerator implements Runnable, CommandLineRunner {


        private final QRGenerator qrGenerator;

        private static int key;

        public static int getKey() {
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
