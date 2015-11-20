package printmon.service;

import org.springframework.stereotype.Component;

@Component
public class NixUpdater extends ExternalUpdater {

    @Override
    public void updatePrinters() {
        System.out.println("IT WORKS");
    }
}
