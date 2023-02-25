package org.sfedueye.crudwebappsfedueye.api;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.sfedueye.crudwebappsfedueye.api.qrcode.QRPermanentGenerator;
import org.sfedueye.crudwebappsfedueye.api.qrcode.RequestFormByQRCode;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/qr",
                produces = "application/json")
public class QRCodeControllerAPI {

    final UserRepository repository;

    public QRCodeControllerAPI(UserRepository repository) {
        this.repository = repository;
    }

    // DELETE ME
    @GetMapping // DELETE ME
    public int f(){ // DELETE ME
        return QRPermanentGenerator.getKey();   // DELETE ME
    }   // DELETE ME
    // DELETE ME

    @PostMapping(consumes = "application/json")
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public void checkUserAfterScanningQR(@RequestBody RequestFormByQRCode form,
                                                   @RequestParam("randomKey") int key,
                                                   HttpServletResponse response) {
        User user;

        if(key != QRPermanentGenerator.getKey()) response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        else {
            if (repository.existsById(form.getUserId())) {

                user = repository.findUserById(form.getUserId());

                if (form.getDataHashCode() != user.hashCode() || user.isEnabled()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                }

            }else response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }


}


